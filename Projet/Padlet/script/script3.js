let sIndex = 0;

        
function showImage(n) {
    sIndex = n
    displaySlide()
}
function nextSlide(n) {
    sIndex = sIndex + n;
    displaySlide();
}

function displaySlide() {
    let i;
    let slides = document.getElementsByClassName("slides");

    if (sIndex >= slides.length) {
        sIndex = 0;
    }

    if (sIndex < 0) {
        sIndex = slides.length - 1;
    }

    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }

    slides[sIndex].style.display = "block";
}
function openPage() {
    var x = document.getElementById("search").value;

    if ((x === "Option")||(x ==="option")) {
        window.open("./option.html");
    }

    if ((x === "Jeux") || (x ==="jeux")) {
        window.open("./jeu.html");
    }
    if ((x === "Vetement") || (x ==="vetement")) {
        window.open("./vetement.html");
    }
    if ((x === "Album") || (x ==="album")) {
        window.open("./album.html");
    }
    if ((x === "Accueil") ||( x ==="accueil")) {
        window.open("../index.html");
    }
	if ((x === "Diaporama" )|| (x ==="diaporama")) {
        window.open("./diaporama.html");
    }

}