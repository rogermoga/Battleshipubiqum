package salvo.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GamesRestController {


    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @RequestMapping("/games")
    public List<Map<String, Object>> dtoGames(){
        List<Map<String, Object>> listdto = new ArrayList<Map<String,Object>>();
        // llenar lista
        List<Game> games = gameRepository.findAll();
        for (int i = 0 ; i< games.size(); i++){
            // Crear variable para el mapa
            Map<String, Object> dtoGame;
            // llenar el mapa
            dtoGame = makeGameDTO(games.get(i));

            //pushear mapa en la lista
            listdto.add(dtoGame);
        }
        return listdto;
    }

    @RequestMapping("/game_view/{nn}")
    public  Map<String, Object> dtoGameView(@PathVariable Long nn){

        //seleccionamos el gameplayer que nos ha dado el parametro nn
        GamePlayer gamePlayer = gamePlayerRepository.findOne(nn);

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

        List<Map<String , Object>> salvoesInfo = MakeSalvoesList(gamePlayer.getSalvoes());



        mapDto.put("Salvoes", salvoesInfo);

        return mapDto;

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