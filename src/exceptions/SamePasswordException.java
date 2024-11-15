package exceptions;

public class SamePasswordException extends RuntimeException {
    public SamePasswordException() {
        super("La contraseña nueva debe ser diferente a la actual.");
    }
}
