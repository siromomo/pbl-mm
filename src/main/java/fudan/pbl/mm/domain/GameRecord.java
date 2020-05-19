package fudan.pbl.mm.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class GameRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String status;
    private int score;
    private String time;

    @ManyToMany(mappedBy = "gameRecords")
    private Set<User> users;

    public GameRecord(){}
    public GameRecord(String status, int score, String time, Set<User> users){
        this.status = status;
        this.score = score;
        this.time = time;
        this.users = users;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsers() {
        return users;
    }

    public int getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
