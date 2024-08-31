import dao.FlightDao;
import service.FlightService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        FlightDao flightDao = FlightDao.getInstance();

        FlightService flightService = new FlightService(flightDao);

        flightService.registerFlight("JetAir", "DEL", "BLR", 1000, "02:50", true, true);
        flightService.registerFlight("Indigo", "DEL", "PNQ", 7000, "01:50", true, true);
        flightService.registerFlight("JetAir", "BLR", "PNQ", 5000, "00:50", true, true);
        flightService.registerFlight("AirIndia", "DEL", "PNQ", 10000, "01:50", true, true);


        flightService.filter("MINIMUM_HOPS", "DEL", "PNQ");
        flightService.filter("CHEAPEST", "DEL", "PNQ");

        flightService.filter("JetAir", true);

    }
}