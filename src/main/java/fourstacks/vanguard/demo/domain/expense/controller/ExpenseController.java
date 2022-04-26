package fourstacks.vanguard.demo.domain.expense.controller;

import fourstacks.vanguard.demo.domain.expense.exception.ExpenseNotFoundException;
import fourstacks.vanguard.demo.domain.expense.model.Expense;
import fourstacks.vanguard.demo.domain.expense.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    private static Logger logger = LoggerFactory.getLogger(ExpenseController.class);
    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }


    @PostMapping("")
    public ResponseEntity<Expense> createExpenseRequest(@RequestBody Expense expense){
        Expense savedExpense = expenseService.create(expense);
        ResponseEntity response = new ResponseEntity(savedExpense, HttpStatus.CREATED);
        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<Expense>> getAllExpenses(){
        List<Expense> Expenses = expenseService.getAllExpenses();
        ResponseEntity<List<Expense>> response = new ResponseEntity<>(Expenses, HttpStatus.OK);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findExpenseById(@PathVariable Long id){
        try {
            Expense Expense = expenseService.findById(id);
            ResponseEntity<?> response = new ResponseEntity<>(Expense, HttpStatus.OK);
            return response;
        } catch (ExpenseNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody Expense expense){
        try{
            Expense updatedExpense = expenseService.update(expense);
            ResponseEntity response = new ResponseEntity(updatedExpense,HttpStatus.OK);
            return response;
        } catch (ExpenseNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id){
        try{
            expenseService.delete(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (ExpenseNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
