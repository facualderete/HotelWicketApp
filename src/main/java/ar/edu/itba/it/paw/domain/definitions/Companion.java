package ar.edu.itba.it.paw.domain.definitions;


public enum Companion {
    COUPLE("Pareja"), FRIENDS("Amigos"), FAMILY("Familia"), ALONE("Solo");

    private final String name;

    private Companion(String s) {
        name = s;
    }

    public String getName(){
        return name;
    }
}
