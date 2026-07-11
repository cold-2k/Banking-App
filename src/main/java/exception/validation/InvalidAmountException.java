package exception.validation;

import exception.account.AccountException;

public class InvalidAmountException extends AccountException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
