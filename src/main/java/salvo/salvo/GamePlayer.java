package salvo.salvo;

import javax.persistence.*;
import java.util.Date;

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

    public GamePlayer(){};

    public GamePlayer(Game game, Player player){
        this.date = new Date();
        this.game1 = game;
        this.player1 = player;
    }

    public Date getDate() {
        return date;
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

    public long getId() {
        return id;
    }
}
