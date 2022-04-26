package fourstacks.vanguard.demo.domain.customer;


import fourstacks.vanguard.demo.BaseControllerTest;
import fourstacks.vanguard.demo.domain.customer.exceptions.CustomerNotFoundException;
import fourstacks.vanguard.demo.domain.customer.model.Customer;
import fourstacks.vanguard.demo.domain.customer.service.CustomerService;
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
public class CustomerControllerTest extends BaseControllerTest {

    @MockBean
    private CustomerService mockCustomerService;

    @Autowired
    private MockMvc mockMvc;

    private Customer inputCustomer;
    private Customer mockResponseCustomer1;
    private Customer mockResponseCustomer2;

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat Dateformat = new SimpleDateFormat("MM-dd-yyyy");
        inputCustomer = new Customer("Yennifer", "Campos", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        mockResponseCustomer1 = new Customer("Yennifer", "Campos", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        mockResponseCustomer1.setId(1l);

        mockResponseCustomer2 = new Customer("Yenni", "Campos", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        mockResponseCustomer2.setId(2l);
    }

    @Test
    @DisplayName(" Customer Post - success")
    public void createCustomerRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseCustomer1).when(mockCustomerService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputCustomer)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("Yennifer")));

    }


    @Test
    @DisplayName("GET / customer - Found")
    public void getCustomerByIdTestSuccess() throws Exception {

        BDDMockito.doReturn(mockResponseCustomer1).when(mockCustomerService).getById(1l);

        mockMvc.perform(get("/customer/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Yennifer")));
    }








    @Test
    @DisplayName("PUT /customer/1 - Success")
    public void putCustomerTestSuccess() throws Exception {

        SimpleDateFormat Dateformat = new SimpleDateFormat("MM-dd-yyyy");
        Customer expectedCustomerUpdate = new Customer("Zulema", "Campos", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        expectedCustomerUpdate.setId(1l);
        BDDMockito.doReturn(expectedCustomerUpdate).when(mockCustomerService).update(any());
        mockMvc.perform(put("/customer", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputCustomer)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Zulema")));


    }

    @Test
    @DisplayName("Put / customer / Not Found")
    public void putCustomerTestNotFound() throws Exception {

        BDDMockito.doThrow(new CustomerNotFoundException("Not Found")).when(mockCustomerService).update(any());
        mockMvc.perform(put("/customer", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputCustomer)))
                        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Customer - Success")
    public void deleteCustomerTest() throws Exception {
        BDDMockito.doReturn(true).when(mockCustomerService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/{id}", 1))
         .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Delete / customer / Not Found")
    public void deleteCustomerTestNotFound() throws Exception {

        BDDMockito.doThrow(new CustomerNotFoundException("Not Found")).when(mockCustomerService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/{id}", 1))
                .andExpect(status().isNotFound());
    }


}

