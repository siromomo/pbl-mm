package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.Cell;
import fudan.pbl.mm.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserById(Long id);
    User findByUsername(String username);
}
