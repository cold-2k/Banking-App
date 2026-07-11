package exception.validation;

import exception.BankingException;

public class ValidationException extends BankingException {
    public ValidationException(String message) {
        super(message);
    }
}
