package dao;

import model.Flight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightDao {

    private static FlightDao flightDao = null;
    private List<Flight> flights;
    private Map<String, List<Flight>> flightsByAirline;

    public FlightDao() {

        this.flights = new ArrayList<>();
        this.flightsByAirline = new HashMap<>();
    }

    public static FlightDao getInstance() {
        if (flightDao == null)
            flightDao = new FlightDao();
        return flightDao;
    }


    public void registerFlight(Flight flight) {

        flights.add(flight);
        List<Flight> flightList = new ArrayList<>();
        if (flightsByAirline.containsKey(flight.getAirlineName()))
            flightList = flightsByAirline.get(flight.getAirlineName());
        flightList.add(flight);
        flightsByAirline.put(flight.getAirlineName(), flightList);
        
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

    public List<Flight> getFlights() {
        return flights;
    }
}
