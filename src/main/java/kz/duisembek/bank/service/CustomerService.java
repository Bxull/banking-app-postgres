package kz.duisembek.bank.service;

import kz.duisembek.bank.model.Account;
import kz.duisembek.bank.model.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> showAllCustomers();
    public Customer getCustomerById(Long id);
    public Customer  saveCustomer(Customer customer);

    public Customer updateCustomer(Customer customer);

    public void deleteCustomer(Long id);

}
