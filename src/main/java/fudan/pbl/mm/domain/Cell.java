package fudan.pbl.mm.domain;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;

import static fudan.pbl.mm.service.CellService.CELL_INIT_MAX_LEVEL;

@Entity
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String nickname;
    private String type;
    private int level;
    private boolean active;
    private int initLevel;

    public Cell(){
        this.level = (int)(Math.random() * CELL_INIT_MAX_LEVEL);
        this.initLevel = level;
    }
    public Cell(String type, String nickname, int level){
        this.type = type;
        this.nickname = nickname;
        this.level = level;
        this.initLevel = level;
        this.active = true;
    }

    public int getInitLevel() {
        return initLevel;
    }

    public void setInitLevel(int initLevel) {
        this.initLevel = initLevel;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

