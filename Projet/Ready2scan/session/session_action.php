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

  <title>Inance</title>

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
                SNEAKERS-BRESTS
              </span>
            </a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class=""> </span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav ">
                <li class="nav-item ">
                  <a class="nav-link" href="index.html">accueil </span></a>
                </li>
                <li class="nav-item active">
                  <a class="nav-link" href="about.html"> Recapitulatif</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="service.html">Services</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="contact.html">Contact Us</a>
                </li>
              </ul>
            </div>
          </nav>
        </div>
      </div>
    </header>
    <!-- end header section -->
  </div>

  <!-- about section -->

  <?php
// Démarrage de la session
session_start();

// Vérification de la présence des données de connexion
if (isset($_POST["pseudo"]) && isset($_POST["mdp1"])) {
    // Récupération et sécurisation des données saisies par l'utilisateur
    $pseudo = addslashes($_POST["pseudo"]);
    $mdp = addslashes($_POST["mdp1"]);

    // Hashage du mot de passe pour la comparaison
    $mdp = md5($mdp);

    // Connexion à la base de données
    $mysqli = new mysqli('localhost', 'root', '', 'sneakers');

    // Vérification de la connexion
    if ($mysqli->connect_errno) {
        echo "Erreur : Problème de connexion à la base de données.\n";
        exit();
    }

    // Préparation de la requête SQL
    $sql = "SELECT com_pseudo, com_mot_de_passe, pro_statut FROM t_compte_com 
            JOIN t_profil_pro using (com_pseudo)
            WHERE com_pseudo = '$pseudo' AND com_mot_de_passe = '$mdp' AND pro_validite = 'A';";

    // Exécution de la requête
    $resultat = $mysqli->query($sql);
    
    if ($resultat == false) {
        // La requête a echoué
        echo "Error: Problème d'accès à la base \n";
        exit();
    } else {
        // Vérification des résultats
        if ($resultat->num_rows == 1) {
            // Récupération des données de l'utilisateur
            $utilisateur = $resultat->fetch_assoc();

            // Mise en place des variables de session
            $_SESSION['login'] = $utilisateur['com_pseudo'];
            $_SESSION['role'] = $utilisateur['pro_statut'];
            // Redirection vers la page d'accueil administrateur
            header("Location:admin_accueil.php");
            exit(); // N'oubliez pas d'appeler exit() après la redirection
        } else {
            // Aucune ligne retournée - le compte n'existe pas ou n'est pas valide
            echo "Pseudo/Mot de passe incorrect(s) ou desactive pour le moment!";
            echo "<br /><a href=\"./session.php\">Cliquez ici pour réafficher le formulaire</a>";
        }
    }
} else {
    echo "Les champs pseudo et mot de passe sont obligatoires.";
    echo "<br /><a href=\"./session.php\">Cliquez ici pour réafficher le formulaire</a>";
}

// Fermeture de la connexion
$mysqli->close();
?>



		

  <!-- end about section -->

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
        &copy; <span id="displayDateYear"></span> All Rights Reserved By
        <a href="https://html.design/">Free Html Templates</a>
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