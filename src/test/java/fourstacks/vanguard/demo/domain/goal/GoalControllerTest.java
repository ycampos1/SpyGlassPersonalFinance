package fourstacks.vanguard.demo.domain.goal;

import fourstacks.vanguard.demo.BaseControllerTest;
import fourstacks.vanguard.demo.domain.customer.model.Customer;
import fourstacks.vanguard.demo.domain.goal.exceptions.GoalNotFoundException;
import fourstacks.vanguard.demo.domain.goal.model.Goal;
import fourstacks.vanguard.demo.domain.goal.model.GoalType;
import fourstacks.vanguard.demo.domain.goal.service.GoalService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class GoalControllerTest extends BaseControllerTest {

    @MockBean
    private GoalService mockGoalService;

    @Autowired
    private MockMvc mockMvc;

    private Goal inputGoal;
    private Goal mockResponseGoal1;
    private Goal mockResponseGoal2;

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Customer customer = new Customer("John", "Smith",
                1L, "js@email.com", format.parse("01-01-2001"));
        customer.setId(1L);
        inputGoal = new Goal(customer, GoalType.SAVINGS, "test", "test",
                format.parse("05-01-2022"), 1000.00, 500.00);
        mockResponseGoal1 = new Goal(customer, GoalType.SAVINGS, "test", "test",
                format.parse("05-01-2022"), 1000.00, 500.00);
        mockResponseGoal1.setId(1l);
        mockResponseGoal2 = new Goal(customer, GoalType.SAVINGS, "test", "test",
                format.parse("05-01-2022"), 1000.00, 500.00);
        mockResponseGoal2.setId(2l);
    }

    @Test
    @DisplayName(" Goal Post - success")
    public void createGoalRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseGoal1).when(mockGoalService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/goal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputGoal)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goalType", Is.is("SAVINGS")));

    }


    @Test
    @DisplayName("GET / goal - Found")
    public void getGoalByIdTestSuccess() throws Exception {

        BDDMockito.doReturn(mockResponseGoal1).when(mockGoalService).getById(1l);

        mockMvc.perform(get("/goal/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.goalType", Is.is("SAVINGS")));
    }

    @Test
    @DisplayName("PUT /goal/1 - Success")
    public void putGoalTestSuccess() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Customer customer = new Customer("Emmanuel", "Francis",
                1L, "ef@email.com", format.parse("01-01-2001"));
        Goal expectedGoalUpdate = new Goal(customer, GoalType.DEBT_PAYOFF, "test", "test",
                format.parse("05-01-2022"), 1000.00, 500.00);
        expectedGoalUpdate.setId(1l);
        BDDMockito.doReturn(expectedGoalUpdate).when(mockGoalService).update(any());
        mockMvc.perform(put("/goal", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputGoal)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.goalType", Is.is("DEBT_PAYOFF")));


    }

    @Test
    @DisplayName("Put / goal / Not Found")
    public void putGoalTestNotFound() throws Exception {

        BDDMockito.doThrow(new GoalNotFoundException("Not Found")).when(mockGoalService).update(any());
        mockMvc.perform(put("/goal", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputGoal)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Goal - Success")
    public void deleteGoalTest() throws Exception {
        BDDMockito.doNothing().when(mockGoalService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/goal/{id}", 1))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Delete / goal / Not Found")
    public void deleteGoalTestNotFound() throws Exception {

        BDDMockito.doThrow(new GoalNotFoundException("Not Found")).when(mockGoalService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/goal/{id}", 1))
                .andExpect(status().isNotFound());
    }


}

