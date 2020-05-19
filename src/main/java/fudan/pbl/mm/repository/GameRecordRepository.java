package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.GameRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRecordRepository extends CrudRepository<GameRecord, Long> {
}
