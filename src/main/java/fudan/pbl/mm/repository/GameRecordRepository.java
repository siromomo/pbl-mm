package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.GameRecord;
import fudan.pbl.mm.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRecordRepository extends CrudRepository<GameRecord, Long> {
    List<GameRecord> findGameRecordsByUsersContaining(User user);
}
