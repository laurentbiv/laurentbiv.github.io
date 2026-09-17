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

  <title>admin_accueil</title>

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
            <a class="navbar-brand" href="../index.php">
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
                  <a class="nav-link" href="admin_accueil.php">Accueil&profils</span></a>
                </li>
                <li class="nav-item active">
                  <a class="nav-link" href="../index.php"> Actualite</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="./admin_sujets.php">Sujet</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="../formulaire/inscription.php">hyperlien</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="deconnexion.php">Deconnexion</a>
                </li>
              </ul>
            </div>
          </nav>
        </div>
      </div>
    </header>
    <!-- end header section -->
  </div>
  

  <?php
session_start(); // Démarrage de la session

if (!isset($_SESSION['login']) || !isset($_SESSION['role'])) {
    header("Location: session.php");
    exit();
}

$mysqli = new mysqli('localhost', 'root', '', 'sneakers');

if ($mysqli->connect_errno) {
    echo "Erreur : Problème de connexion à la base de données.\n";
    exit();
}

if (!$mysqli->set_charset("utf8")) {
    echo "Erreur lors du chargement du jeu de caractères utf8.\n";
    exit();
}

echo '<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">';

echo "<div class='container mt-5'>";
echo "<h1>ESPACE PRIVÉ</h1>";
echo "<p>Bonjour, " . htmlspecialchars($_SESSION['login']) . "</p>";

if ($_SESSION['role'] == 'M') {
    // Affichage des informations de profil pour les membres
    $requete = "SELECT * FROM t_profil_pro WHERE com_pseudo = '" . $mysqli->real_escape_string($_SESSION['login']) . "'";
    $resultat = $mysqli->query($requete);
    
    if ($resultat && $resultat->num_rows > 0) {
        $profil = $resultat->fetch_assoc();
        echo "<div class='table-responsive'>";
        echo "<table class='table table-bordered table-danger'>";
        echo "<tr><th>Nom</th><th>Prenom</th><th>Statut</th><th>Validite</th><th>Date</th><th>Email</th></tr>";
        echo "<tr><td>" . htmlspecialchars($profil['pro_nom']) . "</td><td>" . htmlspecialchars($profil['pro_prenom']) . "</td><td>" . htmlspecialchars($profil['pro_statut']) . "</td><td>" . htmlspecialchars($profil['pro_validite']) . "</td><td>" . htmlspecialchars($profil['pro_date']) . "</td><td>" . htmlspecialchars($profil['com_pseudo']) . "</td></tr>";
        echo "</table>";
        echo "</div>";
    } else {
        echo "<p>Aucune information de profil à afficher.</p>";
    }
} if ($_SESSION['role'] == 'G') {
    
  $requete = "SELECT * FROM t_profil_pro WHERE com_pseudo = '" . $mysqli->real_escape_string($_SESSION['login']) . "'";
  $resultat = $mysqli->query($requete);
  
  if ($resultat && $resultat->num_rows > 0) {
      $profil = $resultat->fetch_assoc();
      echo "<div class='table-responsive'>";
      echo "<table class='table table-bordered table-danger'>";
      echo "<tr><th>Nom</th><th>Prenom</th><th>Statut</th><th>Validite</th><th>Date</th><th>Email</th></tr>";
      echo "<tr><td>" . htmlspecialchars($profil['pro_nom']) . "</td><td>" . htmlspecialchars($profil['pro_prenom']) . "</td><td>" . htmlspecialchars($profil['pro_statut']) . "</td><td>" . htmlspecialchars($profil['pro_validite']) . "</td><td>" . htmlspecialchars($profil['pro_date']) . "</td><td>" . htmlspecialchars($profil['com_pseudo']) . "</td></tr>";
      echo "</table>";
      echo "</div>";
  }

    // Affichage des options d'activation/désactivation pour les gestionnaires
    $requete = "SELECT * FROM t_profil_pro";
    $resultat = $mysqli->query($requete);
    if ($resultat) {
        echo "<p>Nombre total de profils : " . $resultat->num_rows . "</p>";
        echo "<div class='table-responsive'>";
        echo "<table class='table table-bordered'>";
        echo "<thead class='thead-dark'><tr><th>Nom</th><th>Prénom</th><th>Statut</th><th>Validité</th><th>Date</th><th>Email</th><th>Validation</th><th>Statut</th></tr></thead>";
        echo "<tbody>";
        while ($profil = $resultat->fetch_assoc()) {
            echo "<tr>";
            echo "<td>" . htmlspecialchars($profil['pro_nom']) . "</td>";
            echo "<td>" . htmlspecialchars($profil['pro_prenom']) . "</td>";
            echo "<td>" . htmlspecialchars($profil['pro_statut']) . "</td>";
            echo "<td>" . htmlspecialchars($profil['pro_validite']) . "</td>";
            echo "<td>" . htmlspecialchars($profil['pro_date']) . "</td>";
            echo "<td>" . htmlspecialchars($profil['com_pseudo']) . "</td>";
            echo "<td>";
            echo "<form action='compte_action.php' method='post'>";
            echo "<input type='hidden' name='pseudo' value='" . htmlspecialchars($profil['com_pseudo']) . "'>";
            echo "<button type='submit' name='action' class='btn " . ($profil['pro_validite'] == 'A' ? 'btn-success' : 'btn-danger') . " btn-sm'>" . ($profil['pro_validite'] == 'A' ? 'Activation' : 'Désactivé') . "</button>";

            echo "</form>";
            echo "</td>";
            // Ajout de la colonne Status avec bouton de changement
        echo "<td>";
        echo "<form action='compte_action.php' method='post'>";
        echo "<input type='hidden' name='pseudo' value='" . htmlspecialchars($profil['com_pseudo']) . "'>";
        echo "<button type='submit' name='status' class='btn " . ($profil['pro_statut'] == 'G' ? 'btn-warning' : 'btn-info') . " btn-sm'>" . ($profil['pro_statut'] == 'M' ? 'Membre' : 'Gestionnaire') . "</button>";
        echo "</form>";
        echo "</td>";
        echo "</tr>";
            echo "</tr>";
        }
        echo "</tbody>";
        echo "</table>";
        echo "</div>";
    } else {
        echo "<p>Aucune information de profil à afficher.</p>";
    }
}

echo "</div>"; // container
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