package fudan.pbl.mm.controller.request.auth;

import fudan.pbl.mm.domain.User;

import javax.persistence.ManyToMany;
import java.util.Set;

public class GameRecordRequest {
    private String status;
    private int score;
    private String time;
    private Set<String> userNames;

    public GameRecordRequest(){}

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Set<String> getUserNames() {
        return userNames;
    }

    public void setUserIds(Set<String> userIds) {
        this.userNames = userIds;
    }
}
