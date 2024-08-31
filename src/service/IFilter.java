package service;

import model.Flight;

import java.util.List;

public interface IFilter {

    List<Flight> filter(String source, String dest);
}
