package salvo.salvo;


import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GamesRestController {


    @Autowired
    private PlayerRepository playerRepository;


    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createUser(@RequestParam String name, String password) {
        if (name.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "No name given"), HttpStatus.FORBIDDEN);
        }else {

            Player player = playerRepository.findByEmail(name);
            if (player != null) {
                return new ResponseEntity<>(makeMap("error", "Name already used"), HttpStatus.CONFLICT);
            }else {

                Player newPlayer = playerRepository.save(new Player(name, password));
                return new ResponseEntity<>(makeMap("id",newPlayer.getId() ), HttpStatus.CREATED);
            }
        }
    }
    // if its logged in it creates a new game and a Gameplayer
    @RequestMapping (path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createGame(Authentication authentication){
        if (authentication == null){
            return new ResponseEntity<>(makeMap("error","You need to log in"), HttpStatus.FORBIDDEN);
        }else {

            Player loggedInplayer = playerRepository.findByEmail(authentication.getName());
            Game newGame = gameRepository.save(new Game());
            GamePlayer newGamePlayer = gamePlayerRepository.save(new GamePlayer(newGame,loggedInplayer ));
            return new ResponseEntity<>(makeMap("gpid",newGamePlayer.getId()), HttpStatus.CREATED);
        }
    }

    @RequestMapping (path = "/game/{nn}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> joinGame (@PathVariable Long nn, Authentication authentication){

        if (authentication == null){
            return new ResponseEntity<>(makeMap("error","You need to log in"), HttpStatus.FORBIDDEN);
        }

        Game game = gameRepository.findOne(nn);
        if(game == null) {
            return new ResponseEntity<>(makeMap("error","No such game"), HttpStatus.NOT_FOUND);
        }

        if (game.getGameplayers().size() != 1) {
            return new ResponseEntity<>(makeMap("error", "Game is full"), HttpStatus.FORBIDDEN);
        }

        Player loggedInplayer = playerRepository.findByEmail(authentication.getName());
        for (GamePlayer gameplayer : game.getGameplayers()) {

            if(loggedInplayer.getId() == gameplayer.getId()) {
            return new ResponseEntity<>(makeMap("error", "You are already in this game"), HttpStatus.CONFLICT);
            }
        }

        GamePlayer newGamePlayer = gamePlayerRepository.save(new GamePlayer(game, loggedInplayer));
        return new ResponseEntity<>(makeMap("gpid",newGamePlayer.getId()), HttpStatus.CREATED);

    }

    //Create a map with the messages from the responseEntity when creating a new player
    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }

    @RequestMapping("/games")
    public Map<String, Object> gamesAndAuthenDTO(Authentication authentication){

        //el mapa que engloba tot
        Map<String, Object> mapDTO = new LinkedHashMap<>();

        if (authentication != null) {
            Player loggedInplayer = playerRepository.findByEmail(authentication.getName());

            // creates a map for the logged in player info and calls a function to fill it
            Map<String, Object> loggedinPlayerDTO = makeplayerDTO(loggedInplayer);

            mapDTO.put("player", loggedinPlayerDTO);

            } else {
             mapDTO.put("Error", "You Need To Log In");
            }


            List<Map<String, Object>> listdto = new ArrayList<Map<String, Object>>();
            // llenar lista
            List<Game> games = gameRepository.findAll();
            for (int i = 0; i < games.size(); i++) {
                // Crear variable para el mapa
                Map<String, Object> dtoGame;
                // llenar el mapa
                dtoGame = makeGameDTO(games.get(i));

                //pushear mapa en la lista
                listdto.add(dtoGame);
            }

            mapDTO.put("games", listdto);

        return mapDTO;
    }

    @RequestMapping("/game_view/{nn}")
    public  ResponseEntity<Object> dtoGameView(@PathVariable Long nn, Authentication authentication){

        //seleccionamos el gameplayer que nos ha dado el parametro nn
        GamePlayer gamePlayer = gamePlayerRepository.findOne(nn);

        Player loggedInplayer = playerRepository.findByEmail(authentication.getName());

        if (gamePlayer.getPlayer1().getId() != loggedInplayer.getId()){
            return new ResponseEntity<Object>(makeMap("error", "Not Authorized"), HttpStatus.UNAUTHORIZED);
        }else if (authentication == null) {

            return new ResponseEntity<Object>(makeMap("error", "No user logged in"), HttpStatus.UNAUTHORIZED);

        }else{


        Map<String, Object> mapDto = new LinkedHashMap<>();

        mapDto.put("GameplayerID", gamePlayer.getId());
        mapDto.put("CreationDate", gamePlayer.getDate());

        //creamos dos listas, una para sacar la info de los players y otra para la info de los ships
        List<Map<String, Object>> gameplayersInfo = new ArrayList<Map<String, Object>>();

        //ponemos el primer gameplayer en la lista
        gameplayersInfo.add(makeGamePlayer1DTO(gamePlayer));

        //guardamos el set de gameplayers dentro de games
        Set<GamePlayer> gamePlayerSet = gamePlayer.getGame1().getGameplayers();

        //para guardar el segundo gameplayer ejecutamos un algoritmo para ver si existe y no es el mismo que el primero
        if (gamePlayerSet.size()>1){
            for (GamePlayer gameplayer : gamePlayerSet){
                if(gameplayer.getId() != gamePlayer.getId()){
                            gameplayersInfo.add(makeGamePlayer1DTO(gameplayer));
                        }
                    }
                }

        List<Map<String, Object>> shipsInfo = new ArrayList<Map<String, Object>>();

        mapDto.put("Gameplayers", gameplayersInfo);

        for(Ship ship : gamePlayer.getShips()) {
            shipsInfo.add(MakeShipDTO(ship));
        }
        mapDto.put("Ships", shipsInfo);

        List<Map<String , Object>> player1salvoesInfo = MakeSalvoesList(gamePlayer.getSalvoes());

        Set<GamePlayer> gameplayersSet = gamePlayer.getGame1().getGameplayers();

        GamePlayer foundGamePlayer = FindGamePlayer(gameplayersSet, gamePlayer.getId());

        List<Map<String, Object>> player2salvoesInfo = MakeSalvoesList(foundGamePlayer.getSalvoes());

        List<Map<String , Object>> allPlayersSalvoesInfo = new ArrayList<Map<String , Object>>();
        allPlayersSalvoesInfo.addAll(player1salvoesInfo);

        if(player2salvoesInfo != null)  allPlayersSalvoesInfo.addAll(player2salvoesInfo);


        mapDto.put("Salvoes", allPlayersSalvoesInfo);

        return new ResponseEntity<Object>(mapDto, HttpStatus.OK);
        }
    }



    //creates the Logged in Player DTO with the username and the player ID
    private Map<String, Object> makeplayerDTO(Player player){
        Map<String, Object> playerDTO = new LinkedHashMap<String, Object>();
        playerDTO.put("id", player.getId());
        playerDTO.put("name", player.getEmail());
        return playerDTO;
    }


    private GamePlayer FindGamePlayer(Set<GamePlayer> gameplayersset, long player1id){
        GamePlayer returnGamePlayer = new GamePlayer();
        for (GamePlayer gameplayer : gameplayersset) {
            if (gameplayer.getId() != player1id) {
                returnGamePlayer = gameplayer;
            }
        }
        return returnGamePlayer;
    }

    private List<Map<String,Object>> MakeSalvoesList(Set<Salvo> salvoes){

        List<Map<String, Object>> salvoesList = new ArrayList<Map<String, Object>>();
        for (Salvo salvo : salvoes){

            salvoesList.add(MakeSalvoDTO(salvo));
        }
        return salvoesList;
    }

    private Map<String, Object> MakeSalvoDTO(Salvo salvo){
        Map<String, Object> salvoDTO = new HashMap<>();
            salvoDTO.put("turn", salvo.getTurn());
            salvoDTO.put("player", salvo.getGamePlayer().getId());
            salvoDTO.put("locations", salvo.getLocations());

        return salvoDTO;
    }


    private Map<String, Object> MakeShipDTO(Ship ship){
        Map<String, Object> shipDTO = new LinkedHashMap<>();
        shipDTO.put("type", ship.getType());

        List<String> locationsDTO = ship.getLocations();
        shipDTO.put("locations", locationsDTO);
        return  shipDTO;
    }


    private Map<String, Object> makeGamePlayer1DTO(GamePlayer gamePlayer1){
        Map<String, Object> gamePlayer1DTO = new LinkedHashMap<>();

        gamePlayer1DTO.put("ID", gamePlayer1.getId());

        Map<String, Object> playerDTO = makePlayer1DTO(gamePlayer1.getPlayer1());
        gamePlayer1DTO.put("Player", playerDTO);
        return gamePlayer1DTO;
    }

    private Map<String, Object> makePlayer1DTO(Player player1){
        Map<String, Object> player1DTO = new LinkedHashMap<>();

        player1DTO.put("ID", player1.getId());
        player1DTO.put("email", player1.getEmail());
        return player1DTO;
    }

    private Map<String, Object> makeGameDTO(Game game) {
        // crear el mapa
        Map<String,Object> gameDto = new LinkedHashMap<>();

        // llenar el mapa
        gameDto.put("ID", game.getId() );
        gameDto.put("Creation_Date", game.getDate());

        List<Map<String, Object>> gamePlayerDto;

        gamePlayerDto = makeGamePlayerDTOList(game.getGameplayers());
        gameDto.put("Game_Players", gamePlayerDto);

        //devolver el mapa
        return gameDto;
    }

    private List<Map<String,Object>> makeGamePlayerDTOList(Set<GamePlayer> gamePlayers){

        //la variable que retornarem com a dto amb tots els gameplayers
        List<Map<String, Object>> gamePlayersDto = new ArrayList<>();

        //the variable with all the Gameplayers

         for (GamePlayer gamePlayer : gamePlayers){

             Map<String, Object>  eachGamePlayer = makeGamePlayerDto(gamePlayer);

             gamePlayersDto.add(eachGamePlayer);
         }

         return gamePlayersDto;
    }

    private Map<String, Object> makeGamePlayerDto (GamePlayer gamePlayer){

        Map<String, Object>  GamePlayerDto = new LinkedHashMap<>();

        GamePlayerDto.put("ID", gamePlayer.getId());

        Map<String, Object> playerInfo;
        playerInfo =  MakePlayerDto(gamePlayer.getPlayer1());

        GamePlayerDto.put("Player", playerInfo);


        double playerScore = gamePlayer.getScore();

        GamePlayerDto.put("PlayerScore", playerScore);

        return GamePlayerDto;
    }

   private Map<String, Object> MakePlayerDto(Player player){
        Map<String, Object> eachPlayer = new LinkedHashMap<>();

        eachPlayer.put("id", player.getId() );
        eachPlayer.put("Email", player.getEmail() );


        return eachPlayer;


   }

/*    public List<Game> getAll() {
        return gameRepository.findAll();
    }*/






}