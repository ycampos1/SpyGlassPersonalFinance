package fourstacks.vanguard.demo.domain.expense.repo;

import fourstacks.vanguard.demo.domain.expense.model.Category;
import fourstacks.vanguard.demo.domain.expense.model.Expense;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExpenseRepo extends CrudRepository<Expense, Long> {

    Optional<Expense> findById(Long id);
    Optional<Expense> findByCategory(Category category);

}
