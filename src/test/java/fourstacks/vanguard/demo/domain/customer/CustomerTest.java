package fourstacks.vanguard.demo.domain.customer;

import fourstacks.vanguard.demo.domain.customer.exceptions.CustomerNotFoundException;
import fourstacks.vanguard.demo.domain.customer.model.Customer;
import fourstacks.vanguard.demo.domain.customer.repo.CustomerRepo;
import fourstacks.vanguard.demo.domain.customer.service.CustomerService;
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

import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomerTest {

    @MockBean
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerService customerService;

    private Customer input;
    private Customer output;

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat Dateformat = new SimpleDateFormat("MM-dd-yyyy");
        input= new Customer("Yennifer", "Campos", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        output=  new Customer("Yennifer", "Campos", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        output.setId(1l);
    }

    @Test
    @DisplayName("Customer Service create - success")
    public void createCustomer()  {
        doReturn(output).when(customerRepo).save(ArgumentMatchers.any());
        Customer returnedCustomer = customerService.create(input);
        Assertions.assertNotNull(output);
        Assertions.assertEquals(returnedCustomer.getId(), 1l);
    }

    @Test
    @DisplayName("Customer Service Find By Id - success")
    public void findCustomerByIdTest01() throws CustomerNotFoundException {
        doReturn(Optional.of(input)).when(customerRepo).findById(1l);
        Customer foundCustomer= customerService.getById(1l);
        Assertions.assertEquals(input.toString(), foundCustomer.toString());
    }

    @Test
    @DisplayName("Customer Service findByID - Fail")
    public void findCustomerByIdTest02 (){
        doReturn(Optional.empty()).when(customerRepo).findById(1l);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getById(1l);

        });
    }

    @Test
    @DisplayName("Customer Service: Get all Customer - Success")
    public void findAllCustomersTestSuccess(){
        List<Customer> customers = new ArrayList<>();
        customers.add(input);
        doReturn(customers).when(customerRepo).findAll();
        Iterable <Customer> responseCustomer= customerService.findAll();
        Assertions.assertIterableEquals(customers, responseCustomer);
    }

    @Test
    @DisplayName("Update Customer - Success")
    public void updateCustomerTest01() throws ParseException, CustomerNotFoundException {
        SimpleDateFormat Dateformat = new SimpleDateFormat("MM-dd-yyyy");
        Customer expectedCustomerUpdate = new Customer("Emmanuel", "Warrior", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        expectedCustomerUpdate.setId(1l);
        doReturn(Optional.of(input)).when(customerRepo).findById(1l);
        doReturn(expectedCustomerUpdate).when(customerRepo).save(ArgumentMatchers.any());
        Customer actualActor = customerService.update(expectedCustomerUpdate);
        Assertions.assertEquals(expectedCustomerUpdate.toString(), actualActor.toString());
    }

    @Test
    @DisplayName("Update Customer - Fail")
    public void updateCustomerTest02() throws ParseException {
        SimpleDateFormat Dateformat = new SimpleDateFormat("MM-dd-yyyy");
        Customer updatedCustomer = new Customer("Emmanuel", "Warrior", 2022l, "ycampos@gmail.com", Dateformat.parse("01-26-1997"));
        doReturn(Optional.empty()).when(customerRepo).findById(1l);
        Assertions.assertThrows(CustomerNotFoundException.class, () ->{
            customerService.update(updatedCustomer);
        });
    }


    @Test
    @DisplayName("Delete Customer")
    public void deleteCustomer(){
        doReturn(Optional.empty()).when(customerRepo).findById(1l);
        Assertions.assertThrows(CustomerNotFoundException.class, ()-> {
            customerService.delete(1l);
        });
    }

    @Test
    @DisplayName("Customer Service - FindByUserName")
    public void findByEmail() throws CustomerNotFoundException {
        String email= "ycampos@gmail.com";
        doReturn(Optional.of(output)).when(customerRepo).findByEmail(email);
        Customer actualCustomer = customerService.findByEmail(email);
        Assertions.assertEquals(email, actualCustomer.getEmail());
    }
}


