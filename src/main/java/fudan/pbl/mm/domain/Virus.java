package fudan.pbl.mm.domain;

import fudan.pbl.mm.service.CellService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Virus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int level;
    private int radius;
    public Virus(){
        this.level = (int)(Math.random() * CellService.CELL_INIT_MAX_LEVEL);
        this.radius = 5;
    }
    public Virus(int level, int radius){
        this.level = level;
        this.radius = radius;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
