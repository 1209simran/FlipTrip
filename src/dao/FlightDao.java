package dao;

import model.Flight;

import java.util.*;

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

    public List<List<Flight>> findFlightsBetweenToandFrom(String src, String dest, List<Flight> flightList) {
        List<List<Flight>> flightsBetween = new ArrayList<>();
        List<Flight> srcFlights = getSourceFlight(flightList, src);
        Set<String> visited = new HashSet<>();
        srcFlights.forEach(flight -> {
            List<Flight> srcFls = new ArrayList<>();
            srcFls.add(flight);
            findFlightsBetweenToandFrom(flight.getFlightId(), flight.getSourceCity(), dest, visited, flight, flightsBetween, srcFls);
        });
        return flightsBetween;
    }

    private void findFlightsBetweenToandFrom(String flightId, String src, String dest, Set<String> visited, Flight flight, List<List<Flight>> flightsBetweenList, List<Flight> flightsBetween) {
        if (flight.getDestCity().equalsIgnoreCase(dest)) {
            flightsBetweenList.add(flightsBetween);
            return;
        }
        visited.add(flightId);
        List<Flight> destFlights = getSourceFlight(flights, flight.getDestCity());
        destFlights.forEach(destFlight -> {
            if (!visited.contains(destFlight.getFlightId()) && !destFlight.getDestCity().equalsIgnoreCase(src)) {
                flightsBetween.add(destFlight);
                findFlightsBetweenToandFrom(flightId, src, dest, visited, destFlight, flightsBetweenList, flightsBetween);
            }

        });

    }

    private List<Flight> getSourceFlight(List<Flight> flights, String src) {
        List<Flight> flightList = new ArrayList<>();
        flights.forEach(flight -> {
            if (flight.getSourceCity().equalsIgnoreCase(src))
                flightList.add(flight);
        });
        return flightList;
    }
}
