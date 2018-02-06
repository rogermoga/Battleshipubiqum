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

			//We create the ships only with type, we then later assign the gameplayers and locations
			Ship Carrier = new Ship("carrier");
			Ship Battleship = new Ship("battleship");
			Ship Submarine = new Ship("submarine");
			Ship PatrolBoat = new Ship("patrol boat");
			Ship Destroyer = new Ship("destroyer");

			Carrier.setGamePlayer(gamePlayer1);
			Battleship.setGamePlayer(gamePlayer1);
			Submarine.setGamePlayer(gamePlayer3);
			PatrolBoat.setGamePlayer(gamePlayer4);
			Destroyer.setGamePlayer(gamePlayer5);

			Carrier.setLocations(locations5);
			Battleship.setLocations(locations4);
			Submarine.setLocations(locations2);
			PatrolBoat.setLocations(locations22);
			Destroyer.setLocations(locations22);


			//We save the ships
			shipRepository.save(Carrier);
			shipRepository.save(Battleship);
			shipRepository.save(Submarine);
			shipRepository.save(PatrolBoat);
			shipRepository.save(Destroyer);

		};

	}

}

