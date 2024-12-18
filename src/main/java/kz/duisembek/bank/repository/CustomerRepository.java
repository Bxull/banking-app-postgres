package kz.duisembek.bank.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import kz.duisembek.bank.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
