package fourstacks.vanguard.demo.domain.expense.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fourstacks.vanguard.demo.domain.customer.model.Customer;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @ManyToOne
    private Customer customer;
    private Category category;
    private String description;

    private Instant expenseDate;

    public Expense() {
    }

    public Expense(Category category, String description) {
        this.id = id;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Instant getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Instant expenseDate) {
        this.expenseDate = expenseDate;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", customer=" + customer +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", expenseDate=" + expenseDate +
                '}';
    }




}
