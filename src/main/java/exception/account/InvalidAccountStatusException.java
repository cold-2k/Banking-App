package exception.account;

import exception.BankingException;

public class InvalidAccountStatusException extends BankingException {
    public InvalidAccountStatusException(String message) {
        super(message);
    }
}
