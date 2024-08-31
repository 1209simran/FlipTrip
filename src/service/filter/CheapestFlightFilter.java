package service.filter;

import dao.FlightDao;
import model.Flight;
import service.IFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheapestFlightFilter implements IFilter {

    private final FlightDao flightDao;
    private Map<String, Integer> pathWithCost;
    private Map<String, Integer> pathWithMinHops;


    public CheapestFlightFilter() {
        this.flightDao = FlightDao.getInstance();
        this.pathWithCost = new HashMap<>();
        this.pathWithMinHops = new HashMap<>();
    }

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

//    @Override
//    public void filter(String source, String dest) {
//        List<Flight> resFlights = new ArrayList<>();
//        List<Flight> flights = flightDao.getFlights();
//        Queue<Flight> flightQueue = new LinkedList<>();
//        List<Flight> flight = getSourceFlight(flights, source);
//        flightQueue.addAll(flight);
//        Set<String> visited = new HashSet<>();
//        while (!flightQueue.isEmpty()) {
//            Flight fl = flightQueue.poll();
//            String flCode = fl.getAirlineName().toUpperCase() + fl.getSourceCity().toUpperCase()
//                    + fl.getDestCity().toUpperCase();
//            if (fl.getDestCity().equalsIgnoreCase(dest)) {
//                pathWithCost.put(flCode, pathWithCost.getOrDefault(flCode, fl.getPrice()));
//                pathWithMinHops.put(flCode, pathWithMinHops.getOrDefault(flCode, 0));
//                resFlights.add(fl);
//            }
//            int srcPrice = fl.getPrice();
//            List<Flight> srcFlights = getSourceFlight(flights, fl.getDestCity());
//
//            srcFlights.forEach(srcFlight -> {
//                int price = srcPrice + srcFlight.getPrice();
//                String newFlightCode = srcFlight.getAirlineName().toUpperCase() + srcFlight.getSourceCity().toUpperCase()
//                        + srcFlight.getDestCity().toUpperCase();
//                if (!visited.contains(newFlightCode)) {
//                    if (pathWithCost.getOrDefault(newFlightCode, Integer.MAX_VALUE) >
//                            pathWithCost.getOrDefault(newFlightCode, 0) + price) {
//                        visited.add(newFlightCode);
//                        flightQueue.add(srcFlight);
//                        pathWithCost.put(newFlightCode, pathWithCost.getOrDefault(newFlightCode, 0) + price);
//                        pathWithMinHops.put(newFlightCode, pathWithMinHops.getOrDefault(newFlightCode, 0) + 1);
//                    }
//                }
//            });
//
//        }
//        Flight resFlight = minPriceFlight(resFlights);
//        showFlights(resFlight, source, dest);
//    }

//    private void showFlights(Flight resFlight, String source, String dest) {
//        System.out.println("Found flight from " +
//                source + " -> " + dest + " with " + pathWithCost.get(resFlight.getAirlineName().toUpperCase() +
//                resFlight.getSourceCity().toUpperCase()
//                + resFlight.getDestCity().toUpperCase()) + " price and " +
//                pathWithMinHops.get(resFlight.getAirlineName().toUpperCase() + resFlight.getSourceCity().toUpperCase()
//                        + resFlight.getDestCity().toUpperCase()) + " hops");
//    }

//    private Flight minPriceFlight(List<Flight> resFlights) {
//        int minPrice = Integer.MAX_VALUE;
//        int minHop = Integer.MAX_VALUE;
//        Flight res = null;
//        for (Flight flight : resFlights) {
//            String flCode = flight.getAirlineName().toUpperCase() + flight.getSourceCity().toUpperCase() + flight.getDestCity().toUpperCase();
//            if (minPrice > pathWithCost.get(flCode)) {
//                minPrice = pathWithCost.get(flCode);
//            }
//        }
//        for (Flight flight : resFlights) {
//            String flCode = flight.getAirlineName().toUpperCase() + flight.getSourceCity().toUpperCase() + flight.getDestCity().toUpperCase();
//            if (pathWithCost.get(flCode) == minPrice && minHop > pathWithMinHops.get(flCode)) {
//                minHop = pathWithMinHops.get(flCode);
//                res = flight;
//            }
//        }
//        return res;
//    }

//    private List<Flight> getSourceFlight(List<Flight> flights, String src) {
//        List<Flight> flightList = new ArrayList<>();
//        flights.forEach(flight -> {
//            if (flight.getSourceCity().equalsIgnoreCase(src))
//                flightList.add(flight);
//        });
//        return flightList;
//    }
}
