package fudan.pbl.mm.domain;

import antlr.collections.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class CellInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String type;
    private String info;

    @ManyToMany(mappedBy = "cellInfoSet")
    @JsonIgnore
    private Set<Pack> packSet;

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

    public Set<Pack> getPackSet() {
        return packSet;
    }

    public void setPackSet(Set<Pack> packSet) {
        this.packSet = packSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellInfo cellInfo = (CellInfo) o;
        return Objects.equals(type, cellInfo.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
