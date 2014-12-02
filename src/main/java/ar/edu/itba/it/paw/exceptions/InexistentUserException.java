package ar.edu.itba.it.paw.exceptions;

public class InexistentUserException extends RuntimeException {
    public String getMessage(){
        return "El usuario ingresado no existe";
    }
}
