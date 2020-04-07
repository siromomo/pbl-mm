package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.Cell;
import fudan.pbl.mm.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CellRepository extends CrudRepository<Cell, Long> {
    public Cell findCellById(Long id);
    public Cell findCellByNickname(String nickname);
}
