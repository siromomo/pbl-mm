package fudan.pbl.mm.domain;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;

import java.util.Calendar;

import static fudan.pbl.mm.service.CellService.CELL_INIT_MAX_LEVEL;

@Entity
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private static final int NUM_OF_TYPES = 8;

    @Column(unique = true)
    private String nickname;
    private String type;
    private int level;
    @Column(columnDefinition = "boolean default true")
    private boolean active;
    private int initLevel;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pack_id", referencedColumnName = "id")
    Pack pack;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName="id")
    private User user;

    public Cell(){
        this.level = (int)(Math.random() * CELL_INIT_MAX_LEVEL);
        this.initLevel = level;
        this.active = true;
    }
    public Cell(String type, String nickname, int level){
        this.type = type;
        this.nickname = nickname;
        this.level = level;
        this.initLevel = level;
        this.active = true;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public boolean isPackFilled() {
        return pack != null && pack.getCellInfoSet() != null
     && pack.getCellInfoSet().size() >= NUM_OF_TYPES;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Cell)) return false;
        return ((Cell) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return (int)(this.id % Integer.MAX_VALUE);
    }
}

