package ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import location.Artiste;
import location.Evaluation;
import location.FacadeAdministration;
import location.FacadeUtilisateur;
import location.Film;
import location.Genre;
import location.InformationPersonnelle;



/**
 * Controleur JavaFX de la fenêtre utilisateur.
 *
 * @author Eric Cariou
 *
 */
public class UtilisateurControleur {
  
  @FXML
  private CheckBox checkFilmLouable;
  
  @FXML
  private TextField entreeAdresseUtilisateur;
  
  @FXML
  private TextField entreeAgeLimiteFilm;
  
  @FXML
  private TextField entreeAgeUtilisateur;
  
  @FXML
  private TextField entreeAnneeFilm;
  
  @FXML
  private TextField entreeAuteurEvaluation;
  
  @FXML
  private TextField entreeEvaluationMoyenne;
  
  @FXML
  private TextField entreeGenresFilm;
  
  @FXML
  private TextField entreeMotDePasseUtilisateur;
  
  @FXML
  private TextField entreeNationaliteArtiste;
  
  @FXML
  private TextField entreeNomArtiste;
  
  @FXML
  private TextField entreeNomPrenomRealisateurFilm;
  
  @FXML
  private TextField entreeNomUtilisateur;
  
  @FXML
  private TextField entreePrenomArtiste;
  
  @FXML
  private TextField entreePrenomUtilisateur;
  
  @FXML
  private TextField entreePseudoUtilisateur;
  
  @FXML
  private TextField entreeTitreFilm;
  
  @FXML
  private Label labelListeFilms;
  
  @FXML
  private Label labelListeArtistes;
  
  @FXML
  private ListView<String> listeArtistes;
  
  @FXML
  private ListView<String> listeEvaluations;
  
  @FXML
  private ListView<String> listeFilms;
  
  @FXML
  private ListView<String> listeFilmsEnLocation;
  
  @FXML
  private ListView<String> listeGenresFilm;
  
  @FXML
  private ChoiceBox<Integer> listeNoteEvaluation;
  
  @FXML
  private TextArea texteCommentaire;
  
  @FXML
  private StackPane paneAffiche;
  
  //Création d'une seule instance Administration (pattern singleton)
  FacadeAdministration admin = FacadeAdministration.getInstance();
  
  
  private FacadeUtilisateur facade = new FacadeUtilisateur(admin.films);
  
  
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
  
  
  @FXML
  void actionBoutonAfficherActeursFilmSelectionne(ActionEvent event) {
    
    String film = listeFilms.getSelectionModel().getSelectedItem();
    if (film == null) {
      this.afficherPopupErreur("Sélectionner un film");
    } else {
      String[] details = film.split(" ");
      Film f = admin.getFilm(details[0]);
      
      listeArtistes.getItems().clear();
      if (f.getActeursfilm().isEmpty()) {
        listeArtistes.getItems().add("Aucun acteur");
      } else {
        for (Artiste a : f.getActeursfilm()) {
          listeArtistes.getItems().add(a.getNom() + " " + a.getPrenom());
        }
      }
    }
    
  }
  
  @FXML
  void actionBoutonAfficherArtistesActeurs(ActionEvent event) {
    listeArtistes.getItems().clear();
    entreeNomArtiste.clear();
    entreePrenomArtiste.clear();
    entreeNationaliteArtiste.clear();
    if (admin.ensembleActeurs().isEmpty()) {
      listeArtistes.getItems().add("Aucun acteur");
    } else {
      for (Artiste a : admin.ensembleActeurs()) {
        listeArtistes.getItems().add(a.getNom() + " " + a.getPrenom());
      }
    }
    
  }
  
