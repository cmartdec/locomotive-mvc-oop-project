package edu.uoc.locuocomotive.model;

import edu.uoc.locuocomotive.model.person.Passenger;
import edu.uoc.locuocomotive.model.station.Station;
import edu.uoc.locuocomotive.model.train.TrainSeat;

import java.time.LocalTime;

public class Ticket {

    private Passenger passenger;
    private Travel travel;
    private TrainSeat seat;
    private double price;

    public Ticket(Passenger passenger, TrainSeat seat, double price, Route route, Station departureStation, Station arrivalStation, LocalTime departureTime, LocalTime arrivalTime) {
        setPassenger(passenger);
        setSeat(seat);
        setPrice(price);
        setTravel(new Travel(route, departureStation, arrivalStation, departureTime, arrivalTime));
    }

    private void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    private void setTravel(Travel travel) {
        this.travel = travel;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setSeat(TrainSeat seat) {
        this.seat = seat;
    }

    public TrainSeat getSeat() {
        return seat;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return getTravel().getRoute().getId() + "|" + getTravel().getDepartureTime() + "|" + getTravel().getDepartureStation().getName() + "|" + getTravel().getArrivalTime() + "|" + getTravel().getArrivalStation().getName() + "|" + getSeat().getTrainCar().getCarNumber() + "-" + getSeat().getNumber() + "|" + getPrice();
    }

}
