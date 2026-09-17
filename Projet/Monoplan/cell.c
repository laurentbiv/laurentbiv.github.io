#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>
#include "cell.h"
#include "list.h"
#include "stack.h"

/* ================== UTILITAIRES ================== */

static int indices_valides(int ligne, int colonne) {
    return (ligne >= 0 && ligne < NBLIGNES &&
            colonne >= 0 && colonne < NBCOLONNES);
}

/* nombre du type 3, -2, +4.5, 2.0, ... */
static int est_nombre(const char *s) {
    if (!s || *s == '\0') return 0;

    int i = 0;
    int vu_chiffre = 0;
    int vu_point = 0;

    if (s[i] == '+' || s[i] == '-') {
        i++;
        if (s[i] == '\0') return 0;
    }

    for (; s[i] != '\0'; i++) {
        if (s[i] == '.') {
            if (vu_point) return 0;
            vu_point = 1;
        } else if (isdigit((unsigned char)s[i])) {
            vu_chiffre = 1;
        } else {
            return 0;
        }
    }

    return vu_chiffre;
}

/* référence du type A1, B12, ... */
static int est_reference(const char *s) {
    if (!s || *s == '\0') return 0;

    if (s[0] < 'A' || s[0] > 'Z') return 0;

    for (int i = 1; s[i] != '\0'; i++) {
        if (!isdigit((unsigned char)s[i])) return 0;
    }
    return 1;
}

/* Convertir référence en indices - VERSION SIMPLIFIÉE */
static int reference_vers_indices(const char *ref, int *ligne, int *colonne) {
    if (!ref || ref[0] < 'A' || ref[0] > 'Z') return 0;
    
    *colonne = ref[0] - 'A';
    
    char *endptr;
    long num = strtol(ref + 1, &endptr, 10);
    if (*endptr != '\0' || num < 1 || num > NBLIGNES) return 0;
    
    *ligne = (int)num - 1;
    return 1;
}

/* associe "+", "-", "*", "/", "mod" à la bonne fonction */
static void (*trouve_operateur(const char *s))(my_stack_t *) {
    if (strcmp(s, "+")   == 0) return op_add;
    if (strcmp(s, "-")   == 0) return op_sub;
    if (strcmp(s, "*")   == 0) return op_mul;
    if (strcmp(s, "/")   == 0) return op_div;
    if (strcmp(s, "mod") == 0) return op_mod;
    return NULL;
}

/* ================== OPERATEURS ================== */

void op_add(my_stack_t *pile) {
    double b, a;
    if (STACK_POP2(pile, b, double) && STACK_POP2(pile, a, double)) {
        STACK_PUSH(pile, a + b, double);
    }
}

void op_sub(my_stack_t *pile) {
    double b, a;
    if (STACK_POP2(pile, b, double) && STACK_POP2(pile, a, double)) {
        STACK_PUSH(pile, a - b, double);
    }
}

void op_mul(my_stack_t *pile) {
    double b, a;
    if (STACK_POP2(pile, b, double) && STACK_POP2(pile, a, double)) {
        STACK_PUSH(pile, a * b, double);
    }
}

void op_div(my_stack_t *pile) {
    double b, a;
    if (STACK_POP2(pile, b, double) && STACK_POP2(pile, a, double)) {
        if (b != 0.0) {
            STACK_PUSH(pile, a / b, double);
        } else {
            STACK_PUSH(pile, 0.0, double);
        }
    }
}

void op_mod(my_stack_t *pile) {
    double b, a;
    if (STACK_POP2(pile, b, double) && STACK_POP2(pile, a, double)) {
        if (b != 0.0) {
            int ia = (int)a;
            int ib = (int)b;
            STACK_PUSH(pile, (double)(ia % ib), double);
        } else {
            STACK_PUSH(pile, 0.0, double);
        }
    }
}

/* ================== FONCTIONS JALON 3 ================== */

/* Ajoute un successeur à une cellule */
void ajouter_successeur(s_cell *source, s_cell *successeur) {
    if (!source || !successeur) return;
    
    node_t *current = source->successeurs;
    while (current != NULL) {
        s_cell *cell = (s_cell *)list_get_data(current);
        if (cell == successeur) return;
        current = list_next(current);
    }
    
    source->successeurs = list_append(source->successeurs, successeur);
}

