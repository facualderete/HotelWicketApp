package ar.edu.itba.it.paw.domain;

public class HotelEvaluation {
    private double hygiene = 0;
    private double facilities = 0;
    private double service = 0;
    private double location = 0;
    private double price = 0;
    private double comfort = 0;
    private int hygieneCounter = 0;
    private int facilitiesCounter = 0;
    private int serviceCounter = 0;
    private int locationCounter = 0;
    private int priceCounter = 0;
    private int comfortCounter = 0;

    public double getGeneral(){

        int divisor = 0;
        double result = 0;
        if(hygiene != 0){
            result += hygiene/hygieneCounter;
            divisor++;
        }
        if(facilities != 0){
            result += facilities/facilitiesCounter;
            divisor++;
        }
        if(location != 0){
            result += location/locationCounter;
            divisor++;
        }
        if(service != 0){
            result += service/serviceCounter;
            divisor++;
        }
        if(comfort != 0){
            result += comfort/comfortCounter;
            divisor++;
        }
        if(price != 0){
            result += price/priceCounter;
            divisor++;
        }

        if(divisor == 0) return 0;

        result = result/divisor;

        return (double) Math.round(result * 100) / 100;
    }

    public double getHygiene() {
        if(hygiene == 0) return 0;
        return getRounded(hygiene/hygieneCounter);
    }

    public void setHygiene(double hygiene) {
        this.hygiene += hygiene;
        this.hygieneCounter++;
    }

    public double getFacilities() {
        if(facilities == 0) return 0;
        return getRounded(facilities/facilitiesCounter);
    }

    public void setFacilities(double facilities) {
        this.facilities += facilities;
        this.facilitiesCounter++;
    }

    public double getService() {
        if(service == 0) return 0;
        return getRounded(service/serviceCounter);
    }

    public void setService(double service) {
        this.service += service;
        this.serviceCounter++;
    }

    public double getLocation() {
        if(location == 0) return 0;
        return getRounded(location/locationCounter);
    }

    public void setLocation(double location) {
        this.location += location;
        this.locationCounter++;
    }

    public double getPrice() {
        if(price == 0) return 0;
        return getRounded(price/priceCounter);
    }

    public void setPrice(double price) {
        this.price += price;
        this.priceCounter++;
    }

    public double getComfort() {
        if(comfort == 0) return 0;
        return getRounded(comfort/comfortCounter);
    }

    public void setComfort(double comfort) {
        this.comfort += comfort;
        this.comfortCounter++;
    }

    private double getRounded(double n){
        return (double) Math.round(n * 100) / 100;
    }
}
