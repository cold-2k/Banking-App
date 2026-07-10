package model;

import enums.AccountType;
import exception.AccountAlreadyExistsException;
import exception.AccountNotFoundException;
import exception.CustomerNotFoundException;
import exception.InvalidAmountException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Bank {

    // Data fields

    private final String name;
    private final Map<String, Account> accounts;
    private final Map<String, Customer> customers;

    // Constructor

    public Bank(String name) {
        this.name = name;
        this.accounts = new HashMap<>();
        this.customers = new HashMap<>();
    }

    // Utility methods

    @Override
    public String toString() {
        return name;
    }

    // find an existing customer
    private Optional<Customer> findCustomer(String customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    // find an existing account
    private Optional<Account> findAccount(String accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }

    // amount validation
    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(
                    "Amount must be greater than zero."
            );
        }
    }

    // create a new customer in the bank
    public Customer createCustomer(String firstName, String lastName, String phoneNumber, String email) {
        Customer newCustomer = new Customer(firstName, lastName, phoneNumber, email);
        customers.put(newCustomer.getId(), newCustomer);
        return newCustomer;
    }

    // create an account for an existing customer
    public Account openAccount(String customerId, AccountType accountType) {
        // customer validation
        Customer customer = findCustomer(customerId)
                .orElseThrow(() ->
                    new CustomerNotFoundException(
                            "Customer with ID:" + customerId + " not found"
                    )
                );

        // account existence validation
        for (Account account : customer.getAccounts()) {
            if (account.getType() == accountType) {
                throw new AccountAlreadyExistsException(
                        accountType + " account already exists for " + customer.getFirstName()
                );
            }
        }

        // create a new account
        Account newAccount = new Account(customer, accountType);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        customer.addAccount(newAccount);
        return newAccount;
    }

    // amount deposit into an account
    public void deposit(String accountNumber, BigDecimal amount) {
        validateAmount(amount);

        Account account = findAccount(accountNumber).orElseThrow(()->
                new AccountNotFoundException("Account with ID:" + accountNumber + " not found")
        );
        account.deposit(amount);
    }

    // amount withdraw from an account
    public void withdraw(String accountNumber, BigDecimal amount) {
        validateAmount(amount);

        Account account = findAccount(accountNumber).orElseThrow(()->
                new AccountNotFoundException("Account with ID:" + accountNumber + " not found")
        );

        // check for sufficient balance
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InvalidAmountException("Insufficient balance in account with ID:" + accountNumber);
        }

        account.withdraw(amount);
    }

    // amount transfer from one account to another
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        validateAmount(amount);
        withdraw(fromAccountNumber, amount);
        deposit(toAccountNumber, amount);
    }

    // Getters

    public String getName() {
        return name;
    }
}
