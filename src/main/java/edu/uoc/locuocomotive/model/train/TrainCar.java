package edu.uoc.locuocomotive.model.train;

public abstract class TrainCar {

    private String carNumber;

    public TrainCar(int carNumber) {
        setCarNumber(carNumber);
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = "C" + carNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }
}
