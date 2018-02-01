package salvo.salvo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String username;
    private String email;

    @OneToMany(mappedBy="player1", fetch=FetchType.EAGER)
    private Set<GamePlayer> gameplayers = new HashSet<>();

    public Player(){}

    public Player(String Username, String Email){
        this.username = Username;
        this.email = Email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<GamePlayer> getGameplayers() {
        return gameplayers;
    }

    public String toString() {
        return username + " "+ email;
    }
    public long getId(){
        return id;
    }
}
