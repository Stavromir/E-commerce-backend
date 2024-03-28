package bg.softuni.Spring.ecommerce.app.service.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
