package fudan.pbl.mm.domain;

import javax.persistence.*;

@Entity
public class CellInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String type;
    private String info;

    public CellInfo(){}
    public CellInfo(String type, String info){
        this.type = type;
        this.info = info;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public String getType() {
        return type;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setType(String type) {
        this.type = type;
    }
}
