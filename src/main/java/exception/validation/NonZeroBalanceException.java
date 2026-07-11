package exception.validation;

import exception.account.AccountException;

public class NonZeroBalanceException extends AccountException {
    public NonZeroBalanceException(String message) {
        super(message);
    }
}
