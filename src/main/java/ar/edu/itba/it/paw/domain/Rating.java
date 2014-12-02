package ar.edu.itba.it.paw.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Rating{

    private int hygiene;
    private int facilities;
    private int service;
    private int location;
    private int price;
    private int comfort;

    //Requerido por Hibernate
    public Rating(){}

    public Rating(int hygiene, int facilities, int service, int location, int price, int comfort) {
        this.hygiene = hygiene;
        this.facilities = facilities;
        this.service = service;
        this.location = location;
        this.price = price;
        this.comfort = comfort;
    }

    public void setHygiene(int hygiene) {
        this.hygiene = hygiene;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getComfort() {
        return comfort;
    }

    public void setComfort(int comfort) {
        this.comfort = comfort;
    }

    public void setFacilities(int facilities) {
        this.facilities = facilities;
    }

    public void setService(int service) {
        this.service = service;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getHygiene() {
		return hygiene;
	}

	public int getFacilities() {
		return facilities;
	}

	public int getService() {
		return service;
	}

	public int getLocation() {
		return location;
	}

	public double getAverageRating() {
        //Solo se tienen en cuenta los campos sobre los cuales se opinó
        int divisor = 0;
        if(hygiene != 0) divisor++;
        if(facilities != 0) divisor++;
        if(location != 0) divisor++;
        if(service != 0) divisor++;
        if(comfort != 0) divisor++;
        if(price != 0) divisor++;

        if(divisor == 0) return 0;

        double result = ((double) (hygiene + facilities + location + service + comfort + price))/divisor;

        return (double) Math.round(result * 100) / 100;
	}
}
