package exceptions;

public class InvalidUsernameOrPasswordException extends Exception {

    public InvalidUsernameOrPasswordException() {
        super("Nombre de usuario o contraseña inválidos.");
    }

    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }
}
