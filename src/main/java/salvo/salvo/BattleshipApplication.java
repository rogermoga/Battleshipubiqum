package salvo.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BattleshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ShipRepository shipRepository, PlayerRepository playerRepository, GameRepository gameRepository,  GamePlayerRepository gamePlayerRepository) {
		return (args) -> {

			// We create the players
			Player player1 = new Player("Jack", "jBauer@gmail.com");
			Player player2 = new Player("Chloe", "COBrian@gmail.com");
			Player player3 = new Player("Kim", "patatas@gmail.com");
			Player player4 = new Player("David", "Palmer@gmail.com");
			Player player5 = new Player("Michelle", "Dessler@gmail.com");
			//We save the players in the playerRepository
			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);
			playerRepository.save(player5);

			// We create the games
			Game game1 = new Game();
			Game game2 = new Game();
			Game game3 = new Game();
			//We increase the dates of the games
			game2.increaseDate();
			game3.increaseDate();
			game3.increaseDate();
			//We save the games
            gameRepository.save(game1);
            gameRepository.save(game2);
			gameRepository.save(game3);

			//We create the gameplayers
			GamePlayer gamePlayer1 = new GamePlayer(game1,player1);
			GamePlayer gamePlayer2 = new GamePlayer(game2,player2);
			GamePlayer gamePlayer3 = new GamePlayer(game3,player3);
			GamePlayer gamePlayer4 = new GamePlayer(game1,player5);
			GamePlayer gamePlayer5 = new GamePlayer(game2,player3);
			//We save the gameplayers
			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);

			//We create the locations
			List<String> locations5 = Arrays.asList("A2","A3","A4","A5","A6");
			List<String> locations4 = Arrays.asList("B5","C5","D5","E5");
			List<String> locations2 = Arrays.asList("C6","C7");
			List<String> locations22 = Arrays.asList("B6","B7");
			List<String> locations21 = Arrays.asList("J6","J7");
			List<String> locations23 = Arrays.asList("J6","J7","J8");
			List<String> locations24 = Arrays.asList("I6","I7","I8");

			//We create the ships only with type, we then later assign the gameplayers and locations
			Ship Carrier = new Ship("carrier");
			Ship Carrier2 = new Ship("carrier");
			Ship Carrier3 = new Ship("carrier");
			Ship Carrier4 = new Ship("carrier");
			Ship Battleship = new Ship("battleship");
			Ship Battleship2 = new Ship("battleship");
			Ship Battleship3 = new Ship("battleship");
			Ship Battleship4 = new Ship("battleship");
			Ship Submarine = new Ship("submarine");
			Ship Submarine2 = new Ship("submarine");
			Ship Submarine3 = new Ship("submarine");
			Ship Submarine4 = new Ship("submarine");
			Ship PatrolBoat = new Ship("patrol boat");
			Ship PatrolBoat2 = new Ship("patrol boat");
			Ship PatrolBoat3 = new Ship("patrol boat");
			Ship PatrolBoat4 = new Ship("patrol boat");
			Ship Destroyer = new Ship("destroyer");
			Ship Destroyer2 = new Ship("destroyer");
			Ship Destroyer3 = new Ship("destroyer");
			Ship Destroyer4 = new Ship("destroyer");
			Ship Destroyer5 = new Ship("destroyer");
			Ship Destroyer6 = new Ship("destroyer");
			Ship Destroyer7 = new Ship("destroyer");

			Carrier.setGamePlayer(gamePlayer1);
			Carrier2.setGamePlayer(gamePlayer1);
			Carrier3.setGamePlayer(gamePlayer3);
			Carrier4.setGamePlayer(gamePlayer5);
			Battleship2.setGamePlayer(gamePlayer5);
			Battleship3.setGamePlayer(gamePlayer2);
			Battleship4.setGamePlayer(gamePlayer5);
			Battleship.setGamePlayer(gamePlayer1);
			Submarine.setGamePlayer(gamePlayer3);
			Submarine2.setGamePlayer(gamePlayer4);
			Submarine3.setGamePlayer(gamePlayer4);
			Submarine4.setGamePlayer(gamePlayer1);
			PatrolBoat2.setGamePlayer(gamePlayer2);
			PatrolBoat3.setGamePlayer(gamePlayer4);
			PatrolBoat4.setGamePlayer(gamePlayer4);
			PatrolBoat.setGamePlayer(gamePlayer4);
			Destroyer.setGamePlayer(gamePlayer5);
			Destroyer2.setGamePlayer(gamePlayer1);
			Destroyer3.setGamePlayer(gamePlayer4);
			Destroyer4.setGamePlayer(gamePlayer2);
			Destroyer5.setGamePlayer(gamePlayer5);
			Destroyer6.setGamePlayer(gamePlayer3);
			Destroyer7.setGamePlayer(gamePlayer1);

			Carrier.setLocations(locations5);
			Carrier2.setLocations(locations22);
			Carrier3.setLocations(locations23);
			Carrier4.setLocations(locations24);

			Battleship.setLocations(locations4);
			Battleship2.setLocations(locations22);
			Battleship3.setLocations(locations23);
			Battleship4.setLocations(locations24);
			Submarine2.setLocations(locations2);
			Submarine3.setLocations(locations4);
			Submarine4.setLocations(locations5);
			Destroyer2.setLocations(locations24);
			Destroyer3.setLocations(locations23);
			Destroyer4.setLocations(locations21);
			Destroyer5.setLocations(locations22);
			Destroyer6.setLocations(locations5);
			Destroyer7.setLocations(locations4);
			Submarine.setLocations(locations2);
			PatrolBoat.setLocations(locations22);
			PatrolBoat2.setLocations(locations4);
			PatrolBoat3.setLocations(locations23);
			PatrolBoat4.setLocations(locations5);
			Destroyer.setLocations(locations22);

			//We save the ships
			shipRepository.save(Carrier);
			shipRepository.save(Carrier2);
			shipRepository.save(Carrier3);
			shipRepository.save(Carrier4);
			shipRepository.save(Battleship);
			shipRepository.save(Battleship2);
			shipRepository.save(Battleship3);
			shipRepository.save(Battleship4);
			shipRepository.save(Submarine);
			shipRepository.save(Submarine2);
			shipRepository.save(Submarine3);
			shipRepository.save(Submarine4);
			shipRepository.save(PatrolBoat);
			shipRepository.save(PatrolBoat2);
			shipRepository.save(PatrolBoat3);
			shipRepository.save(PatrolBoat4);
			shipRepository.save(Destroyer);
			shipRepository.save(Destroyer2);
			shipRepository.save(Destroyer3);
			shipRepository.save(Destroyer4);
			shipRepository.save(Destroyer5);
			shipRepository.save(Destroyer6);
			shipRepository.save(Destroyer7);

		};

	}

}

