package service.filter;

import dao.FlightDao;
import model.Flight;
import service.IFilter;

import java.util.*;

public class HopsFilter implements IFilter {

    private final FlightDao flightDao;
    private Map<String, Integer> pathWithMinHops;

    public HopsFilter() {
        this.flightDao = FlightDao.getInstance();
        this.pathWithMinHops = new HashMap<>();
    }

    public List<Flight> filter(String source, String dest) {

        List<Flight> resFlights = new ArrayList<>();
        List<Flight> flights = flightDao.getFlights();
        Queue<Flight> flightQueue = new LinkedList<>();
        Map<String, Integer> hopByFlight = new HashMap<>();
        List<Flight> flight = getSourceFlight(flights, source);
        flight.forEach(f -> {
            String flightCode = f.getSourceCity().toUpperCase()
                    + f.getDestCity().toUpperCase() + f.getAirlineName().toUpperCase();
            hopByFlight.put(flightCode, 0);

        });
        flightQueue.addAll(flight);
        Set<String> visited = new HashSet<>();
        while (!flightQueue.isEmpty()) {
            Flight fl = flightQueue.poll();
            if (fl.getDestCity().equalsIgnoreCase(dest)) {
                pathWithMinHops.put(fl.getSourceCity().toUpperCase()
                                + fl.getDestCity().toUpperCase(),
                        pathWithMinHops.getOrDefault(fl.getSourceCity().toUpperCase()
                                + fl.getDestCity().toUpperCase(), 0));
                resFlights.add(fl);
                break;
            }
            List<Flight> srcFlights = getSourceFlight(flights, fl.getDestCity());

            srcFlights.forEach(srcFlight -> {
                String newFlightCode = srcFlight.getSourceCity().toUpperCase()
                        + srcFlight.getDestCity().toUpperCase();
                if (!visited.contains(newFlightCode) && pathWithMinHops.getOrDefault(newFlightCode, Integer.MAX_VALUE) >
                        hopByFlight.getOrDefault(newFlightCode + srcFlight.getAirlineName().toUpperCase(), 0) + 1) {
                    visited.add(newFlightCode);
                    flightQueue.add(srcFlight);
                    pathWithMinHops.put(newFlightCode, hopByFlight.getOrDefault(newFlightCode + srcFlight.getAirlineName().toUpperCase(), 0) + 1);
                }
            });

        }
        resFlights.forEach(res -> {
            System.out.println("Found flight from " +
                    source + " -> " + dest + " with " + pathWithMinHops.get(res.getSourceCity().toUpperCase()
                    + res.getDestCity().toUpperCase()) + " hops");
        });

        return resFlights;
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
