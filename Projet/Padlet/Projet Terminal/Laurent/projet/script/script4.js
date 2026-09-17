//Colors array
let colors = ['blue', 'yellow', 'black', 'red', 'brown', 'orange', 'white'];

//get button
let button = document.getElementById('button');

//add event listener
button.addEventListener('click', function(){
    //randomizer
    var randomColor = colors[Math.floor(Math.random() * colors.length)]
    //get container
    let container = document.getElementById('container');

    container.style.background = randomColor;
})
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