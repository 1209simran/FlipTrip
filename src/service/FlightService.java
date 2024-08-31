package service;

import dao.FlightDao;
import enums.filterType;
import model.Flight;
import service.filter.CheapestFlightFilter;
import service.filter.HopsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Flight flight = new Flight(airlineName, sourceCity, destCity, price, flightDuration, isMealAvailable, isExcessBaggageAvailable);
        flightDao.registerFlight(flight);
        System.out.println(airlineName + " " + sourceCity + " -> " + destCity + " flight registered.");
    }

    public void filter(String airlineName, Boolean isMealAvailable) {
        Map<String, List<Flight>> flightsByAirline = flightDao.getAllFlights();
        List<Flight> flightList = new ArrayList<>();
        if (airlineName != null) {
            if (!flightDao.isAirlineExist(airlineName)) {
                System.out.println(airlineName + " doesn't exist");
                return;
            }
            flightList.addAll(flightsByAirline.get(airlineName));
        }
        if (isMealAvailable != null) {
            if (flightList.isEmpty()) {
                for (Map.Entry<String, List<Flight>> entry : flightsByAirline.entrySet()) {
                    entry.getValue().forEach(flight -> {
                        if (flight.isMealAvailable() & isMealAvailable) {
                            System.out.println("Found flight " + entry.getKey() + " " +
                                    flight.getSourceCity() + " -> " + flight.getDestCity());
                        }
                    });
                }
                return;
            } else {
                flightList = flightList.stream().filter(flight -> flight.isMealAvailable() & isMealAvailable).toList();
            }
        }
        flightList.forEach(flight -> {
            System.out.println("Found flight " + airlineName + " " +
                    flight.getSourceCity() + " -> " + flight.getDestCity());
        });

    }

    public void filter(String filterName, String source, String dest) {
        List<Flight> flightList = new ArrayList<>();
        if (filterType.CHEAPEST.toString().equalsIgnoreCase(filterName)) {
            flightList = cheapestFlightFilter.filter(source, dest);
        } else if (filterType.MINIMUM_HOPS.toString().equalsIgnoreCase(filterName)) {
            flightList = hopsFilter.filter(source, dest);
        }
        if (flightList.isEmpty()) {
            System.out.println("No flights found from " + source + " -> " + dest);
            return;
        }
//        flightList.forEach(flight -> {
//            System.out.println("Found flight from " +
//                    source + " -> " + dest);
//        });
    }
}
