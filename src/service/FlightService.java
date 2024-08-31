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
                            System.out.println("Found flight for airline " + entry.getKey() + " " +
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
            System.out.println("Found flight for airline " + airlineName + " " +
                    flight.getSourceCity() + " -> " + flight.getDestCity());
        });

    }

    public void filter(String filterName, String source, String dest) {
        if (source.equalsIgnoreCase(dest)) {
            System.out.println("Error:- Source and Destination city cannot be same.");
            return;
        }
        if (filterType.CHEAPEST.toString().equalsIgnoreCase(filterName)) {
            cheapestFlightFilter.filter(source, dest);
        } else if (filterType.MINIMUM_HOPS.toString().equalsIgnoreCase(filterName)) {
            hopsFilter.filter(source, dest);
        }

    }


}
