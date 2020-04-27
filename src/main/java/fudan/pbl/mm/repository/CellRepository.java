package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.Cell;
import fudan.pbl.mm.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CellRepository extends CrudRepository<Cell, Long> {
    public Cell findCellById(Long id);
    public Cell findCellByNickname(String nickname);
    List<Cell> findCellsByUser(User user);
}