  @FXML
  void actionBoutonAfficherArtistesRealisateurs(ActionEvent event) {
    listeArtistes.getItems().clear();
    entreeNomArtiste.clear();
    entreePrenomArtiste.clear();
    entreeNationaliteArtiste.clear();
    if (admin.ensembleRealisateurs().isEmpty()) {
      listeArtistes.getItems().add("Aucun réalisateur");
    } else {
      for (Artiste a : admin.ensembleRealisateurs()) {
        listeArtistes.getItems().add(a.getNom() + " " + a.getPrenom());
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherFilmLoue(ActionEvent event) {
    String film = listeFilmsEnLocation.getSelectionModel().getSelectedItem();
    if (film == null || film == "Aucun film en location") {
      this.afficherPopupErreur("Sélectionner un film");
    } else {
      String[] details = film.split(" ");
      Film f = admin.getFilm(details[0]);
      
      entreeTitreFilm.setText(f.getTitre());
      entreeAnneeFilm.setText(Integer.toString(f.getAnnee()));
      entreeAgeLimiteFilm.setText(Integer.toString(f.getAgemin()));
      entreeNomPrenomRealisateurFilm.setText(f.getRealisateurfilm().getNom()
          + " " + f.getRealisateurfilm().getPrenom());
      String listeGenres = " ";
      for (Genre g : f.getGenresfilm()) {
        listeGenres += g;
      }
      entreeGenresFilm.setText(listeGenres);
      
      checkFilmLouable.setSelected(facade.estLouable(f));
      listeEvaluations.getItems().clear();
      if (facade.ensembleEvaluationsFilm(f) == null) {
        listeEvaluations.getItems().add("Aucune évaluation");
      } else {
        for (Evaluation e : facade.ensembleEvaluationsFilm(f)) {
          listeEvaluations.getItems()
              .add(Integer.toString(e.getNote()) + " " + e.getCommentaire());
        }
        entreeEvaluationMoyenne
            .setText(Double.toString(facade.evaluationMoyenne(f)));
      }
      if (f.getAffiche() != null) {
        Image image = new Image("ressources/" + f.getAffiche());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(120.0);
        imageView.setFitHeight(160.0);
        paneAffiche.getChildren().add(imageView);
      } else {
        paneAffiche.getChildren().clear();
      }
    }
    
  }
  
  @FXML
  void actionBoutonAfficherFilmRealisateurSelectionne(ActionEvent event) {
    
    String film = listeFilms.getSelectionModel().getSelectedItem();
    if (film == null) {
      this.afficherPopupErreur("Sélectionner un film");
    } else {
      String[] details = film.split(" ");
      Film f = admin.getFilm(details[0]);
      
      Artiste rea = f.getRealisateurfilm();
      listeFilms.getItems().clear();
      if (admin.ensembleFilmsRealisateur(rea).isEmpty()) {
        listeFilms.getItems().add("Aucun film");
      } else {
        for (Film films : admin.ensembleFilmsRealisateur(rea)) {
          listeFilms.getItems().add(films.getTitre());
        }
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherFilmsActeurSelectionne(ActionEvent event) {
    listeFilms.getItems().clear();
    String rea = listeArtistes.getSelectionModel().getSelectedItem();
    if (rea == null) {
      this.afficherPopupErreur("Sélectionner un acteur");
    } else {
      String[] details = rea.split(" ");
      Artiste a = admin.getArtiste(details[0], details[1]);
      
      if (!admin.ensembleActeurs().contains(a)) {
        listeFilms.getItems().add("Cet artiste n'est pas un acteur");
      } else {
        if (admin.ensembleFilmsActeur(a) == null) {
          listeFilms.getItems().add("Aucun film");
        } else {
          for (Film f : admin.ensembleFilmsActeur(a)) {
            listeFilms.getItems().add(f.getTitre());
          }
        }
      }
      
    }
    
    
  }
  
  @FXML
  void actionBoutonAfficherFilmsGenre(ActionEvent event) {
    String genre = listeGenresFilm.getSelectionModel().getSelectedItem();
    
    listeFilms.getItems().clear();
    if (facade.ensembleFilmsGenre(genre) == null) {
      listeFilms.getItems().add("Aucun film");
    } else {
      for (Film f : facade.ensembleFilmsGenre(genre)) {
        listeFilms.getItems().add(f.getTitre());
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherFilmsRealisateurSelectionne(ActionEvent event) {
    listeFilms.getItems().clear();
    String rea = listeArtistes.getSelectionModel().getSelectedItem();
    if (rea == null) {
      this.afficherPopupErreur("Sélectionner un réalisateur");
    } else {
      String[] details = rea.split(" ");
      Artiste a = admin.getArtiste(details[0], details[1]);
      if (!admin.ensembleRealisateurs().contains(a)) {
        listeFilms.getItems().add("Cet artiste n'est pas un réalisateur");
      } else {
        if (admin.ensembleFilmsRealisateur(a) == null) {
          listeFilms.getItems().add("Aucun film");
        } else {
          for (Film f : admin.ensembleFilmsRealisateur(a)) {
            listeFilms.getItems().add(f.getTitre());
          }
        }
      }
      
    }
  }
  
  @FXML
  void actionBoutonAfficherMonEvaluation(ActionEvent event) {
    if (facade.utilisateur == null) {
      this.afficherPopupErreur("Connectez-vous");
    } else {
      String film = listeFilms.getSelectionModel().getSelectedItem();
      if (film == null) {
        this.afficherPopupErreur("Sélectionner un film");
      } else {
        String[] details = film.split(" ");
        Film f = admin.getFilm(details[0]);
        if (facade.ensembleEvaluationsFilm(f) == null) {
          this.afficherPopupErreur(
              "Vous n'avez pas mis d'évaluation pour ce film");
        } else {
          for (Evaluation e : facade.ensembleEvaluationsFilm(f)) {
            if (e.getAuteur().equals(facade.utilisateur)) {
              entreeAuteurEvaluation.setText(e.getAuteur().getPseudo());
              texteCommentaire.setText(e.getCommentaire());
              listeNoteEvaluation.getSelectionModel().select(e.getNote());
            }
          }
        }
        
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherTousArtistes(ActionEvent event) {
    listeArtistes.getItems().clear();
    entreeNomArtiste.clear();
    entreePrenomArtiste.clear();
    entreeNationaliteArtiste.clear();
    if (admin.getAllArtiste().isEmpty()) {
      listeArtistes.getItems().add("Aucun artiste");
    } else {
      for (Artiste a : admin.getAllArtiste()) {
        listeArtistes.getItems().add(a.getNom() + " " + a.getPrenom());
      }
    }
  }
  
  @FXML
  void actionBoutonAfficherTousFilms(ActionEvent event) {
    listeFilms.getItems().clear();
    if (admin.ensembleFilms().isEmpty()) {
      listeFilms.getItems().add("Aucun film");
    } else {
      for (Film f : admin.ensembleFilms()) {
        listeFilms.getItems().add(f.getTitre() + " " + f.getAnnee());
      }
      entreeTitreFilm.clear();
      entreeAnneeFilm.clear();
      entreeAgeLimiteFilm.clear();
      entreeNomPrenomRealisateurFilm.clear();
      entreeGenresFilm.clear();
      
      checkFilmLouable.setSelected(false);
      
      listeEvaluations.getItems().clear();
      entreeEvaluationMoyenne.clear();
    }
  }
  
  @FXML
  void actionBoutonChercherActeur(ActionEvent event) {
    listeArtistes.getItems().clear();
    String nom = null;
    String prenom = null;
    if (!entreeNomArtiste.getText().isBlank()) {
      nom = entreeNomArtiste.getText();
    }
    if (!entreePrenomArtiste.getText().isBlank()) {
      prenom = entreePrenomArtiste.getText();
    }
    Artiste a = admin.getArtiste(nom, prenom);
    
    if (admin.ensembleActeurs().contains(a)) {
      listeArtistes.getItems().add(a.getNom() + " " + a.getPrenom());
      entreeNationaliteArtiste.setText(a.getNationalite());
    } else {
      listeArtistes.getItems().add("Cet acteur n'existe pas");
    }
    
  }
  
  @FXML
  void actionBoutonChercherFilm(ActionEvent event) {
    
    if (facade.utilisateur == null) {
      this.afficherPopupErreur("Connectez-vous");
    } else {
      
      
      String titre = null;
      if (!entreeTitreFilm.getText().isBlank()) {
        titre = entreeTitreFilm.getText();
      }
      Film f = admin.getFilm(titre);
      if (f == null) {
        this.afficherPopupErreur("Ce film n'existe pas");
      } else {
        listeFilms.getItems().clear();
        listeFilms.getItems().add(f.getTitre() + " " + f.getAnnee());
        entreeTitreFilm.setText(f.getTitre());
        entreeAnneeFilm.setText(Integer.toString(f.getAnnee()));
        entreeAgeLimiteFilm.setText(Integer.toString(f.getAgemin()));
        entreeNomPrenomRealisateurFilm.setText(f.getRealisateurfilm().getNom()
            + " " + f.getRealisateurfilm().getPrenom());
        String listeGenres = " ";
        for (Genre g : f.getGenresfilm()) {
          listeGenres += g;
        }
        entreeGenresFilm.setText(listeGenres);
        if (facade.estLouable(f)) {
          checkFilmLouable.setSelected(true);
        }
      }
    }
  }
  
  @FXML
  void actionBoutonChercherRealisateur(ActionEvent event) {
    listeArtistes.getItems().clear();
    String nom = null;
    String prenom = null;
    if (!entreeNomArtiste.getText().isBlank()) {
      nom = entreeNomArtiste.getText();
    }
    if (!entreePrenomArtiste.getText().isBlank()) {
      prenom = entreePrenomArtiste.getText();
    }
    Artiste a = admin.getArtiste(nom, prenom);
    if (admin.ensembleRealisateurs().contains(a)) {
      listeArtistes.getItems().add(a.getPrenom() + " " + a.getPrenom());
      entreeNationaliteArtiste.setText(a.getNationalite());
    } else {
      listeArtistes.getItems().add("Ce réalisateur n'existe pas");
    }
  }
  
  @FXML
  void actionBoutonConnexion(ActionEvent event) {
    String pseudo = null;
    String mdp = null;
    if (!entreePseudoUtilisateur.getText().isBlank()) {
      pseudo = entreePseudoUtilisateur.getText();
    }
    
    if (!entreeMotDePasseUtilisateur.getText().isBlank()) {
      mdp = entreeMotDePasseUtilisateur.getText();
    }
    if (facade.connexion(pseudo, mdp)) {
      entreeNomUtilisateur.setText(facade.utilisateur.getInfos().getNom());
      entreePrenomUtilisateur
          .setText(facade.utilisateur.getInfos().getPrenom());
      entreeAdresseUtilisateur
          .setText(facade.utilisateur.getInfos().getAdresse());
      entreeAgeUtilisateur
          .setText(Integer.toString(facade.utilisateur.getInfos().getAge()));
      if (facade.utilisateur.getFilmEnLocation().isEmpty()) {
        listeFilmsEnLocation.getItems().add("Aucun film en location");
      } else {
        for (Film f : facade.utilisateur.getFilmEnLocation()) {
          listeFilmsEnLocation.getItems().add(f.getTitre());
        }
      }
      
      
      
    } else {
      this.afficherPopupErreur("Pseudo ou mot de passe érroné");
    }
  }
  
  @FXML
  void actionBoutonCreerMonEvaluation(ActionEvent event) {
    if (facade.utilisateur == null) {
      this.afficherPopupErreur("Connectez-vous");
    } else {
      listeEvaluations.getItems().clear();
      entreeEvaluationMoyenne.clear();
      
      String film = listeFilms.getSelectionModel().getSelectedItem();
      if (film == null) {
        this.afficherPopupErreur("Sélectionner un film");
      } else {
        String[] details = film.split(" ");
        Film f = admin.getFilm(details[0]);
        
        String com = null;
        if (!texteCommentaire.getText().isBlank()) {
          com = texteCommentaire.getText();
        }
        int note = listeNoteEvaluation.getValue();
        
        Evaluation eval = new Evaluation(note, com, facade.utilisateur, f);
        
        facade.ajouterEvaluation(f, eval);
        if (!f.getEvaluationsfilm().contains(eval)) {
          this.afficherPopupErreur("Evaluation impossible à ajouter");
        } else {
          listeEvaluations.getItems().clear();
          for (Evaluation e : facade.ensembleEvaluationsFilm(f)) {
            listeEvaluations.getItems()
                .add(Integer.toString(e.getNote()) + " " + e.getCommentaire());
          }
          entreeEvaluationMoyenne
              .setText(Double.toString(facade.evaluationMoyenne(f)));
          
          texteCommentaire.clear();
          entreeAuteurEvaluation.clear();
          listeNoteEvaluation.getSelectionModel().select(0);
          
        }
        
      }
    }
  }
  
  @FXML
  void actionBoutonDeconnexion(ActionEvent event) {
    if (facade.utilisateur == null) {
      this.afficherPopupErreur("Connectez-vous");
    } else {
      facade.deconnexion();
      entreeNomUtilisateur.clear();
      entreePrenomUtilisateur.clear();
      entreeAdresseUtilisateur.clear();
      entreeAgeUtilisateur.clear();
      entreePseudoUtilisateur.clear();
      entreeMotDePasseUtilisateur.clear();
      listeFilmsEnLocation.getItems().clear();
    }
  }
  
  @FXML
  void actionBoutonFinLocation(ActionEvent event) {
    String film = listeFilmsEnLocation.getSelectionModel().getSelectedItem();
    if (film == null) {
      this.afficherPopupErreur("Sélectionner un film");
    } else {
      String[] details = film.split(" ");
      Film f = admin.getFilm(details[0]);
      
      facade.finLocationFilm(f);
      listeFilmsEnLocation.getItems().clear();
      for (Film films : facade.utilisateur.getFilmEnLocation()) {
        listeFilmsEnLocation.getItems().add(films.getTitre());
      }
    }
    
  }
  
  @FXML
  void actionBoutonInscription(ActionEvent event) {
    String nom = null;
    String prenom = null;
    String adresse = null;

    
    if (!entreeNomUtilisateur.getText().isBlank()) {
      nom = entreeNomUtilisateur.getText();
    }
    
    if (!entreePrenomUtilisateur.getText().isBlank()) {
      prenom = entreePrenomUtilisateur.getText();
    }
    if (!entreeAdresseUtilisateur.getText().isBlank()) {
      adresse = entreeAdresseUtilisateur.getText();
    }
    int age = 0;
    if (!entreeAgeUtilisateur.getText().isBlank()) {
      age = Integer.parseInt(entreeAgeUtilisateur.getText());
    }
    
    InformationPersonnelle info =
        new InformationPersonnelle(nom, prenom, adresse, age);
    String pseudo = null;
    String mdp = null;
    if (!entreePseudoUtilisateur.getText().isBlank()) {
      pseudo = entreePseudoUtilisateur.getText();
    }
    
    if (!entreeMotDePasseUtilisateur.getText().isBlank()) {
      mdp = entreeMotDePasseUtilisateur.getText();
    }
    
    
    int i = facade.inscription(pseudo, mdp, info);
    if (i == 0) {
      this.afficherPopupInformation("Utilisateur inscrit");
      entreeNomUtilisateur.clear();
      entreePrenomUtilisateur.clear();
      entreeAdresseUtilisateur.clear();
      entreeAgeUtilisateur.clear();
      entreePseudoUtilisateur.clear();
      entreeMotDePasseUtilisateur.clear();
    } else if (i == 1) {
      this.afficherPopupErreur("Le pseudo est déjà utilisé");
    } else if (i == 2) {
      this.afficherPopupErreur("Remplir le champ du pseudo et du mot de passe");
    } else {
      this.afficherPopupErreur("Remplir tous les champs");
    }
    
  }
  
  @FXML
  void actionBoutonLouerFilmSelectionne(ActionEvent event) {
    if (facade.utilisateur == null) {
      this.afficherPopupErreur("Connectez-vous");
    } else {
      String film = listeFilms.getSelectionModel().getSelectedItem();
      if (film == null) {
        this.afficherPopupErreur("Sélectionner un film");
      } else {
        String[] details = film.split(" ");
        Film f = admin.getFilm(details[0]);
        
        if (facade.estLouable(f)) {
          facade.louerFilm(f);
          listeFilmsEnLocation.getItems().clear();
          for (Film films : facade.utilisateur.getFilmEnLocation()) {
            listeFilmsEnLocation.getItems().add(films.getTitre());
          }
        }
        if (!facade.utilisateur.getFilmEnLocation().contains(f)) {
          this.afficherPopupErreur("Location impossible");
        }
      }
    }
  }
  
  @FXML
  void actionBoutonModifierMonEvaluation(ActionEvent event) {
    if (facade.utilisateur == null) {
      this.afficherPopupErreur("Connectez-vous");
    } else {
      
      String film = listeFilms.getSelectionModel().getSelectedItem();
      if (film == null) {
        this.afficherPopupErreur("Sélectionner un film");
      } else {
        String[] details = film.split(" ");
        Film f = admin.getFilm(details[0]);
        
        String com = null;
        if (!texteCommentaire.getText().isBlank()) {
          com = texteCommentaire.getText();
        }
        
        
        
        if (com == null) {
          
          this.afficherPopupErreur("Entrez une évaluation");
        } else {
          int note = listeNoteEvaluation.getValue();
          
          Evaluation eval = new Evaluation(note, com, facade.utilisateur, f);
          facade.modifierEvaluation(f, eval);
          
          listeEvaluations.getItems().clear();
          for (Evaluation e : facade.ensembleEvaluationsFilm(f)) {
            listeEvaluations.getItems()
                .add(Integer.toString(e.getNote()) + " " + e.getCommentaire());
          }
          entreeEvaluationMoyenne
              .setText(Double.toString(facade.evaluationMoyenne(f)));
          
          texteCommentaire.clear();
          entreeAuteurEvaluation.clear();
          listeNoteEvaluation.getSelectionModel().select(0);
        }
        
      }
    }
    
  }
  
  @FXML
  void actionSelectionArtiste(MouseEvent event) {
    listeFilms.getItems().clear();
    String artiste = listeArtistes.getSelectionModel().getSelectedItem();
    if (artiste == null || artiste == "Aucun acteur") {
      this.afficherPopupErreur("Sélectionner un artiste");
    } else {
      String[] details = artiste.split(" ");
      Artiste a = admin.getArtiste(details[0], details[1]);
      entreeNomArtiste.setText(a.getNom());
      entreePrenomArtiste.setText(a.getPrenom());
      entreeNationaliteArtiste.setText(a.getNationalite());
    }
    
  }
  
  @FXML
  void actionSelectionEvaluation(MouseEvent event) {
    String film = listeFilms.getSelectionModel().getSelectedItem();
    if (film == null) {
      this.afficherPopupErreur("Sélectionner un film");
    } else {
      String[] details = film.split(" ");
      Film f = admin.getFilm(details[0]);
      
      String evaluation =
          listeEvaluations.getSelectionModel().getSelectedItem();
      if (evaluation == null || evaluation == "Aucune évaluation") {
        this.afficherPopupErreur("Sélectionner une évaluation");
      } else {
        String[] detailse = evaluation.split(" ");
        
        for (Evaluation e : facade.ensembleEvaluationsFilm(f)) {
          if (Integer.toString(e.getNote()).equals(detailse[0])
              && e.getCommentaire().equals(detailse[1])) {
            
            entreeAuteurEvaluation.setText(e.getAuteur().getPseudo());
            texteCommentaire.setText(e.getCommentaire());
            listeNoteEvaluation.getSelectionModel().select(e.getNote());
          }
        }
      }
    }
    
    
  }
  
  @FXML
  void actionSelectionFilm(MouseEvent event) {
    
    if (facade.utilisateur == null) {
      this.afficherPopupErreur("Connectez-vous");
    } else {
      
      
      String film = listeFilms.getSelectionModel().getSelectedItem();
      if (film == null || film == "Aucun film") {
        this.afficherPopupErreur("Sélectionner un film");
      } else {
        String[] details = film.split(" ");
        Film f = admin.getFilm(details[0]);
        entreeTitreFilm.setText(f.getTitre());
        entreeAnneeFilm.setText(Integer.toString(f.getAnnee()));
        entreeAgeLimiteFilm.setText(Integer.toString(f.getAgemin()));
        entreeNomPrenomRealisateurFilm.setText(f.getRealisateurfilm().getNom()
            + " " + f.getRealisateurfilm().getPrenom());
        String listeGenres = " ";
        for (Genre g : f.getGenresfilm()) {
          listeGenres += g;
        }
        entreeGenresFilm.setText(listeGenres);
        
        checkFilmLouable.setSelected(facade.estLouable(f));
        
        listeEvaluations.getItems().clear();
        if (facade.ensembleEvaluationsFilm(f) == null) {
          listeEvaluations.getItems().add("Aucune évaluation");
        } else {
          for (Evaluation e : facade.ensembleEvaluationsFilm(f)) {
            listeEvaluations.getItems()
                .add(Integer.toString(e.getNote()) + " " + e.getCommentaire());
          }
          entreeEvaluationMoyenne
              .setText(Double.toString(facade.evaluationMoyenne(f)));
        }
        
        if (f.getAffiche() != null) {
          Image image = new Image("ressources/" + f.getAffiche());
          ImageView imageView = new ImageView(image);
          imageView.setFitWidth(120.0);
          imageView.setFitHeight(160.0);
          paneAffiche.getChildren().add(imageView);
        } else {
          paneAffiche.getChildren().clear();
        }
        
        
        
      }
    }
    
  }
  
  @FXML
  void initialize() {
    
    
    for (Genre g : Genre.values()) {
      listeGenresFilm.getItems().add(g.name());
    }
    listeNoteEvaluation.getItems().add(0);
    listeNoteEvaluation.getItems().add(1);
    listeNoteEvaluation.getItems().add(2);
    listeNoteEvaluation.getItems().add(3);
    listeNoteEvaluation.getItems().add(4);
    listeNoteEvaluation.getItems().add(5);
    
    
    
    InformationPersonnelle info =
        new InformationPersonnelle("Dupont", "Marc", "Brest ", 50);
    facade.inscription("marc_d", "mdp", info);
    
    facade.connexion("marc_d", "mdp");
    Film f1 = admin.getFilm("Iron-Man");
    f1.setEtat(true);
    
    facade.louerFilm(f1);
    Film f2 = admin.getFilm("Captain-America");
    f2.setEtat(true);
    facade.louerFilm(f2);
    Film f3 = admin.getFilm("The-Avengers");
    f3.setEtat(true);
    facade.louerFilm(f3);
    
    Evaluation e1 = new Evaluation(4, "Bon film", facade.utilisateur, f1);
    facade.ajouterEvaluation(f1, e1);
    Evaluation e2 = new Evaluation(2, "Moyen ", facade.utilisateur, f3);
    facade.ajouterEvaluation(f3, e2);
    
    facade.deconnexion();
    
    InformationPersonnelle info1 =
        new InformationPersonnelle("Dupont", "Alice", "Paris", 3);
    facade.inscription("alice_42", "alice2025", info1);
    
    InformationPersonnelle info2 =
        new InformationPersonnelle("Martin", "Paul", "Bordeaux", 10);
    facade.inscription("paul_the_great", "paul1234", info2);
    
    InformationPersonnelle info3 =
        new InformationPersonnelle("Lemoine", "Sophie", "Marseille", 14);
    facade.inscription("sofy33", "sofy2023", info3);
    
    facade.connexion("sofy33", "sofy2023");
    facade.louerFilm(f3);
    
    Evaluation e3 = new Evaluation(5, " Génial", facade.utilisateur, f3);
    facade.ajouterEvaluation(f3, e3);
    facade.deconnexion();
    
    InformationPersonnelle info4 =
        new InformationPersonnelle("Bernard", "Lucas", "Paris", 31);
    facade.inscription("lucas_b", "lucas2024", info4);
    
    facade.connexion("lucas_b", "lucas2024");
    Film f4 = admin.getFilm("Thor:Ragnarok");
    f4.setEtat(true);
    facade.louerFilm(f4);
    facade.deconnexion();
    
    
    // Mettre ici le code d'initialisation du contenu de la fenêtre
  }
  
}
