package fourstacks.vanguard.demo.domain.expense;

import fourstacks.vanguard.demo.domain.expense.exception.ExpenseNotFoundException;
import fourstacks.vanguard.demo.domain.expense.model.Category;
import fourstacks.vanguard.demo.domain.expense.model.Expense;
import fourstacks.vanguard.demo.domain.expense.repo.ExpenseRepo;
import fourstacks.vanguard.demo.domain.expense.service.ExpenseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static fourstacks.vanguard.demo.domain.expense.model.Category.HOME_AUTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ExpenseTest {

    @MockBean
    private ExpenseRepo expenseRepo;

    @Autowired
    private ExpenseService expenseService;

    private Category category;
    private String description;
    private Expense input;
    private Expense output;


    @BeforeEach
    public void setUp() {
        input = new Expense(HOME_AUTO, "Buy new home");
        output = new Expense(HOME_AUTO, "Buy new car");
        output.setId(1L);
    }

    @Test
    @DisplayName("ExpenseService create - Success")
    public void createExpense() {
        doReturn(output).when(expenseRepo).save(ArgumentMatchers.any());
        Expense returnedExpense = expenseService.create(input);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(returnedExpense.getId(), 1L);
    }

    @Test
    @DisplayName("ExpenseService findById - Success")
    public void findExpenseByIdTest01() throws ExpenseNotFoundException {
        doReturn(Optional.of(input)).when(expenseRepo).findById(1L);
        Expense returnExpense = expenseService.findById(1L);
        Assertions.assertEquals(input.toString(), returnExpense.toString());
    }

    @Test
    @DisplayName("ExpenseService findById - Fail")
    public void findExpenseByIdTest02() {
        doReturn(Optional.empty()).when(expenseRepo).findById(1L);
        Assertions.assertThrows(ExpenseNotFoundException.class, () -> {
            expenseService.findById(1L);
        });
    }

    @Test
    @DisplayName("Update Expense - Success")
    public void updateExpenseTest01() throws ExpenseNotFoundException {
        Expense expectedExpenseUpdate = new Expense(category, description);
        expectedExpenseUpdate.setId(1L);
        doReturn(Optional.of(input)).when(expenseRepo).findById(1L);
        doReturn(expectedExpenseUpdate).when(expenseRepo).save(ArgumentMatchers.any());
        Expense actualExpense = expenseService.update(expectedExpenseUpdate);
        Assertions.assertEquals(expectedExpenseUpdate.toString(), actualExpense.toString());
    }

    @Test
    @DisplayName("Update Expense - Fail")
    public void updateExpenseTest02() throws ExpenseNotFoundException {
        Expense updatedExpense = new Expense(category, description);
        doReturn(Optional.empty()).when(expenseRepo).findById(1L);
        Assertions.assertThrows(ExpenseNotFoundException.class, () -> {
            expenseService.update(updatedExpense);
        });
    }

    @Test
    @DisplayName("Delete Expense")
    public void deleteExpense() {
        doReturn(Optional.empty()).when(expenseRepo).findById(1L);
        Assertions.assertThrows(ExpenseNotFoundException.class, () -> {
            expenseService.delete(1L);
        });
    }
}
