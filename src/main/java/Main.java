import enums.AccountType;
import model.Account;
import model.Bank;
import model.Customer;

void main() {
    Bank bank = new Bank("Straight Bank");
    Customer customer = bank.createCustomer(
            "Swastik",
            "Rai",
            "+91 0011001100",
            "swastik@gmail.com");
    Account account1 = bank.openAccount(customer.getId(), AccountType.SAVINGS);
    Account account2 = bank.openAccount(customer.getId(), AccountType.CURRENT);
    String accountNumber1 = account1.getAccountNumber();
    String accountNumber2 = account2.getAccountNumber();



}