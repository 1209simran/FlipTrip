package service.filter;

import dao.FlightDao;
import model.Flight;
import service.IFilter;

import java.util.ArrayList;
import java.util.List;

public class CheapestFlightFilter implements IFilter {

    private final FlightDao flightDao;

    public CheapestFlightFilter() {
        this.flightDao = FlightDao.getInstance();
    }

    //dfs
    @Override
    public List<List<Flight>> filter(String source, String dest) {
        List<List<Flight>> flights = flightDao.findFlightsBetweenToandFrom(source, dest, flightDao.getFlights());
        return minPriceFlight(flights, source, dest);
    }

    private List<List<Flight>> minPriceFlight(List<List<Flight>> flights, String source, String dest) {
        List<Flight> resFlight = null;
        List<List<Flight>> finalFlightList = new ArrayList<>();
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
        if (resFlight != null)
            finalFlightList.add(resFlight);
        return finalFlightList;
    }

}
