package fourstacks.vanguard.demo.domain.customer.repo;

import fourstacks.vanguard.demo.domain.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

    Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Customer> findByEmail(String email);

}
