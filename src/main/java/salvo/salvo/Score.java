package salvo.salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Game_id")
    Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Player_id")
    Player player;

    double score;

    Date date;

    public Score(){}

    public Score( double score){
        this.score = score;
        date = new Date();
    }

    public long getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
