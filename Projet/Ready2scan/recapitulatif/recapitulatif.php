
 <!-- BIVEGHE LAURENT JAURES -->
  <!-- 29-03-2024 -->
   <!-- mon projet permet de savoir l'actualite des paires de sneakers tendances , les tendances 
  selons les saisons ,affiches les classqiues qui ont ete tendances ,les prochaines sorties,et les 
differentes collaborations entre les differentes marques  -->
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

  <title>recapitulatif</title>

  <!-- slider stylesheet -->
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" />
  <!-- bootstrap core css -->
  <link rel="stylesheet" type="text/css" href="../css/bootstrap.css" />
  <!-- font awesome style -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">

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
                  <a class="nav-link" href="../index.php">Accueil</a>
                </li>
              
                <li class="nav-item">
                  <a class="nav-link" href="../formulaire/inscription.php">Inscription</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="../session/session.php">Connexion</a>
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
// Connexion à la base de données
$mysqli = new mysqli('localhost', 'root', '', 'sneakers');

// Vérification de la connexion
if ($mysqli->connect_errno) {
    echo "Erreur : Problème de connexion à la base de données.\n";
    exit();
}

if (!$mysqli->set_charset("utf8")) {
    printf("Erreur lors du chargement du jeu de caractères utf8 : %s\n", $mysqli->error);
    exit();
}

// Requête pour récupérer tous les sujets
$requeteSujets = "SELECT * FROM t_sujet_sjt;";
$resultatSujets = $mysqli->query($requeteSujets);

// Affichage des sujets et fiches associées
while ($sujet = $resultatSujets->fetch_assoc()) {
    echo "<h2>".$sujet['suj_intitule']."</h2>";
    echo "<div class='row row-cols-1 row-cols-md-3 g-7'>"; // Bootstrap 5 classes for responsive card columns
    
    // Requête pour récupérer les fiches associées à ce sujet
    $requeteFiches = "SELECT * FROM t_fiche_fce WHERE suj_numero = ".$sujet['suj_numero'].";";
    $resultatFiches = $mysqli->query($requeteFiches);
    
    // Vérification s'il y a des fiches pour le sujet
    if ($resultatFiches->num_rows > 0) {
        // Affichage des fiches en tant que cartes Bootstrap
        while ($fiche = $resultatFiches->fetch_assoc()) {
            echo "<div class='col'>";
            echo "<div class='card'>";
            echo "<img class='card-img-top' src='../images/".$fiche['fic_image']."' alt='".$fiche['fic_label']."'>";
            echo "<div class='card-body'>";
            echo "<h5 class='card-title'>".$fiche['fic_label']."</h5>";
            echo "<a href='fiche.php?code=".$fiche['fic_code']."' class='btn btn-primary'>Voir les détails</a>";
            echo "</div>";
            echo "</div>";
            echo "</div>";
        }
    } else {
        echo "<p>Aucune fiche disponible pour le moment.</p>";
    }
    
    echo "</div>"; // Fin de la ligne des fiches
}

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
                    42 rue de la porte
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