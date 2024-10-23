package edu.uoc.locuocomotive.model.train;

import edu.uoc.locuocomotive.model.person.Passenger;

public class TrainSeat {

    private int number;
    private TrainSeatType type;
    private Passenger passenger;
    private TrainCar car;

    public TrainSeat(int number, TrainSeatType type, TrainCar trainCar) {
        setNumber(number);
        setType(type);
        setTrainCar(trainCar);

        this.passenger = null;
    }

    private void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setType(TrainSeatType type) {
        this.type = type;
    }

    public TrainSeatType getType() {
        return type;
    }

    public void setTrainCar(TrainCar car) {
        this.car = car;
    }

    public TrainCar getTrainCar() {
        return car;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Passenger getPassenger() {
        return passenger;
    }

}
