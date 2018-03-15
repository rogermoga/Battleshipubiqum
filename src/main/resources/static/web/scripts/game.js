$(document).ready(function () {
    console.log("ready!");
    
      $('#logoutbutton').click(function (){
        $.ajax({
            timeout: 1000,
            type: 'POST',
            url: '/api/logout'
            
        }).done(function(){
            
            window.location.href="/web/games.html";
            console.log("successfully logged out");
            
        }).fail(function(){
            console.log("Not able to log out");
        });
      
    });

    //var gp = getParameterByName("gp");
    var gp = getURL();
    console.log("Gameplayer nº:" + gp);

    createTable("shiptable");

    createTable("salvotable");


    $.getJSON('/api/game_view/' + gp, function (data) {
        console.log(data);

        console.log("The number of players is " + data.Gameplayers.length);
        console.log("The email of the first player is: " + data.Gameplayers[0].Player.email);

        ShowPlayersInfo(data.Gameplayers);

        ShowShipLocations(data.Ships);

        ShowSalvoes(data.Salvoes, data.GameplayerID, data.Ships);

    });

});

function ShowSalvoes(salvoes, gameplayer1id, ships) {
    for (var i = 0; i < salvoes.length; i++) {

        //shows my own shots
        if (gameplayer1id == salvoes[i].player) {
            for (var j = 0; j < salvoes[i].locations.length; j++) {

                var loc = salvoes[i].locations[j];
                document.getElementById("S" + loc).classList.add("shotLoc");

            }
        }else{
        // shows the shots by the other player
            for (var j = 0; j < salvoes[i].locations.length; j++) {
                var loc = salvoes[i].locations[j];
                
                if (IsHit(loc, ships)) {
                    document.getElementById("U" + loc).classList.add("opponentHitLoc");
                    document.getElementById("U" + loc).innerHTML = salvoes[i].turn;
                    
                } else {
                    document.getElementById("U" + loc).classList.add("opponentShotLoc");
                }
            }
        }
    }

}

function IsHit(loc, ships) {
    
   for (var i = 0; i < ships.length; i++) {

        for (var j = 0; j < ships[i].locations.length; j++) {

            if (loc == ships[i].locations[j]){
                return true;
            }
            
        }
    }
    return false;
}

function ShowShipLocations(ships) {

    for (var i = 0; i < ships.length; i++) {

        for (var j = 0; j < ships[i].locations.length; j++) {

            console.log(ships[i].locations[j]);

            var celdaBarco = ships[i].locations[j];
            document.getElementById("U" + celdaBarco).classList.add("barco");
        }
    }
}
//sirve para cuando hay varios parametros pero de momento al funcion getURl es mucho mas sencilla
/*function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}*/
function getURL() {
    return window.location.search.split("=")[1];
}

function ShowPlayersInfo(gameplayers) {



    var player1span = document.createElement("span");

    var player2span = document.createElement("span");

    var noPlayerSpan = document.createElement("span");

    player1span.innerHTML = gameplayers[0].Player.email + " (you)";

    if (gameplayers.length > 1) {
        player2span.innerHTML = gameplayers[1].Player.email;

        document.getElementById("player2").appendChild(player2span);
    } else {
        noPlayerSpan.innerHTML = "Todavia no hay oponente";
        document.getElementById("player2").appendChild(noPlayerSpan);
    }

    document.getElementById("player1").appendChild(player1span);


}

function createTable(tabla) {
    var tablerows = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
    var tablecolumns = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];


    for (var i = -1; i < tablerows.length; i++) {

        var onerow = document.createElement("tr");

        for (var j = -1; j < tablecolumns.length; j++) {

            if (j == -1 && i == -1) {
                var id = "";
            } else if (j == -1) {
                var id = tablerows[i];
            } else if (i == -1) {
                var id = tablecolumns[j];
            } else {

                var id = tablerows[i] + tablecolumns[j];

            }

            var onecell = document.createElement("td");

            onecell.classList.add("casilla");

            if (tabla == "shiptable") onecell.id = "U" + id;
            else onecell.id = "S" + id;

            if (j == -1 || i == -1) {
                onecell.innerHTML = id;
            }
            onerow.appendChild(onecell);
        }
        document.getElementById(tabla).appendChild(onerow);
    }
}
