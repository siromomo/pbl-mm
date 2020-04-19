package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.ChoiceQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChoiceQuestionRepository extends CrudRepository<ChoiceQuestion, Long> {
    public List<ChoiceQuestion> findChoiceQuestionByKeyword(String keyword);
}
