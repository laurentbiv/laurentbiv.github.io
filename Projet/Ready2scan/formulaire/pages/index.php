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

  <title>SNEAKERS-BREST</title>

  <!-- slider stylesheet -->
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" />
  <!-- bootstrap core css -->
  <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
  <!-- font awesome style -->
  <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />

  <!-- responsive style -->
  <link href="css/responsive.css" rel="stylesheet" /> 
  <!--boostrap-table-->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Custom styles for this template -->
<link href="css/stylest.css" rel="stylesheet" />
  

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
                
                <li class="nav-item">
                  <a class="nav-link" href="recapitulatif/recapitulatif.php"> Recapitulatif</a>
                </li>
                
                <li class="nav-item">
                  <a class="nav-link" href="formulaire/inscription.php">Inscription</a>
                </li>
              </ul>
            </div>
          </nav>
        </div>
      </div>
    </header>
    
    <style>
      .table-bordered th, .table-bordered td {
          border-color:black;
          font-size: 17px;
          text-align:center;
      }
      h1{
        text-align:center;
        margin-top:10px;
      }
  </style>
    <!-- end header section -->
    <!-- slider section -->
    <section class="slider_section ">
      <div class="container ">
        <div class="row">
          <div class="col-md-6 ">
            <div class="detail-box">
              <h1>
                Bienvenue dans l'univers de <br>
               La SNEAKERS
              </h1>
              
            </div>
          </div>
          <div class="col-md-6">
            <div class="img-box">
              <img src="images/jordan1.png" alt="">
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- end slider section -->
  </div>
  
  <?php
// Connexion à la base de données
$mysqli = new mysqli('localhost', 'e21909953sql', 'Hwdv38UQ', 'e21909953_db1');
if ($mysqli->connect_errno) {
    echo "Error: Problème de connexion à la BDD \n";
    exit();
}

if (!$mysqli->set_charset("utf8")) {
    printf("Pb de chargement du jeu de car. utf8 : %s\n", $mysqli->error);
    exit();
}

// Requête pour compter le nombre de fiches
$requeteFiches = "SELECT COUNT(*) AS nombreFiches FROM t_fiche_fce where fic_etat='A';";
$resultatFiches = $mysqli->query($requeteFiches);
$rowFiches = $resultatFiches->fetch_assoc();
$nombreFiches = $rowFiches['nombreFiches'];

// Requête pour compter le nombre de sujets
$requeteSujets = "SELECT COUNT(*) AS nombreSujets FROM t_sujet_sjt;";
$resultatSujets = $mysqli->query($requeteSujets);
$rowSujets = $resultatSujets->fetch_assoc();
$nombreSujets = $rowSujets['nombreSujets'];
?>

<section class="professional_section layout_padding">
  
  <div class="indicators">
    <div class="circle" style="text-align:center;">
      <div class="number-circle" style="border-radius:50%; width:230px; height:230px; background-color:#0355cc; color:white; display:flex; align-items:center; justify-content:center; font-size:50px;">
        <?= $nombreFiches ?>
      </div>
      <span> <p>Nombre de Fiches</p></span>
    </div>
    <div class="circle" style="text-align:center;">
      <div class="number-circle" style="border-radius:50%; width: 230px; height:230px; background-color:#ff8a1d; color:white; display:flex; align-items:center; justify-content:center; font-size:50px;">
        <?= $nombreSujets ?>
      </div>
      <span> <p>Nombre de Sujets</p> </span>
    </div>
  </div>
</section>


</section>
  <h1>Actualités</h1>

  <!-- feature section -->
  

  <!-- end feature section -->

  <!-- about section -->
 



  <section class="about_section layout_padding-bottom">
  <?php
  // Connexion à la base de données
  $mysqli = new mysqli('localhost', 'e21909953sql', 'Hwdv38UQ', 'e21909953_db1');
  if ($mysqli->connect_error) {
      echo "Erreur de connexion à la base de données : " . $mysqli->connect_error;
      exit();
  }

  // Configuration de l'encodage de caractères à utiliser
  if (!$mysqli->set_charset("utf8mb4")) {
      echo "Erreur lors du chargement du jeu de caractères utf8mb4 : " . $mysqli->error;
      exit();
  }

  // Requête pour récupérer toutes les actualités
  $requete = "SELECT * FROM t_actualite_act;";
 // Exécution de la requête et début du tableau

 if ($resultat = $mysqli->query($requete)) {
  if ($resultat->num_rows === 0) {
    echo "<p>Aucune actualité mise en ligne.</p>";
} else{

  echo '<table class="table">';
  echo '<thead>';
  echo '<tr class="table-dark">';
  echo '<th>#</th>';
  echo '<th>Titre</th>';
  echo '<th>Date</th>';
  echo '<th>Texte</th>';
  echo '<th>Pseudo</th>';
  echo '</tr>';
  echo '</thead>';
  echo '<tbody>';

  // Initialisation du compteur de lignes pour alterner les couleurs
  $counter = 0;
  $contextual_classes = [
      'table-primary', 'table-success', 'table-danger',
      'table-info', 'table-warning', 'table-active',
      'table-secondary', 'table-light', 'table-dark'
  ];

  // Parcours des résultats et ajout des classes contextuelles
  while ($actualite = $resultat->fetch_assoc()) {
      $contextual_class = $contextual_classes[$counter % count($contextual_classes)];
      echo "<tr class='{$contextual_class}'>";
      echo '<th scope="row">' . htmlspecialchars($actualite['act_id']) . '</th>';
      echo '<td>' . htmlspecialchars($actualite['act_titre']) . '</td>';
      echo '<td>' . htmlspecialchars($actualite['act_date']) . '</td>';
      echo '<td>' . htmlspecialchars($actualite['act_texte']) . '</td>';
      echo '<td>' . htmlspecialchars($actualite['com_pseudo']) . '</td>';
      echo '</tr>';
      $counter++; // Incrément du compteur pour changer la couleur de la prochaine ligne
  }

  // Fin du tableau
  echo '</tbody>';
  echo '</table>';
}
      $resultat->free();
  } else {
      echo "Erreur lors de l'exécution de la requête : " . $mysqli->error;
  }
$requeteFiches = "SELECT COUNT(*) as nombreFiches FROM t_fiche_fce;";
$resultatFiches = $mysqli->query($requeteFiches);
$rowFiches = $resultatFiches->fetch_assoc();
$nombreFiches = $rowFiches['nombreFiches'];

$requeteSujets = "SELECT COUNT(*) as nombreSujets FROM t_sujet_sjt;";
$resultatSujets = $mysqli->query($requeteSujets);
$rowSujets = $resultatSujets->fetch_assoc();
$nombreSujets = $rowSujets['nombreSujets'];
  $mysqli->close();

  ?>
</section>




  <!-- end about section -->


  <!-- professional section -->



  <!-- end professional section -->

  <!-- service section -->

  


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