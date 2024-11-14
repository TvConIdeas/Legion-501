package exceptions;

public class NonexistentUserException extends RuntimeException {
    public NonexistentUserException() {
        super("Este usuario no existe.");
    }
}
