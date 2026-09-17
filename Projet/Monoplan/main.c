#include <gtk/gtk.h>
#include "cell.h"



calcul feuille;                     // modele : la feuille de calcul
s_bind binds[NBLIGNES][NBCOLONNES]; // liaisons IHM  modèle

/*VARIABLES GLOBALES */

static GtkWidget *entry_formula = NULL; // zone de texte "Formule"
static s_bind    *bind_selection = NULL; // cellule actuellement sélectionne

/* PROTOTYPES  */

static void rafraichir_affichage_feuille(void);

/*  CALLBACKS  */

/* Quand on valide une cellule dans la grille (touche Entree dedans) */


/* Quand une cellule prend le focus (on clique dedans) */
/* Quand on valide une cellule dans la grille (touche Entre dedans) */
static void on_cell_activate(GtkEntry *entry, gpointer user_data)
{
    s_bind *b = (s_bind *)user_data;
    const char *texte = gtk_entry_get_text(entry);

    definir_texte_cellule(&feuille, b->ligne, b->colonne, texte);
    b->cell = obtenir_cellule(&feuille, b->ligne, b->colonne);

    if (bind_selection == b && entry_formula != NULL) {
        gtk_entry_set_text(GTK_ENTRY(entry_formula), texte);
    }

    rafraichir_affichage_feuille();
}


/* Quand on valide la zone "Formule" (Entrée dans la barre de formule) */
static void on_formula_activate(GtkEntry *entry, gpointer user_data)
{
    (void)user_data; // non utilisé

    if (bind_selection == NULL) {
        // aucune cellule sélectionnée -> rien à faire
        return;
    }

    const char *texte = gtk_entry_get_text(entry);

    // Met à jour le modèle sur la cellule sélectionnée
    definir_texte_cellule(&feuille,
                          bind_selection->ligne,
                          bind_selection->colonne,
                          texte);

    // remet à jour le pointeur s_cell* du bind (au cas où elle vient d'être créée)
    bind_selection->cell = obtenir_cellule(&feuille,
                                           bind_selection->ligne,
                                           bind_selection->colonne);

    // Met aussi ce texte dans la GtkEntry de la cellule
    gtk_entry_set_text(GTK_ENTRY(bind_selection->case_widget), texte);

    // definir_texte_cellule s'occupe déja de recalculer les dépendances
    // -> on rafraîchit l'affichage de toute la grille
    rafraichir_affichage_feuille();
}

/* CREATION DES WIDGETS DE CELLULES  */
/* Quand une cellule prend le focus (on clique dedans) */
static gboolean on_cell_focus(GtkWidget *entry, GdkEvent *event, gpointer user_data)
{
    s_bind *b = (s_bind *)user_data;
    bind_selection = b;   // on mémorise la cellule sélectionnée

    if (entry_formula != NULL) {
        if (b->cell != NULL && b->cell->s != NULL) {
            // met le texte de la cellule dans la zone "Formule"
            gtk_entry_set_text(GTK_ENTRY(entry_formula), b->cell->s);
        } else {
            gtk_entry_set_text(GTK_ENTRY(entry_formula), "");
        }
    }

    return FALSE; // on laisse le traitement normal continuer
}

static void creer_widgets_cells(GtkWidget *grid)
{
    for (int i = 0; i < NBLIGNES; i++) {
        for (int j = 0; j < NBCOLONNES; j++) {

            GtkWidget *entry = gtk_entry_new();

            // placer dans la GtkGrid : (colonne, ligne, largeur=1, hauteur=1)
            gtk_grid_attach(GTK_GRID(grid), entry, j, i, 1, 1);

            // remplir la structure de liaison
            binds[i][j].ligne       = i;
            binds[i][j].colonne     = j;
            binds[i][j].case_widget = entry;
            binds[i][j].cell        = obtenir_cellule(&feuille, i, j); // NULL au début

            // quand on valide la cellule (Entrée dans la case)
            g_signal_connect(entry, "activate",
                             G_CALLBACK(on_cell_activate),
                             &binds[i][j]);

            // quand la cellule prend le focus (on clique dedans)
            g_signal_connect(entry, "focus-in-event",
                             G_CALLBACK(on_cell_focus),
                             &binds[i][j]);
        }
    }
}

/* RAFRAICHIR L’AFFICHAGE COMPLET  */

static void rafraichir_affichage_feuille(void)
{
    char buffer[64];

    for (int i = 0; i < NBLIGNES; i++) {
        for (int j = 0; j < NBCOLONNES; j++) {
            s_bind *b = &binds[i][j];

            if (b->cell != NULL) {
                // Affiche la valeur numérique calculée dans la cellule
                snprintf(buffer, sizeof(buffer), "%.2f", b->cell->val);
                gtk_entry_set_text(GTK_ENTRY(b->case_widget), buffer);
            } else {
                gtk_entry_set_text(GTK_ENTRY(b->case_widget), "");
            }
        }
    }
}


/*  FONCTION PRINCIPALE  */

int main(int argc, char *argv[])
{
    GtkBuilder *builder;
    GtkWidget  *window;
    GtkWidget  *grid;
    GError *error = NULL;

    gtk_init(&argc, &argv);

    /* Charger l’interface décrite dans monoplan.glade */
    builder = gtk_builder_new();
    if (!gtk_builder_add_from_file(builder, "monoplan.glade", &error)) {
        g_printerr("Erreur chargement fichier Glade: %s\n", error->message);
        g_clear_error(&error);
        return 1;
    }

    /* Récupérer la fenêtre principale */
    window = GTK_WIDGET(gtk_builder_get_object(builder, "window_main"));
    if (!window) {
        g_printerr("ERREUR: Impossible de trouver 'window_main' dans le fichier Glade.\n");
        return 1;
    }

    /* Récupérer la grille qui contiendra les cellules (id = grid_cells) */
    grid = GTK_WIDGET(gtk_builder_get_object(builder, "grid_cells"));
    if (!grid) {
        g_printerr("ERREUR: Impossible de trouver 'grid_cells' dans le fichier Glade.\n");
        return 1;
    }

    /* Récupérer la zone de saisie de formule (id = entry_formula) */
    entry_formula = GTK_WIDGET(gtk_builder_get_object(builder, "entry_formula"));
    if (!entry_formula) {
        g_printerr("ERREUR: Impossible de trouver 'entry_formula' dans le fichier Glade.\n");
        return 1;
    }
    
    /* Connecter la validation de la formule (Entrée dans la barre de formule) */
    g_signal_connect(entry_formula, "activate",
                     G_CALLBACK(on_formula_activate), NULL);

    /* Initialiser le modèle de feuille */
    initialiser_feuille_calcul(&feuille);

    /* Créer toutes les cellules graphiques et remplir binds[][] */
    creer_widgets_cells(grid);

    /*  Premier rafraîchissement visuel (tout vide au début) */
    rafraichir_affichage_feuille();

    gtk_builder_connect_signals(builder, NULL);
    g_object_unref(builder);

    gtk_widget_show_all(window);
    gtk_main();

    return 0;
}
