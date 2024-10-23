package edu.uoc.locuocomotive.model.train;

import java.util.ArrayList;
import java.util.List;

public class Train {

    private int id;
    private String model;

    private List<TrainCar> trainCars;

    public Train(int id, String model) {
        setId(id);
        setModel(model);

        trainCars = new ArrayList<>();
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void addTrainCars(TrainCar trainCar) {
        trainCars.add(trainCar);
    }

    public TrainSeat getNextAvailableSeat(TrainSeatType selectedSeatType) {
        for (TrainCar trainCar : trainCars) {
            if (trainCar instanceof PassengerCar passengerCar) {
                TrainSeat trainSeat = passengerCar.getNextAvailableSeat(selectedSeatType);
                if (trainSeat != null) {
                    return trainSeat;
                }
            }
        }
        return null;
    }

    public void clearAllSeats() {
        for (TrainCar trainCar : trainCars) {
            if (trainCar instanceof PassengerCar passengerCar) {
                passengerCar.clearAllSeats();
            }
        }
    }

    @Override
    public String toString() {
        return getId() + "|" + getModel() + "|" + trainCars.size();
    }
}
