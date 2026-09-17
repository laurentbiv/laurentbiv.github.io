<!DOCTYPE html>
<html>

<head>
  <!-- Basic -->
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <!-- Mobile Metas -->
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <!-- Site Metas -->
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="author" content="" />

  <title>action</title>

  <!-- slider stylesheet -->
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" />
  <!-- bootstrap core css -->
  <link rel="stylesheet" type="text/css" href="../css/bootstrap.css" />
  <!-- font awesome style -->
  <link rel="stylesheet" type="text/css" href="../css/font-awesome.min.css" />

  <!-- Custom styles for this template -->
  <link href="../css/stylest.css" rel="stylesheet" />
  <!-- responsive style -->
  <link href="../css/responsive.css" rel="stylesheet" />

</head>

<body>
  <div class="hero_area">
    <!-- header section strats -->
    <header class="header_section">
      <div class="header_top">
    
      </div>
      <div class="header_bottom">
        <div class="container-fluid">
          <nav class="navbar navbar-expand-lg custom_nav-container ">
            <a class="navbar-brand" href="index.html">
              <span>
                SNEAKERS-BREST
              </span>
            </a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class=""> </span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav ">
                <li class="nav-item ">
                  <a class="nav-link" href="../index.php">Accueil </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="../recapitulatif/recapitulatif.php"> Recapitulatif</a>
                </li>
              
              </ul>
            </div>
          </nav>
        </div>
      </div>
    </header>
    <!-- end header section -->
  </div>


  <!-- contact section -->
  <div class='action'>
  <?php
  // Initialisation d'une variable pour suivre le succès
  $inscriptionReussie = true;
  $erreurMessageAffiche = false; // Ajout d'une variable pour suivre si l'erreur a déjà été affichée

  if (empty($_POST['pseudo']) || empty($_POST['mdp1']) || empty($_POST['mdp2']) || empty($_POST['nom']) || empty($_POST['prenom'])) {
      echo "Il faut remplir tous les champs. <a href='inscription.php'>Redirection au formulaire</a>";
      $inscriptionReussie = false;
  } elseif (strcmp($_POST['mdp1'], $_POST['mdp2']) != 0) {
      echo "Les mots de passe ne sont pas identiques. <a href='inscription.php'>Redirection au formulaire</a>";
      $inscriptionReussie = false;
  } else {
      $pseudo = addslashes(htmlspecialchars($_POST['pseudo']));
      $mdp = md5($_POST['mdp1']); 
      $nom = addslashes(htmlspecialchars($_POST['nom']));
      $prenom = addslashes(htmlspecialchars($_POST['prenom']));

      
      $mysqli = new mysqli('localhost', 'root', '', 'sneakers');
      if ($mysqli->connect_errno) {
          echo "<h2>Error: Problème de connexion à la BDD.</h2>";
          $inscriptionReussie = false;
      } else {
          if (!$mysqli->set_charset("utf8")) {
              echo "<h2>Pb de chargement du jeu de car. utf8.</h2>";
              $inscriptionReussie = false;
          }

          if($inscriptionReussie) {
              $compte = "INSERT INTO t_compte_com (com_pseudo, com_mot_de_passe) VALUES ('$pseudo', '$mdp')";
              if (!$mysqli->query($compte)) {
                if(!$erreurMessageAffiche) {
                    echo "<h2>Error: La requête a échoué pour le compte.</h2>";
                    $erreurMessageAffiche = true;
                }
                $inscriptionReussie = false;
              }
             
              
              $profil = "INSERT INTO t_profil_pro (pro_nom, pro_prenom, pro_validite, pro_statut, pro_date, com_pseudo) VALUES ('$nom', '$prenom', 'D', 'M', CURDATE(), '$pseudo')";
              if (!$mysqli->query($profil)) {
                if(!$erreurMessageAffiche) {
                    echo "<h2>Error: La requête a échoué pour le profil.</h2>";
                    $erreurMessageAffiche = true;
                }
                $mysqli->query("DELETE FROM t_compte_com WHERE com_pseudo = '$pseudo'");
                $inscriptionReussie = false;
              }
          }
      }

      if($inscriptionReussie) {
        echo "<h1>Inscription réussie !</h1>";
        // Ici, vous pouvez rediriger vers une autre page ou afficher un message
      }
      $mysqli->close();
  }
  ?>
</div>




  <!-- end contact section -->


  <!-- info section -->
  <section class="info_section ">
    <div class="container">
      <h4>
        Get In Touch
      </h4>
      <div class="row">
        <div class="col-lg-10 mx-auto">
          <div class="info_items">
            <div class="row">
              <div class="col-md-4">
                <a href="">
                  <div class="item ">
                    <div class="img-box ">
                      <i class="fa fa-map-marker" aria-hidden="true"></i>
                    </div>
                    <p>
                      Lorem Ipsum is simply dummy text
                    </p>
                  </div>
                </a>
              </div>
              <div class="col-md-4">
                <a href="">
                  <div class="item ">
                    <div class="img-box ">
                      <i class="fa fa-phone" aria-hidden="true"></i>
                    </div>
                    <p>
                      +02 1234567890
                    </p>
                  </div>
                </a>
              </div>
              <div class="col-md-4">
                <a href="">
                  <div class="item ">
                    <div class="img-box">
                      <i class="fa fa-envelope" aria-hidden="true"></i>
                    </div>
                    <p>
                      demo@gmail.com
                    </p>
                  </div>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="social-box">
      <h4>
        Follow Us
      </h4>
      <div class="box">
        <a href="">
          <i class="fa fa-facebook" aria-hidden="true"></i>
        </a>
        <a href="">
          <i class="fa fa-twitter" aria-hidden="true"></i>
        </a>
        <a href="">
          <i class="fa fa-youtube" aria-hidden="true"></i>
        </a>
        <a href="">
          <i class="fa fa-instagram" aria-hidden="true"></i>
        </a>
      </div>
    </div>
  </section>



  <!-- end info_section -->

  <!-- footer section -->
  <footer class="footer_section">
    <div class="container">
      <p>
        &copy; <span id="displayDateYear"></span> 
        <a href="https://html.design/"></a>
      </p>
    </div>
  </footer>
  <!-- footer section -->

  <script src="js/jquery-3.4.1.min.js"></script>
  <script src="js/bootstrap.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js">
  </script>
  <script src="js/custom.js"></script>
  <!-- Google Map -->
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCh39n5U-4IoWpsVGUHWdqB6puEkhRLdmI&callback=myMap"></script>
  <!-- End Google Map -->


</body>

</html>