/* Ajoute un prédécesseur à une cellule */
void ajouter_predecesseur(s_cell *cible, s_cell *predecesseur) {
    if (!cible || !predecesseur) return;
    
    node_t *current = cible->predecesseurs;
    while (current != NULL) {
        s_cell *cell = (s_cell *)list_get_data(current);
        if (cell == predecesseur) return;
        current = list_next(current);
    }
    
    cible->predecesseurs = list_append(cible->predecesseurs, predecesseur);
    cible->degre_entrant++;
}

/* Nettoie toutes les dépendances d'une cellule */
void nettoyer_dependances(s_cell *cellule) {
    if (!cellule) return;

    /* On enlève cellule de la liste des successeurs de tous ses PRÉDÉCESSEURS */
    node_t *pred = cellule->predecesseurs;
    while (pred != NULL) {
        s_cell *p = (s_cell *)list_get_data(pred);
        if (p) {
            p->successeurs = list_remove(p->successeurs, cellule);
        }
        pred = list_next(pred);
    }

    /* On détruit la liste de PRÉDÉCESSEURS de cellule */
    list_destroy(cellule->predecesseurs);
    cellule->predecesseurs = NULL;

    /* Le degré entrant correspond au nombre de prédécesseurs */
    cellule->degre_entrant = 0;

    /*  TRÈS IMPORTANT :
       On NE TOUCHE PAS à cellule->successeurs :
       - ce sont les cellules qui DÉPENDENT de "cellule"
       - elles ne changent pas quand "cellule" change sa propre formule
    */
}


/* Utilitaires pour compter */
int compter_predecesseurs(s_cell *cellule) {
    if (!cellule) return 0;
    
    int count = 0;
    node_t *current = cellule->predecesseurs;
    while (current != NULL) {
        count++;
        current = list_next(current);
    }
    return count;
}

int compter_successeurs(s_cell *cellule) {
    if (!cellule) return 0;
    
    int count = 0;
    node_t *current = cellule->successeurs;
    while (current != NULL) {
        count++;
        current = list_next(current);
    }
    return count;
}

/* Fonction récursive pour collecter le sous-graphe */
static void collecter_sous_graphe(s_cell *cellule, node_t **sous_graphe, int *visite_id) {
    if (!cellule || cellule->visite == *visite_id) return;
    
    cellule->visite = *visite_id;
    *sous_graphe = list_append(*sous_graphe, cellule);
    
    node_t *succ = cellule->successeurs;
    while (succ != NULL) {
        s_cell *successeur = (s_cell *)list_get_data(succ);
        collecter_sous_graphe(successeur, sous_graphe, visite_id);
        succ = list_next(succ);
    }
}

/* Construit le graphe pour une cellule spécifique */
void construire_graphe_cellule(s_cell *cellule, calcul *feuille) {
    if (!cellule || !feuille) return;

    /* 1) On enlève les anciennes dépendances de la cellule
          (son ancienne formule, ses anciens prédécesseurs) */
    nettoyer_dependances(cellule);

    /* 2) Si la cellule n'a pas de jetons, ce n'est pas une formule,
          elle ne dépend de personne → on s'arrête là */
    if (!cellule->jeton) {
        return;
    }

    /* 3) Sinon, on parcourt ses tokens et on recrée les liens
          prédécesseur/successeur à partir des REFERENCES */
    node_t *current = cellule->jeton;
    while (current != NULL) {
        s_token *token = (s_token *)list_get_data(current);

        if (token->type == REF && token->value.ref != NULL) {
            s_cell *ref_cell = token->value.ref;

            /* cellule dépend de ref_cell */
            ajouter_predecesseur(cellule, ref_cell);
            ajouter_successeur(ref_cell, cellule);
        }

        current = list_next(current);
    }
}



