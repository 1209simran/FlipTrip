package service.filter;

import dao.FlightDao;
import model.Flight;
import service.IFilter;

import java.util.ArrayList;
import java.util.List;

public class HopsFilter implements IFilter {

    private final FlightDao flightDao;

    public HopsFilter() {
        this.flightDao = FlightDao.getInstance();
    }


    //dfs
    @Override
    public List<List<Flight>> filter(String source, String dest) {
        List<List<Flight>> flights = flightDao.findFlightsBetweenToandFrom(source, dest, flightDao.getFlights());
        return minHopFlight(flights, source, dest);
    }

    private List<List<Flight>> minHopFlight(List<List<Flight>> flights, String source, String dest) {
        List<Flight> resFlight = null;
        List<List<Flight>> finalFlightList = new ArrayList<>();
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
        if (resFlight != null)
            finalFlightList.add(resFlight);
        return finalFlightList;
    }

}
