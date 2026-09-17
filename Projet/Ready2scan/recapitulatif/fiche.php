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

  <title>fiche</title>

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
                  <a class="nav-link" href="../index.php">Accueil </span></a>
                </li>
                <li class="nav-item active">
                  <a class="nav-link" href="../formulaire/inscription.php"> Inscription</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="recapitulatif.php">Recapitulatif</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="contact.html"></a>
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
<div class='cont'>

<?php
session_start();

// Assurez-vous que vos informations de connexion à la base de données sont correctes
$mysqli = new mysqli('localhost', 'root', '', 'sneakers');
if ($mysqli->connect_errno) {
    echo "Erreur : Problème de connexion à la base de données.\n";
    exit();
}

if (!$mysqli->set_charset("utf8")) {
    echo "Erreur lors du chargement du jeu de caractères utf8.\n";
    exit();
}

// Vérifiez si le code de la fiche a été passé en tant que paramètre GET
if (isset($_GET['code'])) {
    if (strlen($_GET['code']) == 12) {
        $codeFiche = $mysqli->real_escape_string($_GET['code']);

        // Requête pour obtenir les détails de la fiche
        $requeteFiche = "SELECT f.fic_numero, f.fic_label, f.fic_contenu, f.fic_image, s.suj_intitule, f.fic_etat
                        FROM t_fiche_fce AS f
                        INNER JOIN t_sujet_sjt AS s ON f.suj_numero = s.suj_numero
                        WHERE f.fic_code = '{$codeFiche}'";
        $resultatFiche = $mysqli->query($requeteFiche);

        if ($resultatFiche && $resultatFiche->num_rows > 0) {
            $detailsFiche = $resultatFiche->fetch_assoc();

            // Vérifiez si la fiche est cachée (état 'C')
            if ($detailsFiche['fic_etat'] !== 'C') {
                echo "<h2>" . $detailsFiche['fic_label'] . "</h2>";
                echo "<h4>Sujet: " . $detailsFiche['suj_intitule'] . "</h4>";
                echo "<img src='../images/" . $detailsFiche['fic_image'] . "' alt='Image de la fiche'><br>";
                echo "<p>" . $detailsFiche['fic_contenu'] . "</p>";
                echo "<div style='border: 4px solid black; padding: 10px; margin-top: 20px; '>";
                echo "<h4>Liens associés :</h4>";
                // Requête pour obtenir les hyperliens associés à la fiche
                $requeteHyperliens = "SELECT h.hyp_nom, h.hyp_url 
                FROM t_hyperlien_hpl AS h
                JOIN t_association_ast AS a ON h.hyp_numero = a.hyp_numero
                WHERE a.fic_numero = '{$detailsFiche['fic_numero']}'";
                $resultatHyperliens = $mysqli->query($requeteHyperliens);
                if ($resultatHyperliens) {
                    while ($hyperlien = $resultatHyperliens->fetch_assoc()) {
                        echo "<a href='" . htmlspecialchars($hyperlien['hyp_url']) . "'>" . htmlspecialchars($hyperlien['hyp_nom']) . "</a><br>";
                    }
                } else {
                    echo "Aucun hyperlien trouvé pour cette fiche.";
                }
                echo "</div>";
            } else {
                echo "Cette fiche est actuellement cachée et ne peut pas être affichée pour le moment.";
            }
        } else {
            echo "Aucune fiche trouvée pour le code fourni ou la fiche n'existe pas.";
        }
    } else {
        echo "Le code de la fiche est incorrect. Assurez-vous qu'il contienne exactement 12 caractères.";
    }
} else {
    echo "Le code de la fiche n'a pas été fourni.";
}

$mysqli->close();
?>


</div>





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