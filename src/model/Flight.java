package model;

public class Flight {

    private String airlineName;
    private String sourceCity;
    private String destCity;
    private int price;
    private String flightDuration;
    private boolean isMealAvailable;
    private boolean isExcessBaggageAvailable;

    public Flight(String airlineName, String sourceCity, String destCity, int price, String flightDuration, boolean isMealAvailable, boolean isExcessBaggageAvailable) {
        this.airlineName = airlineName;
        this.sourceCity = sourceCity;
        this.destCity = destCity;
        this.price = price;
        this.flightDuration = flightDuration;
        this.isMealAvailable = isMealAvailable;
        this.isExcessBaggageAvailable = isExcessBaggageAvailable;
    }


    public boolean isMealAvailable() {
        return isMealAvailable;
    }

    public void setMealAvailable(boolean mealAvailable) {
        isMealAvailable = mealAvailable;
    }

    public boolean isExcessBaggageAvailable() {
        return isExcessBaggageAvailable;
    }

    public void setExcessBaggageAvailable(boolean excessBaggageAvailable) {
        isExcessBaggageAvailable = excessBaggageAvailable;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }
}
