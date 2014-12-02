package ar.edu.itba.it.paw.domain.definitions;

public enum Reason {
    BUSINESS("Negocios"), TURISM("Turismo");

    private final String name;

    private Reason(String s) {
        name = s;
    }

    public String getName(){
        return name;
    }
}
