$(document).ready(function () {
    console.log("ready!");

    $.ajax("/api/games").done(function (result) {

        console.log(result);
        

        //creo el array donde guardare los datos que necesito de los jugadores
        var playersScores = new Array();

        console.log(playersScores);

        // recorremos el array de games
        for (var i = 0; i < result.length; i++) {

            var maxplayers = result[i].Game_Players.length;
            var gamePlayers = result[i].Game_Players;

            var game = document.createElement("li");
            var gamespan = document.createElement("span");
            var gamedate = new Date(result[i].Creation_Date).toLocaleString();

            gamespan.innerHTML = "<b>Game " + result[i].ID + "</b>  " + gamedate;
            if (maxplayers == 1 || maxplayers == 2) {
                gamespan.append(" Player 1: " + result[i].Game_Players[0].Player.Email);
            }
            if (maxplayers == 2) {
                gamespan.append(" Player 2: " + result[i].Game_Players[1].Player.Email);
            }

            game.appendChild(gamespan);
            document.getElementById("gameslist").appendChild(game);

            for (var j = 0; j < gamePlayers.length; j++) {

                var trobat = false;
                for (var k = 0; k < playersScores.length; k++) {

                    if (gamePlayers[j].Player.id == playersScores[k].playerID) {
       
                        playersScores[k].total += gamePlayers[j].PlayerScore;

                        if (gamePlayers[j].PlayerScore == 1) {
                            playersScores[k].won++;
                        } else if (gamePlayers[j].PlayerScore == 0) {
                            playersScores[k].lost++;
                        } else if (gamePlayers[j].PlayerScore == 0.5) {
                            playersScores[k].tied++;
                        }
                        trobat = true;
                    }
                }


                if (trobat == false) {
                    playersScores.push({
                        email: gamePlayers[j].Player.Email,
                        total: gamePlayers[j].PlayerScore,
                        playerID: gamePlayers[j].Player.id,
                        won: 0,
                        lost: 0,
                        tied: 0

                    });
                    if (gamePlayers[j].PlayerScore == 1) {
                        playersScores[k].won++;
                    } else if (gamePlayers[j].PlayerScore == 0.5) {
                        playersScores[k].tied++;
                    } else if (gamePlayers[j].PlayerScore == 0) {
                        playersScores[k].lost++;
                    }
                } 
            }
        }

        for (var i = 0; i < playersScores.length; i++) {
            var playerTR = document.createElement("tr");

            var playerTD = document.createElement("td");
            var totalTD = document.createElement("td");
            var wonTD = document.createElement("td");
            var lostTD = document.createElement("td");
            var tiedTD = document.createElement("td");

            playerTD.innerHTML = playersScores[i].email;
            totalTD.innerHTML = playersScores[i].total;
            wonTD.innerHTML = playersScores[i].won;
            lostTD.innerHTML = playersScores[i].lost;
            tiedTD.innerHTML = playersScores[i].tied;

            playerTR.appendChild(playerTD);
            playerTR.appendChild(totalTD);
            playerTR.appendChild(wonTD);
            playerTR.appendChild(lostTD);
            playerTR.appendChild(tiedTD);

            document.getElementById("scoretable").appendChild(playerTR);

        }
        console.log(playersScores);
    })
});
