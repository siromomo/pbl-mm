package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.CellInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellInfoRepository extends CrudRepository<CellInfo, Long> {
    CellInfo findCellInfoByType(String type);
}
