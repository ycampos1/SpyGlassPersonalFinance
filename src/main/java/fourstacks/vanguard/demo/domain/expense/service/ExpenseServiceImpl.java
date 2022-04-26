package fourstacks.vanguard.demo.domain.expense.service;

import fourstacks.vanguard.demo.domain.expense.exception.ExpenseNotFoundException;
import fourstacks.vanguard.demo.domain.expense.model.Expense;
import fourstacks.vanguard.demo.domain.expense.repo.ExpenseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private static Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    private ExpenseRepo expenseRepo;

    @Autowired
    public ExpenseServiceImpl (ExpenseRepo expenseRepo){
        this.expenseRepo = expenseRepo;
    }

    @Override
    public Expense create(Expense expense) {
        return expenseRepo.save(expense);
    }

    @Override
    public Expense findById(Long id) throws ExpenseNotFoundException {
        Optional<Expense> optional = expenseRepo.findById(id);
        if(optional.isEmpty())
            throw new ExpenseNotFoundException("Expense not found");
        return optional.get();
    }

    @Override
    public List<Expense> getAllExpenses()  {
        return (List) expenseRepo.findAll();
    }

    @Override
    public Expense update(Expense expense) throws ExpenseNotFoundException {
        Long id = expense.getId();
        Optional<Expense> optional = expenseRepo.findById(id);
        if(optional.isEmpty())
            throw new ExpenseNotFoundException("Expense not found");
        return expenseRepo.save(expense);
    }

    @Override
    public void delete(Long id) throws ExpenseNotFoundException {
        Optional<Expense> optional = expenseRepo.findById(id);
        if(optional.isEmpty())
            throw new ExpenseNotFoundException("Expense not found");
        Expense expenseToRemove = optional.get();
       expenseRepo.delete(expenseToRemove);
    }
}
