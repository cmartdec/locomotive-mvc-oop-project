package edu.uoc.locuocomotive.model.train;

import java.util.ArrayList;
import java.util.List;

public class PassengerCar extends TrainCar implements SeatingCar {

    private List<TrainSeat> seats;

    public PassengerCar(int carNumber, int maxSeats) {
        super(carNumber);

        seats = new ArrayList<>(maxSeats);

        for (int i = 0; i < maxSeats; i++) {
            TrainSeatType seatType = TrainSeatType.THIRD_CLASS;

            if (maxSeats < 20) {
                seatType = TrainSeatType.FIRST_CLASS;
            } else if (maxSeats < 50) {
                seatType = TrainSeatType.SECOND_CLASS;
            }

            seats.add(new TrainSeat(i + 1, seatType, this));
        }
    }

    @Override
    public TrainSeat getNextAvailableSeat(TrainSeatType selectedSeatType) {
        for (TrainSeat seat : seats) {
            if (seat.getType() == selectedSeatType && seat.getPassenger() == null) {
                return seat;
            }
        }
        return null;
    }

    public void clearAllSeats() {
        for (TrainSeat seat : seats) {
            seat.setPassenger(null);
        }
    }
}