/* Algorithme de tri topologique pour le sous-graphe */
void recalculer_sous_graphe(calcul *feuille, s_cell *cell_initiale) {
    (void)feuille;  // pour éviter un warning si non utilisé
    
    if (!cell_initiale) return;
    
    static int visite_id = 1;
    visite_id++;
    
    node_t *sous_graphe = NULL;
    collecter_sous_graphe(cell_initiale, &sous_graphe, &visite_id);
    
    /* Normalement cell_initiale est déjà dans sous_graphe grâce à collecter_sous_graphe */
    if (cell_initiale->visite != visite_id) {
        sous_graphe = list_append(sous_graphe, cell_initiale);
        cell_initiale->visite = visite_id;
    }
    
    /*  ICI : on recalcule degre_entrant en ne comptant que
       les prédécesseurs qui sont DANS le sous-graphe (visite == visite_id) */
    node_t *current = sous_graphe;
    while (current != NULL) {
        s_cell *cell = (s_cell *)list_get_data(current);
        
        int deg = 0;
        node_t *pred = cell->predecesseurs;
        while (pred != NULL) {
            s_cell *p = (s_cell *)list_get_data(pred);
            if (p && p->visite == visite_id) {
                deg++;  // prédécesseur p fait partie du sous-graphe
            }
            pred = list_next(pred);
        }
        cell->degre_entrant = deg;
        
        current = list_next(current);
    }
    
    /* Liste des cellules prêtes à être calculées (d⁻ == 0) */
    node_t *liste_calcul = NULL;
    current = sous_graphe;
    while (current != NULL) {
        s_cell *cell = (s_cell *)list_get_data(current);
        if (cell->degre_entrant == 0) {
            liste_calcul = list_append(liste_calcul, cell);
        }
        current = list_next(current);
    }
    
    /* Algorithme de tri topologique + recalcul */
    while (liste_calcul != NULL) {
        s_cell *cell_a_calculer = (s_cell *)list_get_data(liste_calcul);
        liste_calcul = list_headRemove(liste_calcul);
        
        /* Évaluation de la cellule */
        evaluer_cellule(cell_a_calculer);
        
        /* Mise à jour des successeurs */
        node_t *succ = cell_a_calculer->successeurs;
        while (succ != NULL) {
            s_cell *successeur = (s_cell *)list_get_data(succ);
            successeur->degre_entrant--;
            
            if (successeur->degre_entrant == 0) {
                liste_calcul = list_append(liste_calcul, successeur);
            }
            
            succ = list_next(succ);
        }
    }
    
    list_destroy(sous_graphe);
}


/* Fonction principale pour recalculer avec dépendances */
void recalculer_cellule_et_dependants(calcul *feuille, s_cell *cell_modifiee) {
    if (!feuille || !cell_modifiee) return;
    
    construire_graphe_cellule(cell_modifiee, feuille);
    recalculer_sous_graphe(feuille, cell_modifiee);
}

/* Construit le graphe complet */
void construire_graphe_complet(calcul *feuille) {
    if (!feuille) return;
    
    node_t *current = feuille->cell_existantes;
    while (current != NULL) {
        s_cell *cellule = (s_cell *)list_get_data(current);
        construire_graphe_cellule(cellule, feuille);
        current = list_next(current);
    }
}

/* ================== CELLULES ================== */

s_cell *creer_cellule(const char *texte) {
    s_cell *cell = malloc(sizeof(s_cell));
    if (!cell) return NULL;

    if (texte) {
        cell->s = malloc(strlen(texte) + 1);
        if (!cell->s) { free(cell); return NULL; }
        strcpy(cell->s, texte);
    } else {
        cell->s = NULL;
    }

    cell->val = 0.0;
    cell->jeton = list_create();
    cell->successeurs = list_create();
    cell->predecesseurs = list_create();
    cell->degre_entrant = 0;
    cell->visite = 0;
    
    if (texte && strlen(texte) > 0) {
        analyser_cellule(cell);
        evaluer_cellule(cell);
    }
    
    return cell;
}

void definir_texte(s_cell *cellule, const char *texte) {
    if (!cellule) return;
    
    free(cellule->s);
    
    if (texte) {
        cellule->s = malloc(strlen(texte) + 1);
        if (cellule->s) {
            strcpy(cellule->s, texte);
            analyser_cellule(cellule);
            evaluer_cellule(cellule);
        } else {
            cellule->s = NULL;
            cellule->val = 0.0;
        }
    } else {
        cellule->s = NULL;
        cellule->val = 0.0;
    }
}

