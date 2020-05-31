package fudan.pbl.mm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class GameRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String status;
    private int score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToMany(mappedBy = "gameRecords")
    @JsonIgnore
    private Set<User> users;

    public GameRecord(){}
    public GameRecord(String status, int score, Date time, Set<User> users){
        this.status = status;
        this.score = score;
        this.time = time;
        this.users = users;
    }

    public void setTime(Date time) {
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

    public Date getTime() {
        return time;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
