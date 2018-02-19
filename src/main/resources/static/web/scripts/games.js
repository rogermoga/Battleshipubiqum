
$(document).ready(function () {
    console.log("ready!");

    $.ajax("/api/games").done(function (result) {

        console.log(result);
        // recorremos el array de games
        for (var i=0; i< result.length; i++){

            var maxplayers = result[i].Game_Players.length;

            var game = document.createElement("li");
            var gamespan = document.createElement("span");

            var gamedate = new Date(result[i].Creation_Date).toLocaleString();


            gamespan.innerHTML = "<b>Game " + result[i].ID + "</b>  " + gamedate;
            if (maxplayers == 1 || maxplayers == 2){
                gamespan.append(" Player 1: " + result[i].Game_Players[0].Player.Email);
            }
            if (maxplayers == 2){
            gamespan.append(" Player 2: " + result[i].Game_Players[1].Player.Email);
            }

            game.appendChild(gamespan);
                document.getElementById("gameslist").appendChild(game);
            
            for ( var j = 0; j< maxplayers.length ; j++){
                
                var playerInfo = document.createElement("tr");
                
                var playerEmail = document.createElement("td");
                var total = document.createElement("td");
                var won = document.createElement("td");
                var lost = document.createElement("td");
                var tied = document.createElement("td");
                
                
                
                
               document.getElementById("scoretable").appendChild(playerInfo);
  
            }
            
        }
    })
});