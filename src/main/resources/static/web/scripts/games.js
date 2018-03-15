$(document).ready(function () {
    console.log("ready!");

    $("#password").focus();

    $("#pwcheck").click(function () {
        if ($("#pwcheck").is(":checked")) {
            $("#password").clone()
                .attr("type", "text").insertAfter("#password")
                .prev().remove();
        } else {
            $("#password").clone()
                .attr("type", "password").insertAfter("#password")
                .prev().remove();
        }
    });

   
    $("#creategame").click(function () {
        $.ajax({
            type: 'POST',
            url: '/api/games'
        }).done(function (gamplayerid) {
            console.log(gamplayerid);
            console.log("The Game Player ID is: " + gamplayerid.gpid);
            window.location.href="http://localhost:8080/web/game.html?gp=" + gamplayerid.gpid;
            
        }).fail(function () {
            alert("Game Creation failed");
            console.log("Game Creation failed");
        });

    });
    
    

    $('#signupbutton').click(function () {

        var data = {
            name: $('#username').val(),
            password: $('#password').val()
        };

        $.ajax({
            data: data,
            //timeout: 1000,
            type: 'POST',
            url: '/api/players'

        }).done(function (playerid) {
            var logindata = {
                user: data.name,
                pass: data.password
            };

            $.ajax({
                data: logindata,
                //timeout: 1000,
                type: 'POST',
                url: '/api/login'

            }).done(function (data, textStatus, jqXHR) {
                Hidebuttons();
                ClearValues();

                $.ajax("/api/games").done(function (result) {
                    console.log(result);

                    if (result.player != null) {
                        playerinfo.innerHTML = "You are logged in as : " + result.player.name.toUpperCase();
                        Hidebuttons();
                        location.reload();

                    } else {
                        playerinfo.innerHTML = "Not logged in";
                        console.log("no player logged in");
                    }
                });

                console.log("login successful")

            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert('Booh! Wrong credentials, try again!');
            });


            console.log(playerid);
        }).fail(function (d) {
            playerinfo.innerHTML = d.responseJSON.error;
            console.log(d.responseJSON.error);
        });
    });

    $('#logoutbutton').click(function () {
        $.ajax({
            timeout: 1000,
            type: 'POST',
            url: '/api/logout'

        }).done(function () {
            $("#loginbutton").show();
            $("#signupbutton").show();
            $("#loginform").show();
            $("#logoutbutton").hide();
            ClearValues();
            location.reload();

            playerinfo.innerHTML = "Not logged in";
            console.log("successfully logged out")

        }).fail(function () {
            console.log("Not able to log out")
        });

    });
    $('#loginbutton').click(function () {

        var data = {
            user: $('#username').val(),
            pass: $('#password').val()
        };
        console.log(data.user);
        $.ajax({
            data: data,
            timeout: 1000,
            type: 'POST',
            url: '/api/login'

        }).done(function (data, textStatus, jqXHR) {

            Hidebuttons();
            ClearValues();

            $.ajax("/api/games").done(function (result) {
                console.log(result);

                if (result.player != null) {
                    playerinfo.innerHTML = "You are logged in as : " + result.player.name.toUpperCase();
                    Hidebuttons();
                    location.reload();

                } else {
                    playerinfo.innerHTML = "Not logged in";
                    console.log("no player logged in");
                }
            });

            console.log("loggin successful")

        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert('Booh! Wrong credentials, try again!');
        });
    });

    $.ajax("/api/games").done(function (result) {

        var games = result.games;
        console.log(result);

        if (result.player != null) {
            playerinfo.innerHTML = "You are logged in as : " + result.player.name.toUpperCase();
            Hidebuttons();
        } else {
            playerinfo.innerHTML = "Not logged in";
            console.log("no player logged in");
        }



        //creo el array donde guardare los datos que necesito de los jugadores
        var playersScores = new Array();

        // recorremos el array de games
        for (var i = 0; i < games.length; i++) {

            var maxplayers = games[i].Game_Players.length;
            var gamePlayers = games[i].Game_Players;

            var game = document.createElement("li");
            var gamespan = document.createElement("span");
            var gamedate = new Date(games[i].Creation_Date).toLocaleString();
            var teunjoc = false;
            var currentgameplayer;
            
            if (result.player) {
                for (var j = 0; j < gamePlayers.length; j++) {
                    if (result.player.id == gamePlayers[j].Player.id) {
                        teunjoc = true;
                        currentgameplayer = gamePlayers[j].ID;
                    }
                }
            }

            if (teunjoc) {
                    //per unirte a una partida que ja estas jugant
                var joinbtn = document.createElement("BUTTON");
                joinbtn.setAttribute("type", "button");
                joinbtn.setAttribute("value", "play");
                console.log("The gameplayer is " + currentgameplayer);
                joinbtn.setAttribute("onclick", "window.location.href='http://localhost:8080/web/game.html?gp=" + currentgameplayer + "'");
                joinbtn.textContent = "Play";
                game.appendChild(joinbtn);
                
                //per unirte a una partida que no estas jugant i que nomes hi ha un jugador
            }else if(!teunjoc && gamePlayers.length == 1){
                
                var joingame =  document.createElement("BUTTON");
                joingame.setAttribute("type", "button");
                joingame.setAttribute("value", "join");
                joingame.setAttribute("class","joingamebutton");
                joingame.setAttribute("data-gameid",games[i].ID);
                joingame.textContent = "Join";
                game.appendChild(joingame);
            }

            gamespan.innerHTML = "<b>Game " + games[i].ID + "</b>  " + gamedate;
            if (maxplayers == 1 || maxplayers == 2) {
                gamespan.append(" Player 1: " + games[i].Game_Players[0].Player.Email);
            }
            if (maxplayers == 2) {
                gamespan.append(" Player 2: " + games[i].Game_Players[1].Player.Email);
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
        
         $(".joingamebutton").click(function(){
        console.log($(this).attr('data-gameid'));
             var gameid  = $(this).attr('data-gameid');
        $.ajax({
            type:'POST',
            url: '/api/game/'+ gameid + '/players'
        }).done(function(d){
            console.log(d);
            window.location.href="http://localhost:8080/web/game.html?gp=" + d.gpid;
            console.log("Game joined");
        }).fail(function(d){
            console.log(d);
            console.log("Failed to join");
            alert("Failed to join game");
        })
        
    });
    })
    
});

function ClearValues() {
    $("#username").val("");
    $("#password").val("");
}

function Hidebuttons() {
    $("#signupbutton").hide();
    $("#loginbutton").hide();
    $("#logoutbutton").show();
    $("#loginform").hide();
}
