package service.filter;

import dao.FlightDao;
import model.Flight;
import service.IFilter;

import java.util.List;

public class HopsFilter implements IFilter {

    private final FlightDao flightDao;

    public HopsFilter() {
        this.flightDao = FlightDao.getInstance();
    }

    @Override
    public List<Flight> filter(String source, String dest) {
        return List.of();
    }
}
