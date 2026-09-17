#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <math.h>
#include <string.h>

#include "cell.h"
#include "list.h"

/* ================== FONCTIONS UTILITAIRES POUR TESTS ================== */

static int compter_cellules_liste(node_t *liste) {
    int count = 0;
    node_t *n = liste;
    while (n != NULL) {
        if (list_get_data(n) != NULL) count++;
        n = list_next(n);
    }
    return count;
}

static void afficher_valeur_cellule(s_cell *cell, int ligne, int colonne) {
    char nom_cellule[15];
    sprintf(nom_cellule, "%c%d", 'A' + colonne, ligne + 1);
    
    if (cell == NULL) {
        printf("Cellule %s: NULL\n", nom_cellule);
    } else {
        printf("Cellule %s: texte='%s', valeur=%.2f\n", 
               nom_cellule, cell->s, cell->val);
    }
}

/* ================== TESTS JALON 2 ================== */

static void test_initialisation_feuille() {
    printf("\n=== TEST 1: Initialisation de la feuille ===\n");
    printf("Resultat attendu: Feuille vide avec %d lignes et %d colonnes, toutes les cellules NULL\n", NBLIGNES, NBCOLONNES);
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    assert(feuille.nbLignes == NBLIGNES);
    assert(feuille.nbColonnes == NBCOLONNES);
    assert(feuille.cell_existantes == NULL);
    
    for (int i = 0; i < NBLIGNES; i++) {
        for (int j = 0; j < NBCOLONNES; j++) {
            assert(feuille.tab[i][j] == NULL);
        }
    }
    
    printf("Test reussi: Feuille correctement initialisee\n");
    nettoyer_feuille_calcul(&feuille);
}

