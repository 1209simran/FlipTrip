package dao;

import model.Flight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightDao {

    private static FlightDao flightDao = null;
    private List<Flight> flights;
    private Map<String, Integer> pathWithHops;
    private Map<String, Integer> pathWithCost;
    private Map<String, List<Flight>> flightsByAirline;

    public FlightDao() {
        this.pathWithCost = new HashMap<>();
        this.pathWithHops = new HashMap<>();
        this.flights = new ArrayList<>();
        this.flightsByAirline = new HashMap<>();
    }

    public static FlightDao getInstance() {
        if (flightDao == null)
            flightDao = new FlightDao();
        return flightDao;
    }


    public void registerFlight(Flight flight) {
        String flightCode = flight.getAirlineName().toUpperCase() + flight.getSourceCity().toUpperCase()
                + flight.getDestCity().toUpperCase();
        if (pathWithHops.containsKey(flightCode) && pathWithHops.get(flightCode) == 1) {
            System.out.println("Error:- " + flight.getAirlineName() + " " + flight.getSourceCity()
                    + " -> " + flight.getDestCity() + " already registered.");
        }
        flights.add(flight);
        List<Flight> flightList = new ArrayList<>();
        if (flightsByAirline.containsKey(flight.getAirlineName()))
            flightList = flightsByAirline.get(flight.getAirlineName());
        flightList.add(flight);
        flightsByAirline.put(flight.getAirlineName(), flightList);
        pathWithHops.put(flightCode, 1);
        pathWithCost.put(flightCode, flight.getPrice());
    }

    public List<Flight> getFlightByAirline(String airlineName) {
        return flightsByAirline.get(airlineName).stream().toList();
    }

    public boolean isAirlineExist(String airlineName) {
        return flightsByAirline.containsKey(airlineName);
    }

    public Map<String, List<Flight>> getAllFlights() {
        return flightsByAirline;
    }
}
