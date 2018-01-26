package salvo.salvo;

import javax.persistence.*;
import java.util.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    Date date;

    @OneToMany(mappedBy="game1", fetch=FetchType.EAGER)
    Set<GamePlayer> gameplayers = new HashSet<>();

    public Game(){
        this.date = new Date();

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void increaseDate() {

        this.date = Date.from(this.date.toInstant().plusSeconds(3600));
    }

    public Set<GamePlayer> getGameplayers() {
        return gameplayers;
    }

    public Date getDate() {
        return this.date;
    }
}
