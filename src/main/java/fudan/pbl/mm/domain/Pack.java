package fudan.pbl.mm.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<CellInfo> cellInfoSet;
    @OneToMany
    @JsonIgnore
    private Set<Cell> cells;

    public Pack(){}

    public void setCells(Set<Cell> cell) {
        this.cells = cell;
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public void addToCells(Cell cell){
        if(this.cells == null) this.cells = new HashSet<>();
        this.cells.add(cell);
    }

    public Set<CellInfo> getCellInfoSet() {
        return cellInfoSet;
    }

    public void setCellInfoSet(Set<CellInfo> cellInfoSet) {
        this.cellInfoSet = cellInfoSet;
    }
    public void addToCellInfoSet(CellInfo cellInfo){
        if(cellInfoSet == null) cellInfoSet = new HashSet<>();
        cellInfoSet.add(cellInfo);
    }
}
