package edu.uoc.locuocomotive.model;

import edu.uoc.locuocomotive.model.station.Station;

import java.time.LocalTime;

public class Travel {

    private Route route;
    private Station departureStation;
    private Station arrivalStation;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public Travel(Route route, Station departureStation, Station arrivalStation, LocalTime departureTime, LocalTime arrivalTime) {
        setRoute(route);
        setDepartureStation(departureStation);
        setArrivalStation(arrivalStation);
        setDepartureTime(departureTime);
        setArrivalTime(arrivalTime);
    }

    private void setRoute(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    private void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    private void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

}
