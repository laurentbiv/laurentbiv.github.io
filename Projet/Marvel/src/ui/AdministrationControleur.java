package ui;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import location.Artiste;
import location.FacadeAdministration;
import location.Film;
import location.Genre;

/**
 * Controleur JavaFX de la fenêtre d'administration.
 *
 * @author Eric Cariou
 *
 */
public class AdministrationControleur {
  
  @FXML
  private CheckBox checkBoxLocationFilm;
  
  @FXML
  private TextField entreeAffiche;
  
  @FXML
  private TextField entreeAnneeFilm;
  
  @FXML
  private TextField entreeNationaliteArtiste;
  
  @FXML
  private TextField entreeNomArtiste;
  
  @FXML
  private TextField entreeNomPrenomRealisateur;
  
  @FXML
  private TextField entreePrenomArtiste;
  
  @FXML
  private TextField entreeTitreFilm;
  
  @FXML
  private Label labelListeArtistes;
  
  @FXML
  private Label labelListeFilms;
  
  @FXML
  private ListView<String> listeArtistes;
  
  @FXML
  private ChoiceBox<String> listeChoixAgeLimite;
  
  @FXML
  private ListView<String> listeFilms;
  
  @FXML
  private ListView<String> listeGenresFilm;
  
  @FXML
  private ListView<String> listeTousGenres;
  
  private Stage stageUtilisateur; // Référence à la fenêtre utilisateur
  private Stage stageAdministration; // Référence à la fenêtre d'administration
  
  // Setter pour la fenêtre utilisateur
  public void setStageUtilisateur(Stage stage) {
    this.stageUtilisateur = stage;
  }
  
  // Setter pour la fenêtre d'administration
  public void setStageAdministration(Stage stage) {
    this.stageAdministration = stage;
  }
  
  public FacadeAdministration administration =
      FacadeAdministration.getInstance();
  
  private void afficherPopup(String message, AlertType type) {
    Alert alert = new Alert(type);
    if (type == AlertType.ERROR) {
      alert.setTitle("Erreur");
    } else {
      alert.setTitle("Information");
    }
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.setResizable(true);
    alert.showAndWait();
  }
  
  private void afficherPopupErreur(String message) {
    this.afficherPopup(message, AlertType.ERROR);
  }
  
  private void afficherPopupInformation(String message) {
    this.afficherPopup(message, AlertType.INFORMATION);
  }
  
  // J'ai rajouté un bouton pour afficher tous les films du gestionnaire
  @FXML
  void actionBoutonTousLesFilms(ActionEvent event) {
    listeFilms.getItems().clear();
    listeGenresFilm.getItems().clear();
    entreeTitreFilm.clear();
    entreeAnneeFilm.clear();
    entreeAffiche.clear();
    listeChoixAgeLimite.getSelectionModel().clearSelection();
    entreeNomPrenomRealisateur.clear();
    if (administration.ensembleFilms() != null) {
      for (Film f : administration.ensembleFilms()) {
        String titre = f.getTitre();
        int annee = f.getAnnee();
        listeFilms.getItems().add(titre + " " + "-" + annee);
      }
    } else {
      listeFilms.getItems().add("Aucun film n'a été crée pour le moment");
    }
    checkBoxLocationFilm.setSelected(false);
  }
  
  @FXML
  void actionBoutonAfficherArtistesActeurs(ActionEvent event) {
    listeArtistes.getItems().clear();
    for (Artiste a : administration.ensembleActeurs()) {
      String nom = a.getNom();
      String prenom = a.getPrenom();
      String nationalite = a.getNationalite();
      listeArtistes.getItems().add(nom + " " + prenom + " " + nationalite);
    }
  }
  
  @FXML
  void actionBoutonAfficherArtistesRealisateurs(ActionEvent event) {
    listeArtistes.getItems().clear();
    for (Artiste a : administration.ensembleRealisateurs()) {
      String nom = a.getNom();
      String prenom = a.getPrenom();
      String nationalite = a.getNationalite();
      listeArtistes.getItems().add(nom + " " + prenom + " " + nationalite);
    }
  }
  
