package fourstacks.vanguard.demo.domain.customer.service;

import fourstacks.vanguard.demo.domain.customer.exceptions.CustomerNotFoundException;
import fourstacks.vanguard.demo.domain.customer.model.Customer;
import fourstacks.vanguard.demo.domain.customer.repo.CustomerRepo;
import fourstacks.vanguard.demo.domain.goal.model.Goal;
import fourstacks.vanguard.demo.domain.goal.service.GoalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private CustomerRepo customerRepo;
    private GoalService goalService;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo){
        this.customerRepo= customerRepo;
    }

    @Override
    public Customer create(Customer customer) {
        customer.setId(customer.getId());
        return customerRepo.save(customer);
    }

    @Override
    public Customer createGoal(Customer customer, Goal goal) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(customer.getId());
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("Customer doesn't exist");
        Customer customer1 = customerOptional.get();
        List<Goal> goalList = customer1.getGoals();
        goal = goalService.create(goal);
        goalList.add(goal);
        customer1.setGoals(goalList);
        return customerRepo.save(customer1);
    }


    @Override
    public Customer getById(Long id) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("Customer doesn't exist");
        return customerOptional.get();
    }

    @Override
    public Customer getByFullName(String firstName, String lastName) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findByFirstNameAndLastName(firstName, lastName);
        if (customerOptional.isEmpty())
            throw new CustomerNotFoundException("Customer not found");
        return customerOptional.get();
    }

    @Override
    public Customer update(Customer customer) throws CustomerNotFoundException {
        Long id = customer.getId();
        Optional<Customer> customerOptional= customerRepo.findById(id);
        if (customerOptional.isEmpty())
            throw new CustomerNotFoundException("Customer not found");
        return customerRepo.save(customer);
    }

    @Override
    public Boolean delete(Long id) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("Customer not found");
        customerRepo.delete(customerOptional.get());
        return true;
    }

    @Override
    public Iterable<Customer> findAll() {
        return customerRepo.findAll();
    }

    @Override
    public Iterable<Goal> findAllGoalsByCustomer(Customer customer) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(customer.getId());
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("Customer not found");
        Customer customer1 = customerOptional.get();
        return customer1.getGoals();
    }

    @Override
    public Customer findByEmail(String email) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findByEmail(email);
        if(customerOptional.isEmpty())
            throw new CustomerNotFoundException("Customer not found");
        return customerOptional.get();
    }


}