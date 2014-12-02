package ar.edu.itba.it.paw.exceptions;

public class NotMatchingPasswordsException extends RuntimeException {
    public String getMessage(){
        return "Las contraseñas deben coincidir";
    }
}
