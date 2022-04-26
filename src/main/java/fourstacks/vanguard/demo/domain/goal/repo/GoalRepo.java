package fourstacks.vanguard.demo.domain.goal.repo;

import fourstacks.vanguard.demo.domain.goal.model.Goal;
import fourstacks.vanguard.demo.domain.goal.model.GoalType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GoalRepo  extends CrudRepository<Goal, Long> {
    Optional<Goal> findById(Long id);

}
