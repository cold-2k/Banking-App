package exception.account;

public class AccountAlreadyExistsException extends AccountException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
