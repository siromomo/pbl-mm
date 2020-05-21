package fudan.pbl.mm.domain;

import fudan.pbl.mm.service.CellService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Virus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int hashId;
    private static int count = 0;
    private int level;
    private int radius;
    public Virus(){
        this.level = (int)(Math.random() * CellService.CELL_INIT_MAX_LEVEL);
        this.radius = 5;
        count++;
        hashId = count;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Virus virus = (Virus) o;
        return Objects.equals(hashId, virus.hashId);
    }

    @Override
    public int hashCode() {
        return this.hashId;
    }
}
