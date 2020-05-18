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
    @OneToOne
    @JsonIgnore
    private Cell cell;

    public Pack(){}
    public Pack(Cell cell){
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
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