void detruire_cellule(s_cell *cellule) {
    if (!cellule) return;

    free(cellule->s);

    node_t *n = cellule->jeton;
    while (n) {
        s_token *t = (s_token *)list_get_data(n);
        free(t);
        n = list_next(n);
    }
    list_destroy(cellule->jeton);

    nettoyer_dependances(cellule);
    
    free(cellule);
}

/* ================== FEUILLE ================== */

void initialiser_feuille_calcul(calcul *feuille) {
    if (!feuille) return;

    feuille->fichier = NULL;
    feuille->nbLignes = NBLIGNES;
    feuille->nbColonnes = NBCOLONNES;

    for (int i = 0; i < NBLIGNES; i++)
        for (int j = 0; j < NBCOLONNES; j++)
            feuille->tab[i][j] = NULL;

    feuille->cell_existantes = list_create();
}

void nettoyer_feuille_calcul(calcul *feuille) {
    if (!feuille) return;

    for (int i = 0; i < NBLIGNES; i++)
        for (int j = 0; j < NBCOLONNES; j++)
            if (feuille->tab[i][j]) {
                detruire_cellule(feuille->tab[i][j]);
                feuille->tab[i][j] = NULL;
            }

    list_destroy(feuille->cell_existantes);
    feuille->cell_existantes = NULL;

    free(feuille->fichier);
    feuille->fichier = NULL;
}

s_cell *obtenir_cellule(calcul *feuille, int ligne, int colonne) {
    if (!feuille) return NULL;
    if (!indices_valides(ligne, colonne)) return NULL;
    return feuille->tab[ligne][colonne];
}

/* Version améliorée de analyser_cellule() qui utilise reference_vers_indices() */
static s_cell *trouver_cellule_reference(const char *ref, calcul *feuille) {
    int ligne, colonne;
    if (reference_vers_indices(ref, &ligne, &colonne)) {
        return obtenir_cellule(feuille, ligne, colonne);
    }
    return NULL;
}

void analyser_cellule_avec_feuille(s_cell *cellule, calcul *feuille) {
    if (!cellule || !feuille) return;

    node_t *n = cellule->jeton;
    while (n) {
        s_token *t = (s_token *)list_get_data(n);
        free(t);
        n = list_next(n);
    }
    list_destroy(cellule->jeton);
    cellule->jeton = NULL;

    const char *txt = cellule->s;
    if (!txt || *txt == '\0') {
        cellule->val = 0.0;
        return;
    }

    if (txt[0] != '=') {
        if (est_nombre(txt)) {
            cellule->val = atof(txt);
        } else {
            cellule->val = 0.0;
        }
        return;
    }

    const char *expr = txt + 1;
    char *copie = malloc(strlen(expr) + 1);
    if (!copie) return;
    strcpy(copie, expr);

    char *tok_str = strtok(copie, " ");
    while (tok_str != NULL) {
        s_token *tok = malloc(sizeof(s_token));
        if (!tok) {
            tok_str = strtok(NULL, " ");
            continue;
        }

        if (est_nombre(tok_str)) {
            tok->type = VALUE;
            tok->value.cst = atof(tok_str);
        } else if (est_reference(tok_str)) {
            tok->type = REF;
            tok->value.ref = trouver_cellule_reference(tok_str, feuille);
        } else {
            tok->type = OPERATOR;
            tok->value.operator = trouve_operateur(tok_str);
        }

        cellule->jeton = list_append(cellule->jeton, tok);
        tok_str = strtok(NULL, " ");
    }

    free(copie);
}

void definir_texte_cellule(calcul *feuille, int ligne, int colonne,
                           const char *texte) {
    if (!feuille) return;
    if (!indices_valides(ligne, colonne)) return;

    s_cell *cell = feuille->tab[ligne][colonne];

    if (!cell) {
        /* Cas 1 : la cellule n'existait pas encore → on la crée */
        cell = creer_cellule(texte);
        if (!cell) return;

        feuille->tab[ligne][colonne] = cell;
        feuille->cell_existantes = list_append(feuille->cell_existantes, cell);
        
        analyser_cellule_avec_feuille(cell, feuille);
        evaluer_cellule(cell);
        construire_graphe_cellule(cell, feuille);
    } else {
        
        definir_texte(cell, texte);
        analyser_cellule_avec_feuille(cell, feuille);
        evaluer_cellule(cell);
        construire_graphe_cellule(cell, feuille);
        recalculer_cellule_et_dependants(feuille, cell);
    }
}


