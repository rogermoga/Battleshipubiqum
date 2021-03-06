package salvo.salvo;

import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Game_id")
    private Game game1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Player_id")
    private Player player1;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    Set<Ship> ships = new HashSet<>();

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    Set<Salvo> salvoes = new HashSet<>();


    public GamePlayer(){}

    public GamePlayer(Game game, Player player1){
        this.date = new Date();
        this.game1 = game;
        this.player1 = player1;

    }

    public Date getDate() {
        return date;
    }

    public void addShip(Ship ship){
    ships.add(ship);
    }

    public void addSalvo(Salvo salvo){
        salvoes.add(salvo);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Game getGame1() {
        return game1;
    }

    public void setGame1(Game game1) {
        this.game1 = game1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public long getId() {
        return id;
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(Set<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    public double getScore(){
        Score score = player1.getScore(game1);
        if (score != null) {
            return score.getScore();
        }else{
            return 0;
        }
    }
}
