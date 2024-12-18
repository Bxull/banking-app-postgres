package kz.duisembek.bank.repository;

import kz.duisembek.bank.model.Account;
import kz.duisembek.bank.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveAndFindAccount() {
        // Создаем и сохраняем клиента
        Customer customer = new Customer();
        customer.setFirstName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer = customerRepository.save(customer); // Сохраняем клиента

        // Создаем и сохраняем аккаунт
        Account account = new Account();
        account.setBalance(1000.0);
        account.setCustomer(customer); // Привязываем к клиенту
        account = accountRepository.save(account); // Сохраняем аккаунт

        // Проверяем, что аккаунт успешно сохранён и извлечён
        Account retrievedAccount = accountRepository.findById(account.getId()).orElse(null);
        assertThat(retrievedAccount).isNotNull();
        assertThat(retrievedAccount.getBalance()).isEqualTo(1000.0);
        assertThat(retrievedAccount.getCustomer().getFirstName()).isEqualTo("John Doe");
    }
}
