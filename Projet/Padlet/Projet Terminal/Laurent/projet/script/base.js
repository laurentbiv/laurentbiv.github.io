//Recuperation du document

let xmlhttp = new XMLHttpRequest();
function loadXMLDoc() {
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            fetchData();
        }
    };
    xmlhttp.open("GET", "../data/base.xml", true);
    xmlhttp.send();
}
//on recupere juste le tableau on peut pas modifier
function fetchData() {
    let i;
    let xmlDoc = xmlhttp.responseXML;
    let table = "<tr><th>PARTENAIRE</th><th>FONDATEUR</th></tr>";
    let x = xmlDoc.getElementsByTagName("BOOK");
    for (i = 0; i < x.length; i++) {
        table += "<tr><td>" +
        x[i].getElementsByTagName("TITLE")[0].childNodes[0].nodeValue +
        "</td><td>" +
        x[i].getElementsByTagName("AUTHOR")[0].childNodes[0].nodeValue +
        "</td>" +
        "<td><button type=\"button\" onclick=\"editBook(" +
        x[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue + ")\">" +
        "Edit</button></td>" +
        "<td><button type=\"button\" onclick=\"deleteBook(" +
        x[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue + ")\">" +
        "Delete</button></td>" +                
        "</tr>";

    }
    document.getElementById("data").innerHTML = table;
}
//on recupere le U editer le book  lorsquon clique sur edit sa cree un tableau
function editBook(id) {
    let tblBook = document.getElementById("tblBook");
    let txtTitle = document.getElementById("txtTitle");
    let txtAuthor = document.getElementById("txtAuthor");
    let hId = document.getElementById("hId");

    let xmlDoc = xmlhttp.responseXML;
    let books = xmlDoc.getElementsByTagName("BOOK");
    let book;

    for (i = 0; i < books.length; i++) {
        if (books[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue == id) {
            book = books[i];
        }
    }

    tblBook.style.display = "block";
    hId.value = book.getElementsByTagName("ID")[0].childNodes[0].nodeValue;
    txtTitle.value = book.getElementsByTagName("TITLE")[0].childNodes[0].nodeValue;
    txtAuthor.value = book.getElementsByTagName("AUTHOR")[0].childNodes[0].nodeValue;
} 

//ajouter les information dans le tableau  sa modifier
function updateBook() {
    let xmlDoc = xmlhttp.responseXML;
    let id = document.getElementById("hId").value;
    let books = xmlDoc.getElementsByTagName("BOOK");
    let book;

    for (i = 0; i < books.length; i++) {
        if (books[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue == id) {
            book = books[i];
        }
    }

    let txtTitle = document.getElementById("txtTitle");
    let txtAuthor = document.getElementById("txtAuthor");

    book.getElementsByTagName("TITLE")[0].childNodes[0].nodeValue = txtTitle.value;
    book.getElementsByTagName("AUTHOR")[0].childNodes[0].nodeValue = txtAuthor.value;

    fetchData();
}
//supprime un element en particulier
function deleteBook(id) {
    let xmlDoc = xmlhttp.responseXML;
    let books = xmlDoc.getElementsByTagName("BOOK");
    let book;

    for (i = 0; i < books.length; i++) {
        if(books[i].getElementsByTagName("ID")[0].childNodes[0].nodeValue == id) {
            book = books[i];
        }
    }
    
    xmlDoc.documentElement.removeChild(book);
    fetchData();
}
//sauvergarde autrement enregistre
function makeTextFile (text) {
    let textFile = null;
    let data = new Blob([text], { type: 'text/plain' });

    if (textFile !== null) {
        window.URL.revokeObjectURL(textFile);
    }

    textFile = window.URL.createObjectURL(data);

    return textFile;
}

function saveBook() {                
    let create = document.getElementById('btnSave');

    let link = document.createElement('a');
    link.setAttribute('download', 'dwc_bdd.xml');
    
    const s = new XMLSerializer();

    link.href = makeTextFile(s.serializeToString(xmlhttp.responseXML));
    document.body.appendChild(link);

    window.requestAnimationFrame(function () {
        let event = new MouseEvent('click');
        link.dispatchEvent(event);
        document.body.removeChild(link);
    });
}

//bouton add
function addBook() { 
    add.style.display = "block";
    let xmlDoc = xmlhttp.responseXML;
    let books = xmlDoc.getElementsByTagName("BOOK");

    let book = xmlDoc.createElement("BOOK");    
    let id = xmlDoc.createElement("ID");
    let title = xmlDoc.createElement("TITLE");
    let author = xmlDoc.createElement("AUTHOR");

    let id_Text = xmlDoc.createTextNode(books.length+1);
    id.appendChild(id_Text);
    let title_Text = xmlDoc.createTextNode("Test Title");
    title.appendChild(title_Text);
    let author_Text = xmlDoc.createTextNode("Test Author");
    author.appendChild(author_Text);

    book.append(id);
    book.appendChild(title);
    book.appendChild(author);

    let library = xmlDoc.getElementsByTagName("LIBRARY")[0];
    library.appendChild(book);
    
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