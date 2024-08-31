package service.filter;

import dao.FlightDao;
import model.Flight;
import service.IFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HopsFilter implements IFilter {

    private final FlightDao flightDao;
    private Map<String, Integer> pathWithMinHops;
    private Map<String, Integer> pathWithCost;

    public HopsFilter() {
        this.flightDao = FlightDao.getInstance();
        this.pathWithMinHops = new HashMap<>();
        this.pathWithCost = new HashMap<>();
    }

    public void filter(String source, String dest) {

        List<List<Flight>> flights = flightDao.findFlightsBetweenToandFrom(source, dest);
        minHopFlight(flights, source, dest);
//        List<Flight> resFlights = new ArrayList<>();
//        List<Flight> flights = flightDao.getFlights();
//        Queue<Flight> flightQueue = new LinkedList<>();
//        Map<String, Integer> hopByFlight = new HashMap<>();
//        List<Flight> flight = getSourceFlight(flights, source);
//        flight.forEach(f -> {
//            String flightCode = f.getSourceCity().toUpperCase()
//                    + f.getDestCity().toUpperCase() + f.getAirlineName().toUpperCase();
//            hopByFlight.put(flightCode, 0);
//
//        });
//        flightQueue.addAll(flight);
//        Set<String> visited = new HashSet<>();
//        while (!flightQueue.isEmpty()) {
//            Flight fl = flightQueue.poll();
//            String flCode = fl.getAirlineName().toUpperCase() + fl.getSourceCity().toUpperCase()
//                    + fl.getDestCity().toUpperCase();
//            if (fl.getDestCity().equalsIgnoreCase(dest)) {
//                pathWithMinHops.put(flCode, pathWithMinHops.getOrDefault(flCode, 0));
//                pathWithCost.put(flCode, pathWithCost.getOrDefault(flCode, 0) + fl.getPrice());
//                resFlights.add(fl);
////                break;
//            }
//            List<Flight> srcFlights = getSourceFlight(flights, fl.getDestCity());
//
//            srcFlights.forEach(srcFlight -> {
//                String newFlightCode = srcFlight.getAirlineName().toUpperCase() + srcFlight.getSourceCity().toUpperCase()
//                        + srcFlight.getDestCity().toUpperCase();
//                if (!visited.contains(newFlightCode) && pathWithMinHops.getOrDefault(newFlightCode, Integer.MAX_VALUE) >
//                        hopByFlight.getOrDefault(newFlightCode, 0) + 1) {
//                    visited.add(newFlightCode);
//                    flightQueue.add(srcFlight);
//                    pathWithMinHops.put(newFlightCode, hopByFlight.getOrDefault(newFlightCode, 0) + 1);
//                    pathWithCost.put(newFlightCode, pathWithCost.getOrDefault(newFlightCode, 0) + srcFlight.getPrice());
//                }
//            });
//
//        }
//        Flight resFlight = minHopFlight(resFlights);
//        showFlights(resFlight, source, dest);
    }

    private void minHopFlight(List<List<Flight>> flights, String source, String dest) {
        List<Flight> resFlight = null;
        int minPrice = Integer.MAX_VALUE;
        int minHop = Integer.MAX_VALUE;
        for (List<Flight> flightList : flights) {
            if (minHop > flightList.size())
                minHop = flightList.size();
        }
        for (List<Flight> flightList : flights) {
            int totalPriceOfTrip = 0;
            for (Flight fl : flightList)
                totalPriceOfTrip += fl.getPrice();
            if (minPrice > totalPriceOfTrip && minHop == flightList.size()) {
                minPrice = totalPriceOfTrip;
                resFlight = flightList;
            }
        }
        showFlights(resFlight, source, dest, minPrice, minHop - 1);
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


//    private void showFlights(Flight resFlight, String source, String dest) {
//        System.out.println("Found flight from " +
//                source + " -> " + dest + " with " + pathWithCost.get(resFlight.getAirlineName().toUpperCase() +
//                resFlight.getSourceCity().toUpperCase()
//                + resFlight.getDestCity().toUpperCase()) + " price and " +
//                pathWithMinHops.get(resFlight.getAirlineName().toUpperCase() + resFlight.getSourceCity().toUpperCase()
//                        + resFlight.getDestCity().toUpperCase()) + " hops");
//    }
//
//
//    private Flight minHopFlight(List<Flight> resFlights) {
//        int minPrice = Integer.MAX_VALUE;
//        int minHop = Integer.MAX_VALUE;
//        Flight res = null;
//        for (Flight flight : resFlights) {
//            String flCode = flight.getAirlineName().toUpperCase() + flight.getSourceCity().toUpperCase() + flight.getDestCity().toUpperCase();
//            if (minHop > pathWithMinHops.get(flCode)) {
//                minHop = pathWithMinHops.get(flCode);
//            }
//        }
//        for (Flight flight : resFlights) {
//            String flCode = flight.getAirlineName().toUpperCase() + flight.getSourceCity().toUpperCase() + flight.getDestCity().toUpperCase();
//            if (pathWithMinHops.get(flCode) == minHop && minPrice > pathWithCost.get(flCode)) {
//                minPrice = pathWithCost.get(flCode);
//                res = flight;
//            }
//        }
//        return res;
//    }
//
//    private List<Flight> getSourceFlight(List<Flight> flights, String src) {
//        List<Flight> flightList = new ArrayList<>();
//        flights.forEach(flight -> {
//            if (flight.getSourceCity().equalsIgnoreCase(src))
//                flightList.add(flight);
//        });
//        return flightList;
//    }
}