/* Version originale de analyser_cellule() (sans feuille) */
void analyser_cellule(s_cell *cellule) {
    if (!cellule) return;

    node_t *n = cellule->jeton;
    while (n) {
        s_token *t = (s_token *)list_get_data(n);
        free(t);
        n = list_next(n);
    }
    list_destroy(cellule->jeton);
    cellule->jeton = NULL;

    const char *txt = cellule->s;
    if (!txt || *txt == '\0') {
        cellule->val = 0.0;
        return;
    }

    if (txt[0] != '=') {
        if (est_nombre(txt)) {
            cellule->val = atof(txt);
        } else {
            cellule->val = 0.0;
        }
        return;
    }

    const char *expr = txt + 1;
    char *copie = malloc(strlen(expr) + 1);
    if (!copie) return;
    strcpy(copie, expr);

    char *tok_str = strtok(copie, " ");
    while (tok_str != NULL) {
        s_token *tok = malloc(sizeof(s_token));
        if (!tok) {
            tok_str = strtok(NULL, " ");
            continue;
        }

        if (est_nombre(tok_str)) {
            tok->type = VALUE;
            tok->value.cst = atof(tok_str);
        } else if (est_reference(tok_str)) {
            tok->type = REF;
            tok->value.ref = NULL;  // Pas de feuille disponible
        } else {
            tok->type = OPERATOR;
            tok->value.operator = trouve_operateur(tok_str);
        }

        cellule->jeton = list_append(cellule->jeton, tok);
        tok_str = strtok(NULL, " ");
    }

    free(copie);
}

/* ================== EVALUATION ================== */

double evaluer_cellule(s_cell *cellule) {
    if (!cellule) return 0.0;

    if (!cellule->s || cellule->s[0] != '=') {
        if (cellule->s && est_nombre(cellule->s)) {
            cellule->val = atof(cellule->s);
        } else {
            cellule->val = 0.0;
        }
        return cellule->val;
    }

    if (!cellule->jeton) {
        analyser_cellule(cellule);
    }
    if (!cellule->jeton) {
        return cellule->val;
    }

    my_stack_t *pile = STACK_CREATE(100, double);
    if (!pile) return 0.0;

    node_t *n = cellule->jeton;
    while (n) {
        s_token *t = (s_token *)list_get_data(n);

        if (t->type == VALUE) {
            STACK_PUSH(pile, t->value.cst, double);
        } else if (t->type == REF) {
            double ref_val = 0.0;
            if (t->value.ref != NULL) {
                ref_val = t->value.ref->val;
            }
            STACK_PUSH(pile, ref_val, double);
        } else if (t->type == OPERATOR && t->value.operator) {
            t->value.operator(pile);
        }

        n = list_next(n);
    }

    double res = 0.0;
    if (STACK_MEM_USED(pile) > 0) {
        STACK_POP2(pile, res, double);
    }

    STACK_REMOVE(pile);
    cellule->val = res;
    return res;
}

/* Fonction pour afficher le graphe d'une cellule */
void afficher_graphe_cellule(s_cell *cellule) {
    if (!cellule) {
        printf("Cellule NULL\n");
        return;
    }
    
    printf("Cellule '%s' (valeur=%.2f):\n", cellule->s, cellule->val);
    printf("  Predecesseurs (%d): ", compter_predecesseurs(cellule));
    
    node_t *pred = cellule->predecesseurs;
    while (pred != NULL) {
        s_cell *p = (s_cell *)list_get_data(pred);
        printf("%s ", p->s);
        pred = list_next(pred);
    }
    printf("\n");
    
    printf("  Successeurs (%d): ", compter_successeurs(cellule));
    node_t *succ = cellule->successeurs;
    while (succ != NULL) {
        s_cell *s = (s_cell *)list_get_data(succ);
        printf("%s ", s->s);
        succ = list_next(succ);
    }
    printf("\n");
}
