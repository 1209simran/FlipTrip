package service;

import dao.FlightDao;
import enums.filterType;
import model.Flight;
import service.filter.CheapestFlightFilter;
import service.filter.HopsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FlightService {

    private final FlightDao flightDao;
    private final CheapestFlightFilter cheapestFlightFilter;
    private final HopsFilter hopsFilter;

    public FlightService(FlightDao flightDao) {
        this.flightDao = flightDao;
        this.cheapestFlightFilter = new CheapestFlightFilter();
        this.hopsFilter = new HopsFilter();
    }

    public void registerFlight(String airlineName, String sourceCity, String destCity, int price, String flightDuration, boolean isMealAvailable, boolean isExcessBaggageAvailable) {
        String id = UUID.randomUUID().toString();
        Flight flight = new Flight(id, airlineName, sourceCity, destCity, price, flightDuration, isMealAvailable, isExcessBaggageAvailable);
        flightDao.registerFlight(flight);
        System.out.println(airlineName + " " + sourceCity + " -> " + destCity + " flight registered.");
    }

    public void filterWithAirlineName(String airlineName, String src, String dest) {
        Map<String, List<Flight>> flightsByAirline = flightDao.getAllFlights();
        List<Flight> flightList = new ArrayList<>();
        if (airlineName != null) {
            if (!flightDao.isAirlineExist(airlineName)) {
                System.out.println(airlineName + " doesn't exist");
                return;
            }
            flightList.addAll(flightsByAirline.get(airlineName));
        }

        List<List<Flight>> flights = flightDao.findFlightsBetweenToandFrom(src, dest, flightList);
        flights.forEach(fl -> {
            showFlights(fl, src, dest);
        });
    }

    private void showFlights(List<Flight> resFlight, String source, String dest) {
        if (resFlight != null && !resFlight.isEmpty()) {
            int totalPriceOfTrip = 0;
            for (Flight fl : resFlight)
                totalPriceOfTrip += fl.getPrice();
            System.out.println("Found flight from " + source + " -> " + dest + " with price " + totalPriceOfTrip + " and " + (resFlight.size() - 1) + " hops");
            resFlight.forEach(flight -> {
                System.out.println(flight.getSourceCity() + " -> " + flight.getDestCity() + ", price: " + flight.getPrice());
            });
        } else {
            System.out.println("No flight found from " + source + " to " + dest);
        }

    }

    public void filter(String filterName, String source, String dest) {
        if (source.equalsIgnoreCase(dest)) {
            System.out.println("Error:- Source and Destination city cannot be same.");
            return;
        }
        List<List<Flight>> flights = new ArrayList<>();
        if (filterType.CHEAPEST.toString().equalsIgnoreCase(filterName)) {
            flights = cheapestFlightFilter.filter(source, dest);
        } else if (filterType.MINIMUM_HOPS.toString().equalsIgnoreCase(filterName)) {
            flights = hopsFilter.filter(source, dest);
        }
        flights.forEach(fl -> {
            showFlights(fl, source, dest);
        });
    }


}
