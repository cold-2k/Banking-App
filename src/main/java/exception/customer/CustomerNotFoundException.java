package exception.customer;

import exception.BankingException;

public class CustomerNotFoundException extends BankingException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
