package edu.uoc.locuocomotive.model.station;

import java.time.LocalTime;
import java.util.List;

public class StationTime {
    private Station station;
    private List<LocalTime> times;

    public StationTime(Station station, List<LocalTime> times) {
        setStation(station);
        setTimes(times);
    }

    private void setStation(Station station) {
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    public void setTimes(List<LocalTime> times) {
        this.times = times;
    }

    public List<LocalTime> getTimes() {
        return times;
    }

    @Override
    public String toString() {
        return station.getId() + "|" + getTimes().toString();
    }
}
