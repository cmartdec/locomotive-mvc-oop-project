package edu.uoc.locuocomotive.model.train;

public interface SeatingCar {

    TrainSeat getNextAvailableSeat(TrainSeatType selectedSeatType);

}