static void test_creation_cellule_nombre() {
    printf("\n=== TEST 2: Creation cellule avec nombre ===\n");
    printf("Resultat attendu: Cellule creee avec texte '42' et valeur 42.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 0, 0, "42");
    s_cell *cell = obtenir_cellule(&feuille, 0, 0);
    
    assert(cell != NULL);
    assert(strcmp(cell->s, "42") == 0);
    assert(fabs(cell->val - 42.0) < 0.001);
    assert(compter_cellules_liste(feuille.cell_existantes) == 1);
    
    afficher_valeur_cellule(cell, 0, 0);
    printf("Test reussi: Cellule nombre creee correctement\n");
    nettoyer_feuille_calcul(&feuille);
}

static void test_creation_cellule_texte() {
    printf("\n=== TEST 3: Creation cellule avec texte ===\n");
    printf("Resultat attendu: Cellule creee avec texte 'Bonjour' et valeur 0.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 1, 2, "Bonjour");
    s_cell *cell = obtenir_cellule(&feuille, 1, 2);
    
    assert(cell != NULL);
    assert(strcmp(cell->s, "Bonjour") == 0);
    assert(fabs(cell->val - 0.0) < 0.001);
    
    afficher_valeur_cellule(cell, 1, 2);
    printf("Test reussi: Cellule texte creee correctement\n");
    nettoyer_feuille_calcul(&feuille);
}

static void test_formule_addition_simple() {
    printf("\n=== TEST 4: Formule addition simple ===\n");
    printf("Resultat attendu: Formule '=2 3 +' doit donner valeur 5.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 2, 0, "=2 3 +");
    s_cell *cell = obtenir_cellule(&feuille, 2, 0);
    
    assert(cell != NULL);
    double resultat = evaluer_cellule(cell);
    
    assert(fabs(resultat - 5.0) < 0.001);
    assert(fabs(cell->val - 5.0) < 0.001);
    
    afficher_valeur_cellule(cell, 2, 0);
    printf("Test reussi: Addition 2 + 3 = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_formule_soustraction() {
    printf("\n=== TEST 5: Formule soustraction ===\n");
    printf("Resultat attendu: Formule '=10 4 -' doit donner valeur 6.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 3, 1, "=10 4 -");
    s_cell *cell = obtenir_cellule(&feuille, 3, 1);
    
    double resultat = evaluer_cellule(cell);
    assert(fabs(resultat - 6.0) < 0.001);
    
    afficher_valeur_cellule(cell, 3, 1);
    printf("Test reussi: Soustraction 10 - 4 = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_formule_multiplication() {
    printf("\n=== TEST 6: Formule multiplication ===\n");
    printf("Resultat attendu: Formule '=3 4 *' doit donner valeur 12.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 4, 2, "=3 4 *");
    s_cell *cell = obtenir_cellule(&feuille, 4, 2);
    
    double resultat = evaluer_cellule(cell);
    assert(fabs(resultat - 12.0) < 0.001);
    
    afficher_valeur_cellule(cell, 4, 2);
    printf("Test reussi: Multiplication 3 * 4 = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_formule_division() {
    printf("\n=== TEST 7: Formule division ===\n");
    printf("Resultat attendu: Formule '=15 3 /' doit donner valeur 5.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 5, 3, "=15 3 /");
    s_cell *cell = obtenir_cellule(&feuille, 5, 3);
    
    double resultat = evaluer_cellule(cell);
    assert(fabs(resultat - 5.0) < 0.001);
    
    afficher_valeur_cellule(cell, 5, 3);
    printf("Test reussi: Division 15 / 3 = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_formule_division_zero() {
    printf("\n=== TEST 8: Formule division par zero ===\n");
    printf("Resultat attendu: Formule '=5 0 /' doit donner valeur 0.00 (gestion erreur)\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 6, 4, "=5 0 /");
    s_cell *cell = obtenir_cellule(&feuille, 6, 4);
    
    double resultat = evaluer_cellule(cell);
    assert(fabs(resultat - 0.0) < 0.001);
    
    afficher_valeur_cellule(cell, 6, 4);
    printf("Test reussi: Division par zero geree correctement = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_formule_modulo() {
    printf("\n=== TEST 9: Formule modulo ===\n");
    printf("Resultat attendu: Formule '=7 3 mod' doit donner valeur 1.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 7, 5, "=7 3 mod");
    s_cell *cell = obtenir_cellule(&feuille, 7, 5);
    
    double resultat = evaluer_cellule(cell);
    assert(fabs(resultat - 1.0) < 0.001);
    
    afficher_valeur_cellule(cell, 7, 5);
    printf("Test reussi: Modulo 7 mod 3 = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_formule_complexe() {
    printf("\n=== TEST 10: Formule complexe ===\n");
    printf("Resultat attendu: Formule '=3 4 5 + *' doit donner valeur 27.00 (3 * (4 + 5))\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 8, 6, "=3 4 5 + *");
    s_cell *cell = obtenir_cellule(&feuille, 8, 6);
    
    double resultat = evaluer_cellule(cell);
    assert(fabs(resultat - 27.0) < 0.001);
    
    afficher_valeur_cellule(cell, 8, 6);
    printf("Test reussi: Formule complexe 3 * (4 + 5) = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_formule_negatif() {
    printf("\n=== TEST 11: Formule avec nombre negatif ===\n");
    printf("Resultat attendu: Formule '=-5 3 +' doit donner valeur -2.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 9, 7, "=-5 3 +");
    s_cell *cell = obtenir_cellule(&feuille, 9, 7);
    
    double resultat = evaluer_cellule(cell);
    assert(fabs(resultat - (-2.0)) < 0.001);
    
    afficher_valeur_cellule(cell, 9, 7);
    printf("Test reussi: Addition avec negatif -5 + 3 = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_formule_decimal() {
    printf("\n=== TEST 12: Formule avec nombres decimaux ===\n");
    printf("Resultat attendu: Formule '=2.5 1.5 +' doit donner valeur 4.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 10, 8, "=2.5 1.5 +");
    s_cell *cell = obtenir_cellule(&feuille, 10, 8);
    
    double resultat = evaluer_cellule(cell);
    assert(fabs(resultat - 4.0) < 0.001);
    
    afficher_valeur_cellule(cell, 10, 8);
    printf("Test reussi: Addition decimale 2.5 + 1.5 = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_modification_cellule() {
    printf("\n=== TEST 13: Modification de cellule existante ===\n");
    printf("Resultat attendu: Cellule A1 change de '10' a '=5 2 *' et valeur passe de 10.00 a 10.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 0, 0, "10");
    s_cell *cell1 = obtenir_cellule(&feuille, 0, 0);
    assert(fabs(cell1->val - 10.0) < 0.001);
    
    definir_texte_cellule(&feuille, 0, 0, "=5 2 *");
    s_cell *cell2 = obtenir_cellule(&feuille, 0, 0);
    assert(cell1 == cell2);
    double resultat = evaluer_cellule(cell2);
    assert(fabs(resultat - 10.0) < 0.001);
    
    afficher_valeur_cellule(cell2, 0, 0);
    printf("Test reussi: Cellule modifiee correctement, nouvelle valeur = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_cellule_vide() {
    printf("\n=== TEST 14: Cellule avec texte vide ===\n");
    printf("Resultat attendu: Cellule avec texte '' doit avoir valeur 0.00\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 0, 0, "");
    s_cell *cell = obtenir_cellule(&feuille, 0, 0);
    
    double resultat = evaluer_cellule(cell);
    assert(fabs(resultat - 0.0) < 0.001);
    
    afficher_valeur_cellule(cell, 0, 0);
    printf("Test reussi: Cellule vide geree correctement = %.2f\n", resultat);
    nettoyer_feuille_calcul(&feuille);
}

static void test_multiple_cellules() {
    printf("\n=== TEST 15: Multiple cellules differentes ===\n");
    printf("Resultat attendu: 3 cellules avec valeurs differentes doivent etre gerees independamment\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    definir_texte_cellule(&feuille, 0, 0, "=1 2 +");   // A1 = 3
    definir_texte_cellule(&feuille, 1, 1, "=5 2 *");   // B2 = 10  
    definir_texte_cellule(&feuille, 2, 2, "42");       // C3 = 42
    
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    s_cell *b2 = obtenir_cellule(&feuille, 1, 1);
    s_cell *c3 = obtenir_cellule(&feuille, 2, 2);
    
    assert(fabs(evaluer_cellule(a1) - 3.0) < 0.001);
    assert(fabs(evaluer_cellule(b2) - 10.0) < 0.001);
    assert(fabs(evaluer_cellule(c3) - 42.0) < 0.001);
    assert(compter_cellules_liste(feuille.cell_existantes) == 3);
    
    afficher_valeur_cellule(a1, 0, 0);
    afficher_valeur_cellule(b2, 1, 1);
    afficher_valeur_cellule(c3, 2, 2);
    printf("Test reussi: 3 cellules independantes gerees correctement\n");
    nettoyer_feuille_calcul(&feuille);
}

static void test_indices_invalides() {
    printf("\n=== TEST 16: Indices de cellule invalides ===\n");
    printf("Resultat attendu: Tentative d'acces a des indices invalides doit retourner NULL\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    s_cell *cell1 = obtenir_cellule(&feuille, -1, 0);
    s_cell *cell2 = obtenir_cellule(&feuille, 0, -1);
    s_cell *cell3 = obtenir_cellule(&feuille, NBLIGNES, 0);
    s_cell *cell4 = obtenir_cellule(&feuille, 0, NBCOLONNES);
    
    assert(cell1 == NULL);
    assert(cell2 == NULL);
    assert(cell3 == NULL);
    assert(cell4 == NULL);
    
    printf("Test reussi: Indices invalides correctement rejetes\n");
    nettoyer_feuille_calcul(&feuille);
}

static void test_reference_simple() {
    printf("\n=== TEST 17: Référence simple ===\n");
    printf("Resultat attendu: Formule '=A1' doit référencer la cellule A1 (même valeur)\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Créer la cellule A1 avec valeur 10
    definir_texte_cellule(&feuille, 0, 0, "10");
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    assert(fabs(a1->val - 10.0) < 0.001);
    
    // Créer la cellule B1 qui référence A1
    definir_texte_cellule(&feuille, 0, 1, "=A1");
    s_cell *b1 = obtenir_cellule(&feuille, 0, 1);
    
    assert(b1 != NULL);
    assert(strcmp(b1->s, "=A1") == 0);
    printf("   Cellule B1 créée avec référence à A1\n");
    printf("   (Pour Jalon 2, les références ne sont pas encore résolues)\n");
    
    nettoyer_feuille_calcul(&feuille);
    printf("Test reussi: Référence simple geree\n");
}

static void test_reference_avec_calcul() {
    printf("\n=== TEST 18: Référence avec calcul ===\n");
    printf("Resultat attendu: Formule '=A1 5 +' avec A1=10 doit donner 15\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Créer A1 = 10
    definir_texte_cellule(&feuille, 0, 0, "10");
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    assert(fabs(a1->val - 10.0) < 0.001);
    
    // Créer B1 = A1 + 5 (formule avec référence et nombre)
    definir_texte_cellule(&feuille, 0, 1, "=A1 5 +");
    s_cell *b1 = obtenir_cellule(&feuille, 0, 1);
    
    assert(b1 != NULL);
    assert(strcmp(b1->s, "=A1 5 +") == 0);
    printf("   Cellule B1 créée avec formule '=A1 5 +'\n");
    printf("   (Pour Jalon 2, A1 est traité comme 0.0)\n");
    
    nettoyer_feuille_calcul(&feuille);
    printf("Test reussi: Référence avec calcul geree\n");
}

static void test_references_multiples() {
    printf("\n=== TEST 19: Références multiples ===\n");
    printf("Resultat attendu: Formule '=A1 B1 + C2 *' doit être analysée correctement\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Créer plusieurs cellules
    definir_texte_cellule(&feuille, 0, 0, "5");   // A1 = 5
    definir_texte_cellule(&feuille, 0, 1, "3");   // B1 = 3
    definir_texte_cellule(&feuille, 1, 2, "2");   // C2 = 2
    
    // Créer D1 avec formule complexe
    definir_texte_cellule(&feuille, 0, 3, "=A1 B1 + C2 *");
    s_cell *d1 = obtenir_cellule(&feuille, 0, 3);
    
    assert(d1 != NULL);
    assert(strcmp(d1->s, "=A1 B1 + C2 *") == 0);
    
    printf("   Cellule D1 créée avec formule complexe\n");
    printf("   Formule: %s\n", d1->s);
    printf("   (Pour Jalon 2, A1, B1, C2 sont traités comme 0.0)\n");
    
    nettoyer_feuille_calcul(&feuille);
    printf("Test reussi: Références multiples gerees\n");
}

static void test_reference_invalide() {
    printf("\n=== TEST 20: Référence invalide ===\n");
    printf("Resultat attendu: Formule '=A99' (ligne 99 invalide) doit être acceptée mais non résolue\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // A99 n'existe pas (max 50 lignes)
    definir_texte_cellule(&feuille, 0, 0, "=A99");
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    
    assert(a1 != NULL);
    assert(strcmp(a1->s, "=A99") == 0);
    
    printf("   Cellule A1 créée avec référence invalide A99\n");
    printf("   (La référence est acceptée syntaxiquement mais ne peut être résolue)\n");
    
    nettoyer_feuille_calcul(&feuille);
    printf("Test reussi: Référence invalide geree\n");
}

static void test_melange_reference_nombre() {
    printf("\n=== TEST 21: Mélange référence et nombre ===\n");
    printf("Resultat attendu: Formule '=A1 10 B2 5 + * -' doit être analysée\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Créer A1 et B2
    definir_texte_cellule(&feuille, 0, 0, "100");  // A1
    definir_texte_cellule(&feuille, 1, 1, "20");   // B2
    
    // Formule complexe mélangeant références et nombres
    definir_texte_cellule(&feuille, 2, 2, "=A1 10 B2 5 + * -");
    s_cell *c3 = obtenir_cellule(&feuille, 2, 2);
    
    assert(c3 != NULL);
    printf("   Cellule C3 créée avec formule: %s\n", c3->s);
    printf("   Analyse: A1 - (10 * (B2 + 5))\n");
    
    nettoyer_feuille_calcul(&feuille);
    printf("Test reussi: Mélange référence/nombre géré\n");
}

/* ================== TESTS JALON 3 (GRAPHE ET DÉPENDANCES) ================== */

/* Test 1 : Graphe simple (dépendance directe) */
static void test_jalon3_graphe_simple() {
    printf("\n=== TEST J3-1 : Graphe simple A1 → B1 ===\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Étape 1 : Créer A1 = 10
    definir_texte_cellule(&feuille, 0, 0, "10");
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    
    // Étape 2 : Créer B1 = A1 + 5
    definir_texte_cellule(&feuille, 0, 1, "=A1 5 +");
    s_cell *b1 = obtenir_cellule(&feuille, 0, 1);
    
    printf("Initialisation:\n");
    printf("  A1 = 10\n");
    printf("  B1 = A1 + 5 = %.2f (doit être 15.00)\n", b1->val);
    
    // Vérifier valeur initiale
    assert(fabs(b1->val - 15.0) < 0.001);
    
    // Étape 3 : Modifier A1 = 20 → B1 doit se recalculer automatiquement
    printf("\nModification A1 = 20...\n");
    definir_texte_cellule(&feuille, 0, 0, "20");
    
    printf("Après modification:\n");
    printf("  A1 = %.2f (doit être 20.00)\n", a1->val);
    printf("  B1 = %.2f (doit être 25.00 = 20+5)\n", b1->val);
    
    // Vérifications
    assert(fabs(a1->val - 20.0) < 0.001);
    assert(fabs(b1->val - 25.0) < 0.001);
    
    nettoyer_feuille_calcul(&feuille);
    printf("\nTest J3-1 réussi : Graphe simple fonctionne\n");
}

/* Test 2 : Chaîne de dépendances (A1 → B1 → C1) */
static void test_jalon3_chaine_dependances() {
    printf("\n=== TEST J3-2 : Chaîne de dépendances A1 → B1 → C1 ===\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Créer la chaîne
    definir_texte_cellule(&feuille, 0, 0, "5");           // A1 = 5
    definir_texte_cellule(&feuille, 0, 1, "=A1 2 *");    // B1 = A1 * 2
    definir_texte_cellule(&feuille, 0, 2, "=B1 3 +");    // C1 = B1 + 3
    
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    s_cell *b1 = obtenir_cellule(&feuille, 0, 1);
    s_cell *c1 = obtenir_cellule(&feuille, 0, 2);
    
    printf("Chaîne initiale:\n");
    printf("  A1 = 5\n");
    printf("  B1 = A1 * 2 = %.2f (doit être 10.00)\n", b1->val);
    printf("  C1 = B1 + 3 = %.2f (doit être 13.00)\n", c1->val);
    
    // Vérifier valeurs initiales
    assert(fabs(b1->val - 10.0) < 0.001);
    assert(fabs(c1->val - 13.0) < 0.001);
    
    // Modifier A1 → tout doit se recalculer dans le bon ordre
    printf("\nModification A1 = 10...\n");
    definir_texte_cellule(&feuille, 0, 0, "10");
    
    printf("Après modification:\n");
    printf("  A1 = %.2f (doit être 10.00)\n", a1->val);
    printf("  B1 = %.2f (doit être 20.00 = 10*2)\n", b1->val);
    printf("  C1 = %.2f (doit être 23.00 = 20+3)\n", c1->val);
    
    // Vérifications
    assert(fabs(a1->val - 10.0) < 0.001);
    assert(fabs(b1->val - 20.0) < 0.001);
    assert(fabs(c1->val - 23.0) < 0.001);
    
    nettoyer_feuille_calcul(&feuille);
    printf("\nTest J3-2 réussi : Chaîne de dépendances fonctionne\n");
}

/* Test 3 : Dépendances multiples (A1 → C1 ← B1) */
static void test_jalon3_dependances_multiples() {
    printf("\n=== TEST J3-3 : Dépendances multiples A1 → C1 ← B1 ===\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Créer configuration
    definir_texte_cellule(&feuille, 0, 0, "10");          // A1 = 10
    definir_texte_cellule(&feuille, 0, 1, "20");          // B1 = 20
    definir_texte_cellule(&feuille, 0, 2, "=A1 B1 +");   // C1 = A1 + B1
    
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    s_cell *b1 = obtenir_cellule(&feuille, 0, 1);
    s_cell *c1 = obtenir_cellule(&feuille, 0, 2);
    
    printf("Configuration initiale:\n");
    printf("  A1 = 10\n");
    printf("  B1 = 20\n");
    printf("  C1 = A1 + B1 = %.2f (doit être 30.00)\n", c1->val);
    
    assert(fabs(c1->val - 30.0) < 0.001);
    
    // Test 3.1 : Modifier A1 seulement
    printf("\nTest 3.1 : Modification A1 = 50...\n");
    definir_texte_cellule(&feuille, 0, 0, "50");
    
    printf("Après modification A1:\n");
    printf("  A1 = %.2f (doit être 50.00)\n", a1->val);
    printf("  B1 = %.2f (doit être 20.00 inchangé)\n", b1->val);
    printf("  C1 = %.2f (doit être 70.00 = 50+20)\n", c1->val);
    
    assert(fabs(a1->val - 50.0) < 0.001);
    assert(fabs(b1->val - 20.0) < 0.001);
    assert(fabs(c1->val - 70.0) < 0.001);
    
    // Test 3.2 : Modifier B1 seulement
    printf("\nTest 3.2 : Modification B1 = 30...\n");
    definir_texte_cellule(&feuille, 0, 1, "30");
    
    printf("Après modification B1:\n");
    printf("  A1 = %.2f (inchangé)\n", a1->val);
    printf("  B1 = %.2f (doit être 30.00)\n", b1->val);
    printf("  C1 = %.2f (doit être 80.00 = 50+30)\n", c1->val);
    
    assert(fabs(b1->val - 30.0) < 0.001);
    assert(fabs(c1->val - 80.0) < 0.001);
    
    nettoyer_feuille_calcul(&feuille);
    printf("\nTest J3-3 réussi : Dépendances multiples fonctionnent\n");
}

/* Test 4 : Graphe en étoile (une cellule avec plusieurs dépendances) */
static void test_jalon3_graphe_etoile() {
    printf("\n=== TEST J3-4 : Graphe en étoile ===\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Créer le centre
    definir_texte_cellule(&feuille, 0, 0, "100");        // A1 = 100 (centre)
    
    // Créer plusieurs cellules qui dépendent de A1
    definir_texte_cellule(&feuille, 0, 1, "=A1 10 +");   // B1 = A1 + 10
    definir_texte_cellule(&feuille, 0, 2, "=A1 2 *");    // C1 = A1 * 2
    definir_texte_cellule(&feuille, 0, 3, "=A1 50 -");   // D1 = A1 - 50
    
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    s_cell *b1 = obtenir_cellule(&feuille, 0, 1);
    s_cell *c1 = obtenir_cellule(&feuille, 0, 2);
    s_cell *d1 = obtenir_cellule(&feuille, 0, 3);
    
    printf("Configuration initiale (A1 centre):\n");
    printf("  A1 = 100\n");
    printf("  B1 = A1 + 10 = %.2f (doit être 110.00)\n", b1->val);
    printf("  C1 = A1 * 2 = %.2f (doit être 200.00)\n", c1->val);
    printf("  D1 = A1 - 50 = %.2f (doit être 50.00)\n", d1->val);
    
    // Vérifier initial
    assert(fabs(b1->val - 110.0) < 0.001);
    assert(fabs(c1->val - 200.0) < 0.001);
    assert(fabs(d1->val - 50.0) < 0.001);
    
    // Modifier A1 → toutes les cellules doivent se recalculer
    printf("\nModification A1 = 200...\n");
    definir_texte_cellule(&feuille, 0, 0, "200");
    
    printf("Après modification:\n");
    printf("  A1 = %.2f (doit être 200.00)\n", a1->val);
    printf("  B1 = %.2f (doit être 210.00 = 200+10)\n", b1->val);
    printf("  C1 = %.2f (doit être 400.00 = 200*2)\n", c1->val);
    printf("  D1 = %.2f (doit être 150.00 = 200-50)\n", d1->val);
    
    // Vérifications
    assert(fabs(a1->val - 200.0) < 0.001);
    assert(fabs(b1->val - 210.0) < 0.001);
    assert(fabs(c1->val - 400.0) < 0.001);
    assert(fabs(d1->val - 150.0) < 0.001);
    
    nettoyer_feuille_calcul(&feuille);
    printf("\nTest J3-4 réussi : Graphe en étoile fonctionne\n");
}

/* Test 5 : Suppression de dépendances (changement de formule) */
static void test_jalon3_suppression_dependance() {
    printf("\n=== TEST J3-5 : Suppression de dépendance ===\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Étape 1 : Créer dépendance A1 → B1
    definir_texte_cellule(&feuille, 0, 0, "10");
    definir_texte_cellule(&feuille, 0, 1, "=A1 5 +");
    
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    s_cell *b1 = obtenir_cellule(&feuille, 0, 1);
    
    printf("Étape 1 : Création dépendance\n");
    printf("  A1 = 10\n");
    printf("  B1 = A1 + 5 = %.2f\n", b1->val);
    
    // Vérifier que A1 a un successeur
    int succ_avant = compter_successeurs(a1);
    printf("  Successeurs de A1 : %d (doit être 1)\n", succ_avant);
    assert(succ_avant == 1);
    
    // Étape 2 : Changer B1 pour qu'il ne dépende plus de A1
    printf("\nÉtape 2 : Modification B1 (plus de dépendance à A1)\n");
    definir_texte_cellule(&feuille, 0, 1, "42");
    
    printf("  Nouvelle formule B1 = 42\n");
    printf("  Nouvelle valeur B1 = %.2f\n", b1->val);
    
    // Vérifier que A1 n'a plus de successeurs
    int succ_apres = compter_successeurs(a1);
    printf("  Successeurs de A1 après : %d (doit être 0)\n", succ_apres);
    
    // Vérifications
    assert(fabs(b1->val - 42.0) < 0.001);
    assert(succ_apres == 0);
    
    // Étape 3 : Modifier A1 ne doit plus affecter B1
    printf("\nÉtape 3 : Modification A1 (B1 ne doit pas changer)\n");
    definir_texte_cellule(&feuille, 0, 0, "99");
    
    printf("  A1 = %.2f\n", a1->val);
    printf("  B1 = %.2f (doit être 42.00 inchangé)\n", b1->val);
    
    assert(fabs(a1->val - 99.0) < 0.001);
    assert(fabs(b1->val - 42.0) < 0.001);
    
    nettoyer_feuille_calcul(&feuille);
    printf("\nTest J3-5 réussi : Suppression de dépendance fonctionne\n");
}

/* Test 6 : Référence à cellule inexistante */
static void test_jalon3_reference_inexistante() {
    printf("\n=== TEST J3-6 : Référence à cellule inexistante ===\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    // Créer une cellule qui référence une cellule qui n'existe pas / hors feuille
    definir_texte_cellule(&feuille, 0, 0, "=X99 5 +");
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    
    printf("Création A1 avec référence à X99 (inexistante / hors feuille):\n");
    printf("  Formule A1 : %s\n", a1->s);
    printf("  Valeur A1 : %.2f (doit être 5.00 car X99 est traitée comme 0.0)\n", a1->val);
    
    // La cellule X99 est hors feuille (99 > NBLIGNES), donc elle vaut 0.0
    // Formule : 0.0 + 5 = 5
    assert(fabs(a1->val - 5.0) < 0.001);
    
    // Maintenant, on tente de "créer" X99, mais comme elle est hors feuille,
    // notre implémentation ne la crée pas réellement.
    printf("\nTentative de création de X99 = 100 (hors feuille)...\n");
    definir_texte_cellule(&feuille, 98, 23, "100");  // ligne 99, col X -> hors NBLIGNES
    
    // A1 reste basée sur une référence invalide (ref == NULL), donc ne change pas.
    printf("  Valeur A1 après tentative de création X99 : %.2f (référence hors feuille, reste 5.00)\n", a1->val);
    
    // Vérification : A1 doit toujours valoir 5.0
    assert(fabs(a1->val - 5.0) < 0.001);
    
    nettoyer_feuille_calcul(&feuille);
    printf("\nTest J3-6 réussi : Références hors feuille traitées comme 0.0 et ne sont pas reliées ensuite\n");
}


/* Test 7 : Performance avec plusieurs cellules */
static void test_jalon3_performance_multiple() {
    printf("\n=== TEST J3-7 : Performance avec plusieurs cellules ===\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    
    int nombre_cellules = 10;
    printf("Création de %d cellules en chaîne...\n", nombre_cellules);
    
    // Créer une longue chaîne A1 → A2 → A3 → ... → A10
    definir_texte_cellule(&feuille, 0, 0, "1");  // A1 = 1
    
    for (int i = 1; i < nombre_cellules; i++) {
        char formule[20];
        char cellule_precedente[10];
        
        // Nom de la cellule précédente (A1, A2, etc.)
        sprintf(cellule_precedente, "A%d", i);
        // Formule : cellule précédente + 1
        sprintf(formule, "=%s 1 +", cellule_precedente);
        
        definir_texte_cellule(&feuille, i, 0, formule);
    }
    
    // Vérifier la dernière cellule
    s_cell *derniere = obtenir_cellule(&feuille, nombre_cellules - 1, 0);
    printf("  A%d = %.2f (doit être %d.00)\n", 
           nombre_cellules, derniere->val, nombre_cellules);
    
    assert(fabs(derniere->val - (double)nombre_cellules) < 0.001);
    
    // Modifier la première cellule → toute la chaîne doit se recalculer
    printf("\nModification A1 = 10...\n");
    definir_texte_cellule(&feuille, 0, 0, "10");
    
    s_cell *a1 = obtenir_cellule(&feuille, 0, 0);
    printf("  A1 = %.2f (doit être 10.00)\n", a1->val);
    printf("  A%d = %.2f (doit être %d.00)\n", 
           nombre_cellules, derniere->val, nombre_cellules + 9);
    
    assert(fabs(a1->val - 10.0) < 0.001);
    assert(fabs(derniere->val - (double)(nombre_cellules + 9)) < 0.001);
    
    nettoyer_feuille_calcul(&feuille);
    printf("\nTest J3-7 réussi : Performance avec chaîne longue fonctionne\n");
}

static void test_jalon3_graphe_complexe() {
    printf("\n=== TEST J3-8 : Graphe complexe (figure 2) ===\n");
    
    calcul feuille;
    initialiser_feuille_calcul(&feuille);
    printf("Création du graphe complexe...\n");
    
    // Créer les cellules de base
    definir_texte_cellule(&feuille, 2, 3, "100");   // D3 = 100 (ligne 3, col D)
    definir_texte_cellule(&feuille, 3, 0, "200");   // A4 = 200
    definir_texte_cellule(&feuille, 1, 3, "50");    // D2 = 50
    
    // C5 = D2 * 2
    definir_texte_cellule(&feuille, 4, 2, "=D2 2 *");
    
    // A5 = D3 + C5 + A4
    definir_texte_cellule(&feuille, 4, 0, "=D3 C5 + A4 +");
    
    s_cell *d3 = obtenir_cellule(&feuille, 2, 3);
    s_cell *d2 = obtenir_cellule(&feuille, 1, 3);
    s_cell *c5 = obtenir_cellule(&feuille, 4, 2);
    s_cell *a4 = obtenir_cellule(&feuille, 3, 0);
    s_cell *a5 = obtenir_cellule(&feuille, 4, 0);
    
    printf("\nValeurs initiales:\n");
    printf("  D3 = %.2f\n", d3->val);
    printf("  D2 = %.2f\n", d2->val);
    printf("  C5 = D2 * 2 = %.2f\n", c5->val);
    printf("  A4 = %.2f\n", a4->val);
    printf("  A5 = D3 + C5 + A4 = %.2f (doit être 400.00)\n", a5->val);
    
    assert(fabs(d2->val - 50.0) < 0.001);
    assert(fabs(c5->val - 100.0) < 0.001);
    assert(fabs(a5->val - 400.0) < 0.001);
    
    // Modifier D2 → C5 et A5 doivent se recalculer
    printf("\nModification D2 = 100...\n");
    definir_texte_cellule(&feuille, 1, 3, "100");
    
    printf("Après modification:\n");
    printf("  D2 = %.2f (doit être 100.00)\n", d2->val);
    printf("  C5 = %.2f (doit être 200.00 = 100*2)\n", c5->val);
    printf("  A5 = %.2f (doit être 500.00 = 100+200+200)\n", a5->val);
    
    assert(fabs(d2->val - 100.0) < 0.001);
    assert(fabs(c5->val - 200.0) < 0.001);
    assert(fabs(a5->val - 500.0) < 0.001);
    
    nettoyer_feuille_calcul(&feuille);
    printf("\nTest J3-8 réussi : Graphe complexe fonctionne\n");
    
}

/* ================== FONCTION PRINCIPALE ================== */

int main() {
    printf("===== DEBUT DES TESTS COMPLETS =====\n");
    printf("Configuration: %d lignes x %d colonnes\n", NBLIGNES, NBCOLONNES);
    
    printf("\n===== JALON 2: Analyse et évaluation ====\n");
    test_initialisation_feuille();
    test_creation_cellule_nombre();
    test_creation_cellule_texte();
    test_formule_addition_simple();
    test_formule_soustraction();
    test_formule_multiplication();
    test_formule_division();
    test_formule_division_zero();
    test_formule_modulo();
    test_formule_complexe();
    test_formule_negatif();
    test_formule_decimal();
    test_modification_cellule();
    test_cellule_vide();
    test_multiple_cellules();
    test_indices_invalides();
    test_reference_simple();
    test_reference_avec_calcul();
    test_references_multiples();
    test_reference_invalide();
    test_melange_reference_nombre();
    
    printf("\n\n===== JALON 3: Graphe et dépendances ====\n");
    test_jalon3_graphe_simple();           // Test 1
    test_jalon3_chaine_dependances();      // Test 2  
    test_jalon3_dependances_multiples();   // Test 3
    test_jalon3_graphe_etoile();           // Test 4
    test_jalon3_suppression_dependance();  // Test 5
    test_jalon3_reference_inexistante();   // Test 6
    test_jalon3_performance_multiple();    // Test 7
    test_jalon3_graphe_complexe();         // Test 8
    
    printf("\n===== FIN DE TOUS LES TESTS =====\n");
    printf(" JALON 2: 21 tests validés\n");
    printf(" JALON 3: 8 tests validés\n");
    printf(" TOTAL: 29 tests passés avec succès!\n");
    
    return 0;
}
