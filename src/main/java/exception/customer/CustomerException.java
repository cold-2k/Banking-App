package exception.customer;

import exception.BankingException;

public class CustomerException extends BankingException {
    public CustomerException(String message) {
        super(message);
    }
}
