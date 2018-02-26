package salvo.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BattleshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ScoreRepository scoreRepository, SalvoRepository salvoRepository, ShipRepository shipRepository, PlayerRepository playerRepository, GameRepository gameRepository,  GamePlayerRepository gamePlayerRepository) {
		return (args) -> {

			// We create the players
			Player player1 = new Player("Jack", "jBauer@gmail.com", "24");
			Player player2 = new Player("Chloe", "COBrian@gmail.com", "42");
			Player player3 = new Player("Kim", "patatas@gmail.com", "kb");
			Player player4 = new Player("David", "Palmer@gmail.com", "mole");
			Player player5 = new Player("Michelle", "Dessler@gmail.com", "patatas");
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
			List<String> locations24 = Arrays.asList("G1","G2","G3","G4");
			List<String> locations25 = Arrays.asList("I1","J5","C5");
			List<String> locations26 = Arrays.asList("B9","A9","A10");
			List<String> locations27 = Arrays.asList("C2","D2","E2","E4","E10");


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

			Salvo salvo1 = new Salvo(gamePlayer1);
			Salvo salvo2 = new Salvo(gamePlayer2);
            Salvo salvo3 = new Salvo(gamePlayer1);
            Salvo salvo4 = new Salvo(gamePlayer1);
            Salvo salvo5 = new Salvo(gamePlayer3);
            Salvo salvo6 = new Salvo(gamePlayer4);
            Salvo salvo7 = new Salvo(gamePlayer5);
            Salvo salvo8 = new Salvo(gamePlayer5);

            salvo1.setLocations(locations25);
            salvo2.setLocations(locations26);
            salvo3.setLocations(locations27);
            salvo4.setLocations(locations4);
            salvo5.setLocations(locations5);
            salvo6.setLocations(locations21);
            salvo7.setLocations(locations23);
            salvo8.setLocations(locations24);

            salvo4.setTurn(2);
            salvo5.setTurn(2);
            salvo6.setTurn(2);
            salvo7.setTurn(2);
            salvo8.setTurn(3);

            salvoRepository.save(salvo1);
			salvoRepository.save(salvo2);
            salvoRepository.save(salvo3);
            salvoRepository.save(salvo4);
            salvoRepository.save(salvo5);
            salvoRepository.save(salvo6);
            salvoRepository.save(salvo7);
            salvoRepository.save(salvo8);

            Score score1 = new Score(1);
            Score score2 = new Score(0);
            Score score3 = new Score(1);
            Score score4 = new Score(0.5);
            Score score5 = new Score(0.5);
            Score score6 = new Score(0.5);
            Score score7 = new Score(0.5);

            score1.setGame(game1);
            score2.setGame(game2);
            score3.setGame(game3);
            score4.setGame(game1);
            score5.setGame(game2);
            score6.setGame(game1);
            score7.setGame(game3);

            score1.setPlayer(player1);
            score2.setPlayer(player2);
            score3.setPlayer(player3);
            score4.setPlayer(player5);
            score5.setPlayer(player3);
            score6.setPlayer(player2);
            score7.setPlayer(player4);

            scoreRepository.save(score1);
            scoreRepository.save(score2);
            scoreRepository.save(score3);
            scoreRepository.save(score4);
            scoreRepository.save(score5);
            scoreRepository.save(score6);
            scoreRepository.save(score7);

		};

	}

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
	UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Player player = playerRepository.findByUsername(email);
                if (player != null) {
                    return new User(player.getEmail(), player.getPassword(),
                            AuthorityUtils.createAuthorityList("USER"));
                } else {
                    throw new UsernameNotFoundException("Unknown user: " + email);
                }
            }
        };
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

				.antMatchers("/web/games.html").permitAll()
				.antMatchers("/web/scripts/games.js").permitAll()
				.antMatchers("/web/styles/styles.css").permitAll()
				.antMatchers("/api/games").permitAll()
				.antMatchers("/api/login").permitAll()
				.antMatchers("/api/logout").permitAll()

				.anyRequest().fullyAuthenticated();

        http.formLogin()
                .usernameParameter("Username")// if we dont use these, the default parameters srping uses are username and password
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}

}