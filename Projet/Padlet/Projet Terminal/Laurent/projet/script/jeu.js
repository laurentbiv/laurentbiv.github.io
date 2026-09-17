let gagne = 0;
let perdu = 0; 
let choix=["pierre","feuille","ciseau"];
let gameStatus = document.getElementById("gameStatus");
let gameScore = document.getElementById("gameScore");
let pierres = document.getElementById("pierre");
let feuilles = document.getElementById("feuille");
let ciseaus = document.getElementById("ciseau");

function runGame(userChoix){
let computerChoix = choix[Math.floor(Math.random() * choix.length)]
  
switch(userChoix + '_' + computerChoix){
    case 'pierre_feuille':
    case 'feuille_ciseau':
    case 'ciseau_pierre':
        perdu+= 1;
        gameStatus.innerHTML =`Vous: ${userChoix} | ordinateur: ${computerChoix} -> ordinateur gagne`
        break;
    case 'pierre_ciseau':
    case 'feuille_pierre':
    case 'ciseau_feuille':
        gagne+=1;
        gameStatus.innerHTML =`Vous: ${userChoix} | ordinateur: ${computerChoix} -> Vous gagne`;
        break;

    case 'pierre_pierre':
    case 'feuille_feuille':
    case 'ciseau_ciseau':
        gameStatus.innerHTML =`Vous: ${userChoix} | ordinateur: ${computerChoix} -> Egalite`;
        break;
    }
    gameScore.innerHTML = `Vous: ${gagne} | ordinateur: ${perdu}`;
}

pierres.addEventListener("click",() => runGame("pierre"));
feuilles.addEventListener("click",() => runGame("feuille"));
ciseaus.addEventListener("click",() => runGame("ciseau"));
  
