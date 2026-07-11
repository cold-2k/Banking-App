package exception.customer;

public class InvalidCustomerException extends CustomerException {
    public InvalidCustomerException(String message) {
        super(message);
    }
}
