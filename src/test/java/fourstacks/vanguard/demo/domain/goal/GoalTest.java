package fourstacks.vanguard.demo.domain.goal;

import fourstacks.vanguard.demo.domain.customer.model.Customer;
import fourstacks.vanguard.demo.domain.goal.exceptions.GoalNotFoundException;
import fourstacks.vanguard.demo.domain.goal.model.Goal;
import fourstacks.vanguard.demo.domain.goal.model.GoalType;
import fourstacks.vanguard.demo.domain.goal.repo.GoalRepo;
import fourstacks.vanguard.demo.domain.goal.service.GoalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;

import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.doReturn;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GoalTest {

    @MockBean
    private GoalRepo goalRepo;

    @Autowired
    private GoalService goalService;

    private Goal input;
    private Goal output;

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat Dateformat = new SimpleDateFormat("MM-dd-yyyy");
        Customer customer =  new Customer("Yennifer", "Campos", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        input = new Goal(customer, GoalType.DEBT_PAYOFF, "Pay credit card", "Pay off $2,000 in 6 moths", Dateformat.parse("10-18-2022"),2000.00, 500.00);
        output = new Goal(customer, GoalType.DEBT_PAYOFF, "Pay credit card", "Pay off $2,000 in 6 moths", Dateformat.parse("10-18-2022"),2000.00, 500.00);
        output.setId(1L);
    }

    @Test
    @DisplayName("Goal Service create - success")
    public void createGoal(){
        doReturn(output).when(goalRepo).save(ArgumentMatchers.any());
        Goal returnedGoal = goalService.create(input);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(returnedGoal.getId(), 1L);
    }

    @Test
    @DisplayName("Goal Service Find By Id - success")
    public void findGoalByIdTest01() throws GoalNotFoundException {
        doReturn(Optional.of(input)).when(goalRepo).findById(1L);
        Goal foundGoal = goalService.getById(1L);
        Assertions.assertEquals(input.toString(), foundGoal.toString());
    }

    @Test
    @DisplayName("Goal Service findByID - Fail")
    public void findGoalById02(){
        doReturn(Optional.empty()).when(goalRepo).findById(1L);
        Assertions.assertThrows(GoalNotFoundException.class, () ->{
            goalService.getById(1L);
        });
    }

    @Test
    @DisplayName("Goal Service: Get all Goals - Success")
    public void findAllGoalsTest01() throws GoalNotFoundException {
        List<Goal> goals = new ArrayList<>();
        goals.add(input);
        doReturn(goals).when(goalRepo).findAll();
        Iterable<Goal> responseGoal = goalService.findAll();
        Assertions.assertIterableEquals(goals, responseGoal);
    }

    @Test
    @DisplayName("Update Goal - Success")
    public void updateGoalTest01() throws GoalNotFoundException, ParseException {
        SimpleDateFormat Dateformat = new SimpleDateFormat("MM-dd-yyyy");
        Customer customer = new Customer("Yenni", "Campos", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        Goal expectedGoalUpdate =  new Goal(customer, GoalType.DEBT_PAYOFF, "Pay credit card", "Pay off $2,000 in 6 moths", Dateformat.parse("10-18-2022"),4000.00, 700.00);
        expectedGoalUpdate.setId(1L);
        doReturn(Optional.of(input)).when(goalRepo).findById(1L);
        doReturn(expectedGoalUpdate).when(goalRepo).save(ArgumentMatchers.any());
        Goal actualGoal = goalService.update(expectedGoalUpdate);
        Assertions.assertEquals(expectedGoalUpdate.toString(), actualGoal.toString());
    }

    @Test
    @DisplayName("Update Goal - Fail")
    public void updateGoalTest02() throws GoalNotFoundException, ParseException {
        SimpleDateFormat Dateformat = new SimpleDateFormat("MM-dd-yyyy");
        Customer customer = new Customer("Yenni", "Campos", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        Goal expectedGoalUpdate =  new Goal(customer, GoalType.DEBT_PAYOFF, "Pay credit card", "Pay off $2,000 in 6 moths", Dateformat.parse("10-18-2022"),4000.00, 700.00);
        expectedGoalUpdate.setId(1l);
        doReturn(Optional.empty()).when(goalRepo).findById(1L);
        Assertions.assertThrows(GoalNotFoundException.class, () ->{
            goalService.update(expectedGoalUpdate);
        });
    }

    @Test
    @DisplayName("Delete Goal")
    public void deleteGoal(){
        doReturn(Optional.empty()).when(goalRepo).findById(1L);
        Assertions.assertThrows(GoalNotFoundException.class, () ->{
            goalService.delete(1L);
        });
    }


    @Test
    @DisplayName("Calculate Progress Test - Success")
    public void calculateProgressTestSuccess(){
        BDDMockito.doReturn(input).when(goalRepo).save(ArgumentMatchers.any());
        Goal returnedGoal = goalService.create(input);
        Assertions.assertNotNull(returnedGoal);

        Double expected = 20.00;
        Double actual = returnedGoal.getProgressPercentage();
        Assertions.assertEquals(expected,actual);
    }
}
