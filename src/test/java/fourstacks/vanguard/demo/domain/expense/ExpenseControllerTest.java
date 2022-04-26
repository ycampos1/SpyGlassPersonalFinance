package fourstacks.vanguard.demo.domain.expense;

import fourstacks.vanguard.demo.BaseControllerTest;
import fourstacks.vanguard.demo.domain.expense.exception.ExpenseNotFoundException;
import fourstacks.vanguard.demo.domain.expense.model.Expense;
import fourstacks.vanguard.demo.domain.expense.model.Category;
import fourstacks.vanguard.demo.domain.expense.service.ExpenseService;
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
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ExpenseControllerTest extends BaseControllerTest {

    @MockBean
    private ExpenseService mockExpenseService;

    @Autowired
    private MockMvc mockMvc;

    private Expense inputExpense;
    private Expense mockResponseExpense1;
    private Expense mockResponseExpense2;

    @BeforeEach
    public void setUp() throws ParseException {
        inputExpense = new Expense(Category.EDUCATION, "test");

        mockResponseExpense1 = new Expense(Category.EDUCATION, "test");
        mockResponseExpense1.setId(1l);
        mockResponseExpense2 = new Expense(Category.EDUCATION, "test");
        mockResponseExpense2.setId(2l);
    }

    @Test
    @DisplayName(" Expense Post - success")
    public void createExpenseRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseExpense1).when(mockExpenseService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputExpense)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category", Is.is("EDUCATION")));

    }


    @Test
    @DisplayName("GET / expense - Found")
    public void getExpenseByIdTestSuccess() throws Exception {

        BDDMockito.doReturn(mockResponseExpense1).when(mockExpenseService).findById(1l);

        mockMvc.perform(get("/expense/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.category", Is.is("EDUCATION")));
    }

    @Test
    @DisplayName("Delete Expense - Success")
    public void deleteExpenseTest() throws Exception {
        BDDMockito.doNothing().when(mockExpenseService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/expense/{id}", 1))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Delete / expense / Not Found")
    public void deleteExpenseTestNotFound() throws Exception {

        BDDMockito.doThrow(new ExpenseNotFoundException("Not Found")).when(mockExpenseService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/expense/{id}", 1))
                .andExpect(status().isNotFound());
    }


}

