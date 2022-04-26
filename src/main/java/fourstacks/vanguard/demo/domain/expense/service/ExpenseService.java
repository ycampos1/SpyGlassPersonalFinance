package fourstacks.vanguard.demo.domain.expense.service;

import fourstacks.vanguard.demo.domain.expense.exception.ExpenseNotFoundException;
import fourstacks.vanguard.demo.domain.expense.model.Expense;

import java.util.List;

public interface ExpenseService {
    Expense create (Expense expense);
    Expense findById (Long id) throws ExpenseNotFoundException;
    List<Expense> getAllExpenses();
    Expense update (Expense expense) throws ExpenseNotFoundException;
    void delete (Long id) throws ExpenseNotFoundException;
}
