import enums.AccountType;
import model.Account;
import model.Bank;
import model.Customer;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("Straight Bank");
        Customer customer = bank.createCustomer(
                "Swastik",
                "Rai",
                "+91 0011001100",
                "swastik@gmail.com");
        Account account1 = bank.openAccount(customer.getId(), AccountType.SAVINGS);
        Account account2 = bank.openAccount(customer.getId(), AccountType.CURRENT);

        // test code
        bank.deposit(account1.getAccountNumber(), BigDecimal.valueOf(100));
        bank.transfer(account1.getAccountNumber(), account2.getAccountNumber(), BigDecimal.valueOf(100));

        System.out.println(bank);
        System.out.println(account1);
        System.out.println(account2);
        System.out.println(customer);
    }
}