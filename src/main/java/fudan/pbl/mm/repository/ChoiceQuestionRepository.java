package fudan.pbl.mm.repository;

import fudan.pbl.mm.domain.ChoiceQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceQuestionRepository extends CrudRepository<ChoiceQuestion, Long> {
    public List<ChoiceQuestion> findChoiceQuestionByKeyword(String keyword);

    ChoiceQuestion findChoiceQuestionById(Long id);
    @Query( value = "select * from choice_question order by rand() limit 1", nativeQuery = true)
    public ChoiceQuestion findRandomQuestion();
}
