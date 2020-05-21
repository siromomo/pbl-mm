package fudan.pbl.mm.controller.request.auth;

import fudan.pbl.mm.domain.User;

import javax.persistence.ManyToMany;
import java.util.Set;

public class GameRecordRequest {
    private String status;
    private int score;
    private String time;
    private long packId;
    private Set<Long> userIds;

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

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setPackId(long packId) {
        this.packId = packId;
    }

    public long getPackId() {
        return packId;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }
}