  @FXML
  void actionBoutonAfficherFilmsActeurSelectionne(ActionEvent event) {
    listeFilms.getItems().clear();
    String artiste = listeArtistes.getSelectionModel().getSelectedItem();
    if (artiste == null) {
      this.afficherPopupErreur("Aucun acteur n'a été selectionné");
    } else {
      String[] details = artiste.split(" ");
      
      Artiste sup = administration.getArtiste(details[0], details[1]);
      if (administration.ensembleActeurs().contains(sup)) {
        Set<Film> films = new HashSet<Film>();
        films = administration.ensembleFilmsActeur(sup);
        if (sup.getFilms().size() != 0) {
          for (Film f : films) {
            listeFilms.getItems().add(f.getTitre() + " " + f.getAnnee());
          }
        } else {
          listeFilms.getItems().add("Cet acteur n'est dans aucun film");
        }
      } else {
        this.afficherPopupErreur("Cet artiste n'est pas un acteur");
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherFilmsDuRealisateur(ActionEvent event) {
    listeFilms.getItems().clear();
    String realisateur = entreeNomPrenomRealisateur.getText();
    if (!entreeNomPrenomRealisateur.getText().isBlank()) {
      String[] details = realisateur.split(" ");
      String nom = details[0];
      String prenom = details[1];
      if (prenom == null) {
        this.afficherPopupErreur("Entrez un nom et un prénom");
      } else {
        Artiste rea = administration.getArtiste(nom, prenom);
        if (administration.ensembleRealisateurs().contains(rea)) {
          for (Film film : administration.ensembleFilmsRealisateur(rea)) {
            listeFilms.getItems().add(film.getTitre() + " " + film.getAnnee());
          }
        } else {
          this.afficherPopupErreur("Cet artiste n'est pas un réalisateur");
        }
      }
    } else {
      this.afficherPopupErreur("Le champs réalisateur est vide");
    }
  }
  
  @FXML
  void actionBoutonAfficherFilmsRealisateurSelectionne(ActionEvent event) {
    listeFilms.getItems().clear();
    String artiste = listeArtistes.getSelectionModel().getSelectedItem();
    if (artiste == null) {
      this.afficherPopupErreur("Aucun réalisateur n'a été selectionné");
    } else {
      String[] details = artiste.split(" ");
      
      Artiste sup = administration.getArtiste(details[0], details[1]);
      
      // Artiste sup = new Artiste(details[0], details[1], details[2]);
      if (administration.ensembleRealisateurs().contains(sup)) {
        Set<Film> films = new HashSet<Film>();
        films = administration.ensembleFilmsRealisateur(sup);
        if (sup.getFilms().size() != 0) {
          for (Film f : films) {
            listeFilms.getItems().add(f.getTitre() + " " + f.getAnnee());
          }
        } else {
          listeFilms.getItems().add("Ce réalisateur na fait aucun film ");
        }
      } else {
        this.afficherPopupErreur("Cet artiste n'est pas un réalisateur");
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherTousActeursFilm(ActionEvent event) {
    String f = listeFilms.getSelectionModel().getSelectedItem();
    if (f == null) {
      this.afficherPopupErreur("Aucun film n'a été selectionné");
    } else {
      String[] details = f.split(" ");
      if (details[0] != null) {
        Film film = administration.getFilm(details[0]);
        if (film != null) {
          listeArtistes.getItems().clear();
          for (Artiste acteur : film.getActeursfilm()) {
            listeArtistes.getItems().add(acteur.getNom() + " "
                + acteur.getPrenom() + " " + acteur.getNationalite());
          }
        } else {
          listeArtistes.getItems().add("Film introuvable");
        }
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherTousArtistes(ActionEvent event) {
    listeArtistes.getItems().clear();
    for (Artiste a : administration.getAllArtiste()) {
      String nom = a.getNom();
      String prenom = a.getPrenom();
      String nationalite = a.getNationalite();
      listeArtistes.getItems().add(nom + " " + prenom + " " + nationalite);
    }
  }
  
  @FXML
  void actionBoutonAjouterActeurFilm(ActionEvent event) {
    String artiste = listeArtistes.getSelectionModel().getSelectedItem();
    if (artiste == null) {
      this.afficherPopupErreur(
          "Aucun acteurs n'a été selectionné pour l'ajouter au film");
    } else {
      String[] details = artiste.split(" ");
      
      String selection = listeFilms.getSelectionModel().getSelectedItem();
      if (selection == null) {
        this.afficherPopupErreur(
            "Aucun film n'a été selectionné pour ajouter un acteur");
      } else {
        String[] details1 = selection.split(" ");
        Artiste acteur = administration.getArtiste(details[0], details[1]);
        Film film = administration.getFilm(details1[0]);
        boolean v = administration.ajouterActeurs(film, acteur);
        listeFilms.getItems().clear();
        if (v == true) {
          this.afficherPopupInformation("L'acteur " + acteur.getPrenom()
              + " à été ajouté au film " + film.getTitre());
        } else {
          this.afficherPopupErreur(
              "L'acteur est déja présent dans le film ou problème lors de l'ajout");
        }
      }
    }
  }
  
  @FXML
  void actionBoutonAjouterGenreFilm(ActionEvent event) {
    // Selection du genre
    Genre genre = null;
    String gt = listeTousGenres.getSelectionModel().getSelectedItem();
    for (Genre g : Genre.values()) {
      if (g.name().equals(gt)) {
        genre = g;
      }
    }
    if (gt == null) {
      this.afficherPopupErreur("Aucun genre n'a été selectionné");
    } else {
      // Selection du film
      String f = listeFilms.getSelectionModel().getSelectedItem();
      if (f == null) {
        this.afficherPopupErreur("Aucun film n'a été selectionné");
      } else {
        String[] details = f.split(" ");
        Film film = administration.getFilm(details[0]);
        
        // Ajout du genre selectionné au film
        
        if (administration.ajouterGenres(film, genre)) {
          this.afficherPopupInformation("Le genre " + genre.name()
              + " à été ajouté au film " + film.getTitre());
        } else {
          this.afficherPopupErreur("Probleme lors de l'ajout du genre.");
          
        }
      }
    }
    
  }
  
  @FXML
  void actionBoutonChercherArtiste(ActionEvent event) {
    String nom = entreeNomArtiste.getText();
    String prenom = entreePrenomArtiste.getText();
    if (!nom.isBlank() && !prenom.isBlank()) {
      Artiste artiste = administration.getArtiste(nom, prenom);
      if (artiste != null) {
        entreeNationaliteArtiste.setText(artiste.getNationalite());
        String nationalite = artiste.getNationalite();
        listeArtistes.getItems().clear();
        listeArtistes.getItems().add(nom + " " + prenom + " " + nationalite);
      } else {
        this.afficherPopupErreur("L'artiste n'exite pas");
      }
    } else {
      this.afficherPopupErreur("Entrez un nom et un prénom");
    }
  }
  
  @FXML
  void actionBoutonChercherFilm(ActionEvent event) {
    listeFilms.getItems().clear();
    listeGenresFilm.getItems().clear();
    String titre = entreeTitreFilm.getText();
    if (!titre.isBlank()) {
      Film film = administration.getFilm(titre);
      if (film != null) {
        listeFilms.getItems().add(film.getTitre() + " " + film.getAnnee());
        entreeAnneeFilm.setText(String.valueOf(film.getAnnee()));
        listeChoixAgeLimite.getSelectionModel()
            .select(String.valueOf(film.getAgemin()));
        entreeNomPrenomRealisateur.setText(film.getRealisateurfilm().getNom()
            + " " + film.getRealisateurfilm().getPrenom());
        if (film.getGenresfilm().size() != 0) {
          for (Genre genre : film.getGenresfilm()) {
            listeGenresFilm.getItems().add(genre.name());
          }
        } else {
          listeGenresFilm.getItems().add("Aucun genre pour ce film");
        }
      } else {
        this.afficherPopupErreur("Ce film n'existe pas");
      }
    } else {
      this.afficherPopupInformation("Entree un titre dans le champ titre");
    }
  }
  
  @FXML
  void actionBoutonChoisirArtisteSelectionneRealisateur(ActionEvent event) {
    // Recup des info du realisteur
    entreeNomPrenomRealisateur.clear();
    String artiste = listeArtistes.getSelectionModel().getSelectedItem();
    if (artiste == null) {
      this.afficherPopupErreur("Aucun artiste n'a été selectionné");
    } else {
      String[] details = artiste.split(" ");
      
      Artiste rea = new Artiste(details[0], details[1], details[2]);
      entreeNomPrenomRealisateur.setText(rea.getNom() + " " + rea.getPrenom());
    }
  }
  
  @FXML
  void actionBoutonEnregistrerArtiste(ActionEvent event) {
    String nom = entreeNomArtiste.getText();
    String prenom = entreePrenomArtiste.getText();
    String nationalite = entreeNationaliteArtiste.getText();
    if (!nom.isBlank() && !prenom.isBlank() && !nationalite.isBlank()) {
      if (administration != null) {
        Artiste artiste = administration.creerArtiste(nom, prenom, nationalite);
        if (artiste != null) {
          listeArtistes.getItems().add(nom + " " + prenom + " " + nationalite);
        } else {
          labelListeArtistes
              .setText("Erreur lors de la création de l'artiste.");
        }
      } else {
        this.afficherPopupErreur("L'admisnitration est null");
      }
    } else {
      this.afficherPopupErreur("Au moins un des champs est vide");
    }
  }
  
  @FXML
  void actionBoutonEnregistrerFilm(ActionEvent event) {
    
    // Recuperation des trois champs titre , annee et age
    String titre = entreeTitreFilm.getText();
    String anneet = entreeAnneeFilm.getText();
    String limitet = listeChoixAgeLimite.getValue();
    String realisateur = entreeNomPrenomRealisateur.getText();
    if (titre == null || anneet == null || limitet == null
        || realisateur == null) {
      this.afficherPopupErreur("Au moins un des champs est vide");
    } else {
      int annee = Integer.parseInt(anneet);
      int limite = Integer.parseInt(limitet);
      String[] details = realisateur.split(" ");
      String nom = details[0];
      String prenom = details[1];
      Artiste rea = administration.getArtiste(nom, prenom);
      boolean location = checkBoxLocationFilm.isSelected();
      
      // Verif si tout est bon et qu'un artiste est selectionné
      if (!titre.isBlank() && !anneet.isBlank()
          && listeChoixAgeLimite.getValue() != null
          && !entreeNomPrenomRealisateur.getText().isBlank()) {
        
        if (administration != null) {
          
          // creation du film
          
          Film film = administration.creerFilm(titre, rea, annee, limite);
          if (film != null) {
            listeFilms.getItems().clear();
            listeFilms.getItems().add(titre + " " + anneet);
            rea.getFilms().add(film);
            administration.ensembleRealisateurs().add(rea);
            if (entreeAffiche != null && !entreeAffiche.getText().isBlank()) {
              film.setAffiche(entreeAffiche.getText());
            }
            film.setEtat(location);
          } else {
            Film film1 = administration.getFilm(titre);
            if (film1 != null) {
              if (checkBoxLocationFilm.isSelected()) {
                film1.setEtat(true);
                this.afficherPopupInformation(
                    "Le film est ouvert à la location");
              } else {
                film1.setEtat(false);
                this.afficherPopupInformation(
                    "Le film est fermé à la location");
              }
            } else {
              listeFilms.getItems().add("Le film n'a pas été créé");
            }
          }
        } else {
          this.afficherPopupErreur("L'admisnitration est null");
        }
      } else {
        this.afficherPopupErreur("Au moins un des champs est vide");
      }
    }
  }
  
  @FXML
  void actionBoutonNouveauArtiste(ActionEvent event) {
    entreeNomArtiste.clear();
    entreePrenomArtiste.clear();
    entreeNationaliteArtiste.clear();
  }
  
  @FXML
  void actionBoutonNouveauFilm(ActionEvent event) {
    entreeTitreFilm.clear();
    entreeAnneeFilm.clear();
    entreeAffiche.clear();
    listeChoixAgeLimite.getSelectionModel().clearSelection();
    entreeNomPrenomRealisateur.clear();
    checkBoxLocationFilm.setSelected(false);
  }
  
  @FXML
  void actionBoutonParcourirAffiche(ActionEvent event) {
    // Créer un FileChooser pour choisir un fichier image
    FileChooser fileChooser = new FileChooser();
    
    // Ajouter des filtres pour ne permettre que les images
    FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
        "Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
    fileChooser.getExtensionFilters().add(imageFilter);
    
    // Ouvrir le dialogue pour choisir un fichier
    File file = fileChooser.showOpenDialog(new Stage());
    
    // Si un fichier est sélectionné, on le charge
    if (file != null) {
      // Mettre à jour le label pour afficher le nom du fichier
      entreeAffiche.setText(file.getName());
      
      String titre = entreeTitreFilm.getText();
      if (!titre.isBlank()) {
        Film film = administration.getFilm(titre);
        if (film != null) {
          try {
            administration.ajouterAffiche(film, entreeAffiche.getText());
          } catch (IOException e) {
            System.out.println(
                "Erreur lors de l'ajout de l'affiche" + e.getMessage());
          }
        }
      }
    }
  }
  
  @FXML
  void actionBoutonSupprimerArtiste(ActionEvent event) {
    
    String artiste = listeArtistes.getSelectionModel().getSelectedItem();
    if (artiste == null) {
      this.afficherPopupErreur("Aucun artiste n'a été selectionné");
    } else {
      String[] details = artiste.split(" ");
      Artiste sup = administration.getArtiste(details[0], details[1]);
      if (administration.supprimerArtiste(sup)) {
        listeArtistes.getItems().clear();
        for (Artiste a : administration.getAllArtiste()) {
          String nom = a.getNom();
          String prenom = a.getPrenom();
          String nationalite = a.getNationalite();
          listeArtistes.getItems().add(nom + " " + prenom + " " + nationalite);
        }
      } else {
        this.afficherPopupErreur("L'artiste ne peux pas etre supprimé");
      }
    }
  }
  
  @FXML
  void actionBoutonSupprimerFilm(ActionEvent event) {
    String f = listeFilms.getSelectionModel().getSelectedItem();
    if (f == null) {
      this.afficherPopupErreur("Aucun film n'a été selectionné");
    } else {
      String[] details = f.split(" ");
      
      Film film = administration.getFilm(details[0]);
      String titre = film.getTitre();
      listeFilms.getItems().clear();
      if (administration.supprimerFilm(film)) {
        this.afficherPopupInformation(
            "Le film " + titre + " a été à supprimé.");
        if (administration.ensembleFilms() != null) {
          for (Film ff : administration.ensembleFilms()) {
            listeFilms.getItems().add(ff.getTitre() + " " + ff.getAnnee());
          }
        } else {
          listeFilms.getItems().add("Aucun film n'a été crée pour le moment");
        }
      } else {
        this.afficherPopupErreur("Le film n'a pas pu etre supprimé");
      }
    }
  }
  
  @FXML
  void actionMenuApropos(ActionEvent event) {
    this.afficherPopupInformation("Version développé par Andias Ames,"
        + " Lise Auffret et Laurent Biveghe \n\nCette"
        + " application permet de gérer la location de films.");
    
  }
  
  @FXML
  void actionMenuCharger(ActionEvent event) {
    
  }
  
  @FXML
  void actionMenuQuitter(ActionEvent event) {
    // Fermer la fenêtre de formation
    if (stageAdministration != null) {
      stageAdministration.close();
    }
    
    // Fermer la fenêtre des étudiants
    if (stageUtilisateur != null) {
      stageUtilisateur.close();
    }
    
    // Optionnel : arrêter l'application
    System.exit(0);
    
  }
  
  @FXML
  void actionMenuSauvegarder(ActionEvent event) {
    
  }
  
  @FXML
  void actionListeSelectionArtiste(MouseEvent event) {
    
  }
  
  @FXML
  void actionListeSelectionFilm(MouseEvent event) {
    listeGenresFilm.getItems().clear();
    String f = listeFilms.getSelectionModel().getSelectedItem();
    if (f == null || f == "Aucun film n'a été crée pour le moment") {
      this.afficherPopupErreur("Le film n'existe pas dans l'application");
    }
    String[] details = f.split(" ");
    Film film = administration.getFilm(details[0]);
    if (film.getEtat()) {
      checkBoxLocationFilm.setSelected(true);
    } else {
      checkBoxLocationFilm.setSelected(false);
    }
    entreeTitreFilm.setText(film.getTitre());
    entreeAnneeFilm.setText(String.valueOf(film.getAnnee()));
    listeChoixAgeLimite.getSelectionModel()
        .select(String.valueOf(film.getAgemin()));
    entreeNomPrenomRealisateur.setText(film.getRealisateurfilm().getNom() + " "
        + film.getRealisateurfilm().getPrenom());
    if (film.getGenresfilm().size() != 0) {
      for (Genre genre : film.getGenresfilm()) {
        listeGenresFilm.getItems().add(genre.name());
      }
    } else {
      listeGenresFilm.getItems().add("Aucun genre pour ce film");
    }
    entreeAffiche.clear();
    entreeAffiche.setText(film.getAffiche());
  }
  
  @FXML
  void initialize() {
    // Initialisation des données
    // Realisateurs
    Artiste rea1 = administration.creerArtiste("Favreau", "Jon", "Américain");
    Artiste rea2 = administration.creerArtiste("Russo", "Anthony", "Américain");
    Artiste rea3 = administration.creerArtiste("Russo", "Joe", "Américain");
    Artiste rea4 = administration.creerArtiste("Gunn", "James", "Américain");
    Artiste rea5 = administration.creerArtiste("Waititi", "Taika", "Néo-Zélandais");

    
    // Acteurs
    Artiste act1 = administration.creerArtiste("Downey.Jr", "Robert", "Américain");
    Artiste act2 = administration.creerArtiste("Evans", "Chris", "Américain");
    Artiste act3 = administration.creerArtiste("Johansson", "Scarlett", "Américaine");
    Artiste act4 = administration.creerArtiste("Hemsworth", "Chris", "Australien");
    Artiste act5 = administration.creerArtiste("Ruffalo", "Mark", "Américain");
    Artiste act6 = administration.creerArtiste("Holland", "Tom", "Britannique");
    Artiste act7 = administration.creerArtiste("Boseman", "Chadwick", "Américain");
    Artiste act8 = administration.creerArtiste("Larson", "Brie", "Américaine");
    Artiste act9 = administration.creerArtiste("Cumberbatch", "Benedict", "Britannique");
    Artiste act10 = administration.creerArtiste("Rudd", "Paul", "Américain");

    
    // Films
    Film film1 = administration.creerFilm("Iron-Man", rea1, 2008, 13);
    Film film2 = administration.creerFilm("Captain-America", rea2, 2011, 13);
    Film film3 = administration.creerFilm("The-Avengers", rea3, 2012, 13);
    Film film4 =
        administration.creerFilm("Guardians-of-the-Galaxy", rea4, 2014, 13);
    Film film5 = administration.creerFilm("Thor:Ragnarok", rea5, 2017, 13);
    Film film6 = administration.creerFilm("Avengers:Endgame", rea2, 2019, 13);
    
    administration.ajouterActeurs(film1, act1, act2, act3, act4);
    administration.ajouterActeurs(film2, act5, act6);
    administration.ajouterActeurs(film3, act7, act8);
    administration.ajouterActeurs(film4, act9);
    administration.ajouterActeurs(film5, act10);
    administration.ajouterActeurs(film6, act1, act2, act3, act4, act5, act6,
        act7, act8, act9, act10);
    
    // Ajout des affiches
    film1.setAffiche("IronMan.jpg");
    film2.setAffiche("CaptainAmerica.jpg");
    film3.setAffiche("Avengers.jpg");
    film4.setAffiche("Galaxy.jpg");
    film5.setAffiche("Thor.jpg");
    film6.setAffiche("Endgame.jpg");
    
    // Ajout des genres
    administration.ajouterGenres(film1, Genre.Action, Genre.Aventure);
    administration.ajouterGenres(film2, Genre.Action, Genre.ScienceFiction);
    administration.ajouterGenres(film3, Genre.Action);
    administration.ajouterGenres(film4, Genre.Action, Genre.Aventure);
    administration.ajouterGenres(film5, Genre.Action, Genre.ScienceFiction,
        Genre.Comedie);
    
    // Initialisation de la liste deroulante d'age limite
    listeChoixAgeLimite.getItems().add("3");
    listeChoixAgeLimite.getItems().add("6");
    listeChoixAgeLimite.getItems().add("10");
    listeChoixAgeLimite.getItems().add("13");
    listeChoixAgeLimite.getItems().add("16");
    listeChoixAgeLimite.getItems().add("18");
    
    // Initilisation de la liste de tout les genres
    for (Genre g : Genre.values()) {
      listeTousGenres.getItems().add(g.name());
    }
  }
}
