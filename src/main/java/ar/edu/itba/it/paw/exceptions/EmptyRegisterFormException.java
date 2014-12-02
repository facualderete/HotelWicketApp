package ar.edu.itba.it.paw.exceptions;

public class EmptyRegisterFormException extends RuntimeException {
    public String getMessage(){
        return "Debe completar todos los campos para continuar";
    }
}
