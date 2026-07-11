package exception.account;

public class AccountNotFoundException extends AccountException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
