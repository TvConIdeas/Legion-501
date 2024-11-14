package exceptions;

public class UsernameUnavailableException extends RuntimeException {
    public UsernameUnavailableException() {
        super("Nombre de usuario existente.");
    }
}
