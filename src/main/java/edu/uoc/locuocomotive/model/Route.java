package edu.uoc.locuocomotive.model;

import edu.uoc.locuocomotive.model.station.StationTime;
import edu.uoc.locuocomotive.model.train.Train;

import java.util.List;

public class Route {
    private String id;
    private Train train;
    private List<StationTime> stationTimes;

    public Route(String id, Train train, List<StationTime> stationTimes) {
        setId(id);
        setTrain(train);
        setStationTimes(stationTimes);
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Train getTrain() {
        return train;
    }

    private void setStationTimes(List<StationTime> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public List<StationTime> getStationTimes() {
        return stationTimes;
    }

    @Override
    public String toString() {
        return getId();
    }

}
