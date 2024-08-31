package service;

import model.Flight;

import java.util.List;

public interface IFilter {

    List<List<Flight>> filter(String source, String dest);
}
