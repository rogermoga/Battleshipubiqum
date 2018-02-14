package salvo.salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    int turn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="GamePlayer_id")
    GamePlayer gamePlayer;

    @ElementCollection
    @Column(name="salvoLocations")
    List<String> locations = new ArrayList<>();

    public Salvo(){   }

    public Salvo(GamePlayer gamePlayer){


        turn= 1;
        this.gamePlayer = gamePlayer;
        this.gamePlayer.addSalvo(this);

    }

    public long getId() {
        return id;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
