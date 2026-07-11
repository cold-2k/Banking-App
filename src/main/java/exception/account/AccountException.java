package exception.account;

import exception.BankingException;

public class AccountException extends BankingException {
    public AccountException(String message) {
        super(message);
    }
}
