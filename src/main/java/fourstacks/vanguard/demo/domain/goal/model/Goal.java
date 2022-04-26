package fourstacks.vanguard.demo.domain.goal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import fourstacks.vanguard.demo.domain.customer.model.Customer;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private Customer customer;
    private GoalType goalType;
    private String name;
    private String goalDescription;
    private Date savingsDateGoal;
    private Double targetSavingsAmount;
    private Double amountAlreadySaved;
    private Double amountLeftUntilGoal;
    private Double progressPercentage;

    public Goal() {
    }

    public Goal(Customer customer, GoalType goalType, String name, String goalDescription, Date savingsDateGoal, Double targetSavingsAmount, Double amountAlreadySaved) {
        this.customer = customer;
        this.goalType = goalType;
        this.name = name;
        this.targetSavingsAmount = targetSavingsAmount;
        this.goalDescription = goalDescription;
        this.savingsDateGoal = savingsDateGoal;
        this.amountAlreadySaved = amountAlreadySaved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public Date getSavingsDateGoal() {
        return savingsDateGoal;
    }

    public void setSavingsDateGoal(Date savingsDateGoal) {
        this.savingsDateGoal = savingsDateGoal;
    }

    public Double getTargetSavingsAmount() {
        return targetSavingsAmount;
    }

    public void setTargetSavingsAmount(Double targetSavingsAmount) {
        this.targetSavingsAmount = targetSavingsAmount;
    }

    public Double getAmountAlreadySaved() {
        return amountAlreadySaved;
    }

    public void setAmountAlreadySaved(Double amountAlreadySaved) {
        this.amountAlreadySaved = amountAlreadySaved;
    }

    public Double getAmountLeftUntilGoal() {
        return amountLeftUntilGoal;
    }

    public void setAmountLeftUntilGoal(Double amountLeftUntilGoal) {
        this.amountLeftUntilGoal = amountLeftUntilGoal;
    }

    public Double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", customer=" + customer +
                ", goalType=" + goalType +
                ", name='" + name + '\'' +
                ", goalDescription='" + goalDescription + '\'' +
                ", savingsDateGoal=" + savingsDateGoal +
                ", targetSavingsAmount=" + targetSavingsAmount +
                ", amountAlreadySaved=" + amountAlreadySaved +
                ", amountLeftUntilGoal=" + amountLeftUntilGoal +
                ", progressPercentage=" + progressPercentage +
                '}';
    }
}