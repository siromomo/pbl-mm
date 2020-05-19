package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.ChoiceQuestion;
import fudan.pbl.mm.domain.Knowledge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeRepository extends CrudRepository<Knowledge, Long> {
    Knowledge findKnowledgeById(Long id);
    @Query( value = "select * from knowledge order by rand() limit 1", nativeQuery = true)
    public ChoiceQuestion findRandomKnowledge();
}
