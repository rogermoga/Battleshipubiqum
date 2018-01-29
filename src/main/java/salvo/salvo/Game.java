package salvo.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public long getId() {
        return id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void increaseDate() {

        this.date = Date.from(this.date.toInstant().plusSeconds(3600));
    }

    @JsonIgnore
    public Set<GamePlayer> getGameplayers() {
        return gameplayers;
    }

    public Date getDate() {
        return this.date;
    }
}
