package model;

import enums.AccountStatus;
import enums.AccountType;
import exception.account.AccountAlreadyExistsException;
import exception.account.AccountNotFoundException;
import exception.account.InvalidAccountException;
import exception.account.InvalidAccountStatusException;
import exception.customer.CustomerNotFoundException;
import exception.validation.InvalidAmountException;
import exception.validation.NonZeroBalanceException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Bank {

    // ---------- Data fields ----------

    private final String name;
    private final Map<String, Account> accounts;
    private final Map<String, Customer> customers;

    // ---------- Constructor ----------

    public Bank(String name) {
        this.name = name;
        this.accounts = new HashMap<>();
        this.customers = new HashMap<>();

        IO.println("Welcome to the \"" + this.name + "\" bank!");
    }

    // ---------- Utility methods ----------

    // 1. Validations

        // account state validation
        private void validateAccountStatus(Account account, AccountStatus accountStatus) {
            // account must be ACTIVE to proceed with transaction
            if (account.getStatus() != accountStatus) throw new InvalidAccountStatusException(
                    "Account is not " + accountStatus.name()
            );
        }

        // amount validation to be greater than zero for transactions
        private void validateNonZeroAmount(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException(
                    "Amount must be greater than zero."
            );
        }

        // validate account balance is zero
        private void validateZeroBalance(Account account) {
            if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) throw new NonZeroBalanceException(
                "Account balance must be zero"
            );
        }

        // validate account is not closed
        private void validateAccountIsNotClosed(Account account) {
            if (account.getStatus() == AccountStatus.CLOSED) {
                throw new InvalidAccountStatusException(
                        "Account is already closed");
            }
        }

        // validate accounts are different
        private void validateDifferentAccount(String accountNumber1, String accountNumber2) {
            if (accountNumber1.equals(accountNumber2)) throw new InvalidAccountException(
                    // mentioned transfer because the requirement of different accounts is required for it only as of now
                    "Accounts must be different to complete the transfer"
            );
        }

    // 2. Account Modifications

        // account status modification
        private void changeAccountStatus(Account account, AccountStatus status) {
            if (account.getStatus() == status) {
                throw new InvalidAccountException("Account is already " + status);
            }

            account.setStatus(status);
            IO.println("Account " + account.getAccountNumber() + " is now " + status);
        }


    // 3. Bank Operations

        // find an existing customer
        private Optional<Customer> findCustomer(String customerId) {
            return Optional.ofNullable(customers.get(customerId));
        }

        // find an existing account
        private Optional<Account> findAccount(String accountId) {
            return Optional.ofNullable(accounts.get(accountId));
        }

        // create a new customer in the bank
        public Customer createCustomer(String firstName, String lastName, String phoneNumber, String email) {
            Customer newCustomer = new Customer(firstName, lastName, phoneNumber, email);
            customers.put(newCustomer.getId(), newCustomer);
            return newCustomer;
        }

        // create an account for an existing customer
        public Account openAccount(String customerId, AccountType accountType) {
            // customer existence validation
            Customer customer = findCustomer(customerId)
                    .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer with ID:" + customerId + " not found"
                        )
                    );

            // account existence validation for given account type
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
            Account account = findAccount(accountNumber).orElseThrow(()->
                    new AccountNotFoundException("Account with ID:" + accountNumber + " not found")
            );

            // Validations
            validateNonZeroAmount(amount);
            validateAccountStatus(account, AccountStatus.ACTIVE);

            account.credit(amount);
        }

        // amount withdraw from an account
        public void withdraw(String accountNumber, BigDecimal amount) {
            Account account = findAccount(accountNumber).orElseThrow(()->
                    new AccountNotFoundException("Account with ID:" + accountNumber + " not found")
            );

            // Validations
            validateNonZeroAmount(amount);
            validateAccountStatus(account, AccountStatus.ACTIVE);

            // check for sufficient balance
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InvalidAmountException("Insufficient balance in account with ID:" + accountNumber);
            }

            account.debit(amount);
        }

        // amount transfer from one account to another
        public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
            validateDifferentAccount(fromAccountNumber, toAccountNumber);
            withdraw(fromAccountNumber, amount);
            deposit(toAccountNumber, amount);
        }

    // 4. Miscellaneous

        // overridden toString
        @Override
        public String toString() {
            return name;
        }

    // 5. Getters and Setters

        public String getName() {
        return name;
    }
}
