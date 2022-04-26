package fourstacks.vanguard.demo.domain.goal.service;

import fourstacks.vanguard.demo.domain.customer.exceptions.CustomerNotFoundException;
import fourstacks.vanguard.demo.domain.customer.model.Customer;
import fourstacks.vanguard.demo.domain.goal.exceptions.GoalNotFoundException;
import fourstacks.vanguard.demo.domain.goal.model.Goal;
import org.springframework.data.repository.CrudRepository;

public interface GoalService{
    Goal create(Goal goal);
    Goal getById(Long id) throws GoalNotFoundException;
    Goal update(Goal goal) throws GoalNotFoundException;
    void delete(Long id) throws GoalNotFoundException;
    Iterable<Goal> findAll() throws GoalNotFoundException;
}
