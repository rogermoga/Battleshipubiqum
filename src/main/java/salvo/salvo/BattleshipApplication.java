package salvo.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BattleshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository,  GamePlayerRepository gamePlayerRepository) {
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
			//We save the gameplayers
			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
		};

	}

}

