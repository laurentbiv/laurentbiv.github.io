#ifndef CELL_H
#define CELL_H

#include "list.h"
#include "stack.h"

#define NBLIGNES    50
#define NBCOLONNES  26
typedef struct _GtkWidget GtkWidget;   // déclaration "vide" pour pouvoir utiliser GtkWidget*


typedef struct node node_t;

/* Cellule AVEC graphe */
typedef struct cell {
    char   *s;              /* texte saisi */
    double  val;            /* valeur numérique */
    node_t *jeton;          /* liste de s_token* */
    node_t *successeurs;    /* liste de s_cell* qui dépendent de MOI */
    node_t *predecesseurs;  /* liste de s_cell* dont JE dépends */
    int     degre_entrant;  /* d⁻ : nombre de prédécesseurs NON calculés */
    int     visite;         /* pour parcours */
} s_cell;

typedef struct bind {
    int ligne;             // position dans la feuille
    int colonne;
    GtkWidget *case_widget; // GtkEntry de la cellule
    s_cell    *cell;        // pointeur vers le modèle interne
} s_bind;


/* Jeton  */
typedef struct token {
    enum { VALUE, REF, OPERATOR } type;
    union {
        double  cst;
        s_cell *ref;
        void  (*operator)(my_stack_t *);
    } value;
} s_token;

/* Feuille de calcul */
typedef struct feuille_calcul {
    char   *fichier;
    s_cell *tab[NBLIGNES][NBCOLONNES];
    int     nbLignes;
    int     nbColonnes;
    node_t *cell_existantes;  /* liste de s_cell* */
} calcul;

/* ============ FONCTIONS EXISTANTES (J2) ============ */

/* Feuille */
void   initialiser_feuille_calcul(calcul *feuille);
void   nettoyer_feuille_calcul(calcul *feuille);
s_cell *obtenir_cellule(calcul *feuille, int ligne, int colonne);
void   definir_texte_cellule(calcul *feuille, int ligne, int colonne,
                             const char *texte);

/* Cellules */
s_cell *creer_cellule(const char *texte);
void    detruire_cellule(s_cell *cellule);
void    definir_texte(s_cell *cellule, const char *texte);

/* Analyse + évaluation */
void   analyser_cellule(s_cell *cellule);
double evaluer_cellule(s_cell *cellule);

/* Opérateurs */
void op_add(my_stack_t *pile);
void op_sub(my_stack_t *pile);
void op_mul(my_stack_t *pile);
void op_div(my_stack_t *pile);
void op_mod(my_stack_t *pile);

/* ============ FONCTIONS NOUVELLES (J3) ============ */

/* Gestion du graphe */
void ajouter_successeur(s_cell *source, s_cell *successeur);
void ajouter_predecesseur(s_cell *cible, s_cell *predecesseur);
void nettoyer_dependances(s_cell *cellule);
void construire_graphe_cellule(s_cell *cellule, calcul *feuille);
void construire_graphe_complet(calcul *feuille);

/* Tri topologique et recalcul */
void recalculer_cellule_et_dependants(calcul *feuille, s_cell *cell_modifiee);
void recalculer_sous_graphe(calcul *feuille, s_cell *cell_initiale);

/* Utilitaires graphe */
int  compter_predecesseurs(s_cell *cellule);
int  compter_successeurs(s_cell *cellule);
void afficher_graphe_cellule(s_cell *cellule);

#endif
