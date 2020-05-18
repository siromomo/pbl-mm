package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.Cell;
import fudan.pbl.mm.domain.Pack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackRepository extends CrudRepository<Pack, Long> {
    Pack findPackByCellsContaining(Cell cell);
    Pack findPackByIdGreaterThan(Long id);
}
