package kz.duisembek.bank.service.impl;


import kz.duisembek.bank.model.Customer;
import kz.duisembek.bank.repository.CustomerRepository;
import kz.duisembek.bank.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private Customer customer1;
    private Customer customer2;
    @BeforeEach
    void setUp(){
        customer1 = new Customer();
        customer1.setId(1L);

        customer2 = new Customer();
        customer2.setId(2L);
    }

    @Test
    void showAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerServiceImpl.showAllCustomers();

        assertNotNull(customers);
        assertEquals(2, customers.size());
        assertTrue(customers.contains(customer1));
        assertTrue(customers.contains(customer2));
    }

    @Test
    void testGetCustomerById_ShouldReturnCustomer_WhenFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        Customer result = customerServiceImpl.getCustomerById(1L);

        assertNotNull(result);
        assertEquals(customer1.getId(), result.getId());
        assertEquals(customer1.getFirstName(), result.getFirstName());
    }
    @Test
    void testGetCustomerById_ShouldReturnNull_WhenNotFound() {
        when(customerRepository.findById(3L)).thenReturn(Optional.empty());

        Customer result = customerServiceImpl.getCustomerById(3L);

        assertNull(result);
    }

    @Test
    void testSaveCustomer_ShouldSaveAndReturnCustomer() {
        // Arrange
        when(customerRepository.save(customer1)).thenReturn(customer1);

        // Act
        Customer result = customerServiceImpl.saveCustomer(customer1);

        // Assert
        assertNotNull(result);
        assertEquals(customer1.getId(), result.getId());
        assertEquals(customer1.getFirstName(), result.getFirstName());
        verify(customerRepository, times(1)).save(customer1);
    }

    @Test
    void testUpdateCustomer_ShouldUpdateAndReturnCustomer() {
        // Arrange
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.save(customer1)).thenReturn(customer1);

        // Act
        Customer result = customerServiceImpl.updateCustomer(customer1);

        // Assert
        assertNotNull(result);
        assertEquals(customer1.getId(), result.getId());
        verify(customerRepository, times(1)).save(customer1);
    }
    @Test
    void testUpdateCustomer_ShouldReturnNull_WhenCustomerNotFound() {
        // Arrange
        when(customerRepository.existsById(1L)).thenReturn(false);

        // Act
        Customer result = customerServiceImpl.updateCustomer(customer1);

        // Assert
        assertNull(result);
        verify(customerRepository, times(0)).save(customer1);
    }

    @Test
    void testDeleteCustomer_ShouldDeleteCustomer_WhenCalled() {
        // Act
        customerServiceImpl.deleteCustomer(1L);

        // Assert
        verify(customerRepository, times(1)).deleteById(1L);
    }
}
