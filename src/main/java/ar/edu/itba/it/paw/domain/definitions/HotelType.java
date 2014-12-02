package ar.edu.itba.it.paw.domain.definitions;

public enum HotelType {
    RESORT("Resort"), CASINO("Casino"), MOUNTAIN("Montaña"), MOTEL("Motel"), APART("Apart");

    private final String name;

    private HotelType(String s) {
        name = s;
    }

    public String getName(){
        return name;
    }
}
