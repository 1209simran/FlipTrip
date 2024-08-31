package service.filter;

import dao.FlightDao;
import model.Flight;
import service.IFilter;

import java.util.*;

public class CheapestFlightFilter implements IFilter {

    private final FlightDao flightDao;
    private Map<String, Integer> pathWithCost;

    public CheapestFlightFilter() {
        this.flightDao = FlightDao.getInstance();
        this.pathWithCost = new HashMap<>();
    }

    @Override
    public List<Flight> filter(String source, String dest) {

        List<Flight> resFlights = new ArrayList<>();
        List<Flight> flights = flightDao.getFlights();
        Queue<Flight> flightQueue = new LinkedList<>();
        List<Flight> flight = getSourceFlight(flights, source);
        flightQueue.addAll(flight);
        Set<String> visited = new HashSet<>();
        while (!flightQueue.isEmpty()) {
            Flight fl = flightQueue.poll();
            String flCode = fl.getSourceCity().toUpperCase()
                    + fl.getDestCity().toUpperCase();
            if (fl.getDestCity().equalsIgnoreCase(dest)) {
                pathWithCost.put(flCode, pathWithCost.getOrDefault(flCode, fl.getPrice()));
                resFlights.add(fl);
//                break;
            }
            int srcPrice = fl.getPrice();
            List<Flight> srcFlights = getSourceFlight(flights, fl.getDestCity());

            srcFlights.forEach(srcFlight -> {
                int price = srcPrice + srcFlight.getPrice();
                String newFlightCode = srcFlight.getSourceCity().toUpperCase()
                        + srcFlight.getDestCity().toUpperCase();
                if (!visited.contains(newFlightCode)) {
                    if (pathWithCost.getOrDefault(newFlightCode, Integer.MAX_VALUE) >
                            pathWithCost.getOrDefault(newFlightCode, 0) + price) {
                        visited.add(newFlightCode);
                        flightQueue.add(srcFlight);
                        pathWithCost.put(newFlightCode, pathWithCost.getOrDefault(newFlightCode, 0) + price);
                    }
                }
            });

        }
        Flight resFlight = minPriceFlight(resFlights);
        System.out.println("Found flight from " +
                source + " -> " + dest + " with " + pathWithCost.get(resFlight.getSourceCity().toUpperCase()
                + resFlight.getDestCity().toUpperCase()) + " price");
        return resFlights;

    }

    private Flight minPriceFlight(List<Flight> resFlights) {
        int minPrice = Integer.MAX_VALUE;
        Flight res = null;
        for (Flight flight : resFlights) {
            if (minPrice > pathWithCost.get(flight.getSourceCity().toUpperCase() + flight.getDestCity().toUpperCase())) {
                minPrice = pathWithCost.get(flight.getSourceCity().toUpperCase()
                        + flight.getDestCity().toUpperCase());
                res = flight;
            }
        }
        return res;
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
