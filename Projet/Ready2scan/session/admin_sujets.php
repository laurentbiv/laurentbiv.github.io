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

  <title>admin_sujets</title>

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
                  <a class="nav-link" href="../recapitulatif/recapitulatif.php"> Actualite</a>
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

  <!-- about section -->

  <?php
session_start();

// Vérification de l'utilisateur connecté et du rôle
if (!isset($_SESSION['login']) || ($_SESSION['role'] !== 'M' && $_SESSION['role'] !== 'G')) {
    header("Location: session.php");
    exit();
}

// Connexion à la base de données
$mysqli = new mysqli('localhost', 'root', '', 'sneakers');
if ($mysqli->connect_errno) {
    echo "Erreur : Problème de connexion à la base de données.\n";
    exit();
}

if (!$mysqli->set_charset("utf8")) {
    echo "Erreur lors du chargement du jeu de caractères utf8.\n";
    exit();
}

// Gestion de l'ajout d'un sujet
if (isset($_POST['ajouter']) && !empty($_POST['intitule'])) {
    $intitule = $mysqli->real_escape_string($_POST['intitule']);
    $com_pseudo = $_SESSION['login'];

    $requeteAjoutSujet = "INSERT INTO t_sujet_sjt (suj_intitule, suj_date, com_pseudo) VALUES ('$intitule', CURDATE(), '$com_pseudo')";
    
    if ($mysqli->query($requeteAjoutSujet)) {
        header("Location: admin_sujets.php");
        exit();
    } else {
        echo "Erreur lors de l'ajout du sujet: " . $mysqli->error;
    }
}

// Gestion de la suppression des sujets et des fiches pour les membres ('M')
if ($_SESSION['role'] == 'M' && isset($_POST['action']) && $_POST['action'] == 'supprimer' && isset($_POST['suj_numero'])) {
    $suj_numero = $mysqli->real_escape_string($_POST['suj_numero']);
    
    $requete3 = "DELETE FROM t_association_ast WHERE fic_numero IN (SELECT fic_numero FROM t_fiche_fce WHERE suj_numero = '$suj_numero')";
    $resultat3 = $mysqli->query($requete3);
    // Ensuite supprimer le sujet
    // Supprimer les fiches associées d'abord
    $requete1 = "DELETE FROM t_fiche_fce WHERE suj_numero = '$suj_numero'";
    $resultat1 = $mysqli->query($requete1);

   
    $requete2 = "DELETE FROM t_sujet_sjt WHERE suj_numero = '$suj_numero'";
    $resultat2 = $mysqli->query($requete2);
    
   

    if ($resultat1 && $resultat2 && $resultat3) {
        header("Location: admin_sujets.php");
        exit();
    } else {
        echo "Erreur lors de la suppression du sujet.";
    }
}

// Gestion de la désactivation des fiches pour les gestionnaires ('G')
if ($_SESSION['role'] == 'G' && isset($_POST['action']) && isset($_POST['suj_numero'])) {
    $suj_numero = $mysqli->real_escape_string($_POST['suj_numero']);
    
    // Mettre à jour le statut des fiches pour les désactiver
    $requeteDesactivation = "UPDATE t_fiche_fce SET fic_etat = 'A' WHERE suj_numero = '$suj_numero'";
    if ($mysqli->query($requeteDesactivation)) {
        header("Location: admin_sujets.php");
        exit();
    } else {
        echo "Erreur lors de la désactivation des fiches.";
    }
}

// Code HTML pour l'entête et le lien Bootstrap CSS
echo '<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">';
echo "<div class='container mt-5'>";
echo "<h2>Gestion des sujets</h2>";

// Formulaire d'ajout de sujet pour tous les utilisateurs
if ($_SESSION['role'] !='G'){
  echo "<div class='mb-4'>";
  echo "<h4>Ajouter un nouveau sujet</h4>";
  echo "<form method='post' action=''>";
  echo "<div class='form-group'>";
  echo "<label for='intitule'>Intitulé du sujet:</label>";
  echo "<input type='text' class='form-control' id='intitule' name='intitule' required>";
  echo "</div>";
  echo "<button type='submit' name='ajouter' class='btn btn-primary'>Ajouter</button>";
  echo "</form>";
  echo "</div>";
  
}

// Requête pour obtenir tous les sujets et leurs fiches associées
$requete = "
    SELECT 
        s.suj_numero, 
        s.suj_intitule, 
        s.com_pseudo, 
        GROUP_CONCAT(f.fic_label ORDER BY f.fic_numero SEPARATOR '<br>') AS fiches
    FROM 
        t_sujet_sjt s
    LEFT JOIN 
        t_fiche_fce f ON s.suj_numero = f.suj_numero
    GROUP BY 
        s.suj_numero
    ORDER BY 
        s.suj_numero ASC";

if ($resultat = $mysqli->query($requete)) {
    echo "<table class='table table-bordered'>";
    echo "<thead class='thead-light'>";
    echo "<tr><th>Intitulé du sujet</th><th>Pseudo associé</th><th>Intitulés des fiches</th><th>Action</th></tr>";
    echo "</thead>";
    echo "<tbody>";

    // Boucle pour afficher chaque sujet avec des boutons d'action spécifiques au rôle
    while ($ligne = $resultat->fetch_assoc()) {
        echo "<tr>";
        echo "<td>" . htmlspecialchars($ligne['suj_intitule']) . "</td>";
        echo "<td>" . htmlspecialchars($ligne['com_pseudo']) . "</td>";
        echo "<td>" . (empty($ligne['fiches']) ? 'Aucune fiche pour le moment' : $ligne['fiches']) . "</td>";
        echo "<td>";
        if ($_SESSION['role'] == 'M') {
            echo "<form method='post' action=''>";
            echo "<input type='hidden' name='suj_numero' value='" . $ligne['suj_numero'] . "'>";
            echo "<button type='submit' name='action' value='supprimer' class='btn btn-danger btn-sm'>Supprimer</button>";
            echo "</form>";
        } 
        echo "</td>";
        echo "</tr>";
    }

    echo "</tbody>";
    echo "</table>";
} else {
    echo "<p>Erreur lors de l'exécution de la requête.</p>";
}

echo "</div>";
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