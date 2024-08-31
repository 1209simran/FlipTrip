package service.filter;

import dao.FlightDao;
import model.Flight;
import service.IFilter;

import java.util.List;

public class CheapestFlightFilter implements IFilter {

    private final FlightDao flightDao;

    public CheapestFlightFilter() {
        this.flightDao = FlightDao.getInstance();
    }

    //dfs
    @Override
    public void filter(String source, String dest) {
        List<List<Flight>> flights = flightDao.findFlightsBetweenToandFrom(source, dest);
        minPriceFlight(flights, source, dest);
    }

    private void minPriceFlight(List<List<Flight>> flights, String source, String dest) {
        List<Flight> resFlight = null;
        int minPrice = Integer.MAX_VALUE;
        int minHop = Integer.MAX_VALUE;
        for (List<Flight> flightList : flights) {
            int totalPriceOfTrip = 0;
            for (Flight fl : flightList)
                totalPriceOfTrip += fl.getPrice();
            if (minPrice > totalPriceOfTrip)
                minPrice = totalPriceOfTrip;
        }
        for (List<Flight> flightList : flights) {
            int totalPriceOfTrip = 0;
            for (Flight fl : flightList)
                totalPriceOfTrip += fl.getPrice();
            if (minPrice == totalPriceOfTrip && minHop > flightList.size()) {
                minHop = flightList.size();
                resFlight = flightList;
            }
        }
        showFlights(resFlight, source, dest, minPrice, minHop);
    }

    private void showFlights(List<Flight> resFlight, String source, String dest, int price, int hops) {
        if (resFlight != null) {
            System.out.println("Found flight from " +
                    source + " -> " + dest + " with " + price + " price and " + hops + " hops");
            resFlight.forEach(flight -> {
                System.out.println(flight.getSourceCity() + " -> " + flight.getDestCity());
            });
        } else {
            System.out.println("No flight found from " + source + " to " + dest);
        }

    }
}
