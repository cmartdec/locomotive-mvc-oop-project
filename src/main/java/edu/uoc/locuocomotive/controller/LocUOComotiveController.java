package edu.uoc.locuocomotive.controller;

import edu.uoc.locuocomotive.model.Route;
import edu.uoc.locuocomotive.model.Ticket;
import edu.uoc.locuocomotive.model.station.*;
import edu.uoc.locuocomotive.model.train.*;
import edu.uoc.locuocomotive.model.person.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class LocUOComotiveController {

    List<Station> stations;
    List<Route> routes;
    List<Train> trains;
    private Map<String, Passenger> passengers;
    List<Ticket> tickets;

    Station currentStation;

    public LocUOComotiveController(String stationsFile, String routesFile, String trainsFile) {
        stations = new ArrayList<>();
        routes = new ArrayList<>();
        trains = new ArrayList<>();
        passengers = new HashMap<>();
        tickets = new ArrayList<>();

        loadTrains(trainsFile);
        loadStations(stationsFile);
        loadRoutes(routesFile);

        if (stations.isEmpty()) {
            throw new NullPointerException("No stations loaded");
        }

        setCurrentStation(stations.get(0));
    }

    private void loadStations(String stationsFile) {
        // Get the file from the resources folder
        InputStream is = getClass().getResourceAsStream("/data/" + stationsFile);

        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + stationsFile);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                // Create the station and add it to the list of stations
                addStation(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], parts[5], Integer.parseInt(parts[6]), Integer.parseInt(parts[7]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRoutes(String routesFile) {
        // Get the file from the resources folder
        InputStream is = getClass().getResourceAsStream("/data/" + routesFile);

        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + routesFile);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split by character :
                String[] parts = line.split("=");
                String[] parts2 = parts[0].split("\\|");

                // Create the route and add it to the list of routes
                addRoute(parts2[0], Integer.parseInt(parts2[1]), parts[1].split("\\|"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTrains(String trainsFile) {
        // Get the file from the resources folder
        InputStream is = getClass().getResourceAsStream("/data/" + trainsFile);

        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + trainsFile);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                int[] seatsPerCar = new int[parts.length - 2];

                for (int i = 2; i < parts.length; i++) {
                    seatsPerCar[i - 2] = Integer.parseInt(parts[i]);
                }

                addTrain(Integer.parseInt(parts[0]), parts[1], seatsPerCar);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStation(int id, String name, String city, int openingYear, String type, String image, int positionX, int positionY) {
        Station station = new Station(id, name, city, openingYear, StationType.valueOf(type), image, positionX, positionY);
        stations.add(station);
    }

    public void addRoute(String id, int trainId, String... stationsAndTimes) {
        List<StationTime> routeStationTimes = new ArrayList<>();

        for (String stationAndTimes : stationsAndTimes) {
            String[] parts = stationAndTimes.split("\\[");
            Station station = getStationById(Integer.parseInt(parts[0]));
            List<LocalTime> times = new ArrayList<>();

            if (parts.length > 1) {
                String[] timesStr = parts[1].replace("]", "").split(",");
                for (String time : timesStr) {
                    times.add(LocalTime.parse(time));
                }
            }

            StationTime stationTime = new StationTime(station, times);
            routeStationTimes.add(stationTime);
        }

        Train train = getTrainById(trainId);
        Route route = new Route(id, train, routeStationTimes);
        routes.add(route);
    }

    public void addTrain(int id, String model, int... cars) {
        Train train = new Train(id, model);

        for (int i = 0; i < cars.length; i++) {
            int seats = cars[i];
            TrainCar trainCar;

            if (seats == 0) {
                trainCar = new DiningCar(i+1);
            } else {
                trainCar = new PassengerCar(i+1, seats);
            }

            train.addTrainCars(trainCar);
        }

        trains.add(train);
    }

    public List<String> getStationsInfo() {
        List<String> stationsInfo = new ArrayList<>();

        for (Station station : stations) {
            stationsInfo.add(station.toString());
        }

        return stationsInfo;
    }

    public String[] getSeatTypes() {
        String[] seatTypes = new String[TrainSeatType.values().length];

        for (int i = 0; i < TrainSeatType.values().length; i++) {
            seatTypes[i] = TrainSeatType.values()[i].toString();
        }

        return seatTypes;
    }

    public List<String> getRoutesByStation(int stationId) {
        List<String> departureArrivalTimes = new ArrayList<>();
        int costPerHour = 30; // Cost per hour of travel

        for (Route route : routes) {
            List<StationTime> routeStationsTimes = route.getStationTimes();
            boolean foundCurrentStation = false;
            LocalTime departureTime = null, arrivalTime = null;
            LocalDateTime departureDate, arrivalDate = null;
            String originStation = null;
            String destinationStation = null;
            int originStationId = 0;
            int destinationStationId = 0;
            int originStationPos = 0;

            for (int i = 0; i < routeStationsTimes.size(); i++) {
                StationTime routeStationTime = routeStationsTimes.get(i);
                if (routeStationTime.getStation().getId() == currentStation.getId()) {
                    foundCurrentStation = true;
                    originStation = routeStationTime.getStation().getName();
                    originStationId = routeStationTime.getStation().getId();
                    originStationPos = i;
                }

                if (foundCurrentStation && routeStationTime.getStation().getId() == stationId) {
                    destinationStation = routeStationTime.getStation().getName();
                    destinationStationId = routeStationTime.getStation().getId();

                    for (int j = 0; j < routeStationsTimes.get(originStationPos).getTimes().size(); j++) {
                        // Get the departure time from the origin station
                        departureTime = routeStationsTimes.get(originStationPos).getTimes().get(j);
                        departureDate = LocalDate.now().atTime(departureTime);

                        // Get the arrival time from the destination station
                        arrivalTime = routeStationTime.getTimes().get(j);
                        arrivalDate = LocalDate.now().atTime(arrivalTime);

                        // Calculate the duration of the trip in hours
                        if (departureTime.isAfter(arrivalTime) || departureTime.equals(arrivalTime)) {
                            arrivalDate = arrivalDate.plusDays(1);
                        }

                        long duration = java.time.Duration.between(departureDate, arrivalDate).toHours();

                        // Calculate the cost of the ticket
                        long ticketCost = duration * costPerHour;

                        departureArrivalTimes.add(departureTime + "-" + arrivalTime.toString() + "|" + route.getId() + "|" + ticketCost + "|" + originStationId + "|" + destinationStationId + "|" + originStation + "|" + destinationStation);
                    }
                }
            }
        }

        // Sort the departure times
        departureArrivalTimes.sort(Comparator.comparing(s -> LocalTime.parse(s.split("\\|")[0].split("-")[0])));

        return departureArrivalTimes;
    }

    public void addPassenger(String passport, String name, String surname, LocalDate birthdate, String email) throws Exception {
        Passenger passenger;

        // Find the passenger by passport
        passenger = passengers.get(passport);

        // If the passenger does not exist, create a new one
        if (passenger == null) {
            if (email == null) {
                passenger = new Passenger(passport, name, surname, birthdate);
                passengers.put(passport, passenger);
            } else {
                passenger = new Passenger(passport, name, surname, birthdate, email);
                passengers.put(passport, passenger);

            }

            passengers.put(passport, passenger);
        } else {
            // If the passenger exists, update the information
            passenger.setName(name);
            passenger.setSurname(surname);
            passenger.setBirthdate(birthdate);
            passenger.setEmail(email);
        }
    }

    public void createTicket(String passport, String routeId, LocalTime departureTime, LocalTime arrivalTime, double cost, int originStationId, int destinationStationId, String selectedSeatType) throws Exception {
        Route route = getRouteById(routeId);
        Station originStation = getStationById(originStationId);
        Station destinationStation = getStationById(destinationStationId);

        if (route == null) {
            throw new Exception("Route not found");
        }

        Train train = route.getTrain();

        if (train == null) {
            throw new Exception("Train not found");
        }

        TrainSeat trainSeat = train.getNextAvailableSeat(TrainSeatType.valueOf(selectedSeatType));

        if (trainSeat == null) {
            throw new Exception("No available seats");
        }

        Passenger passenger = passengers.get(passport);

        if (passenger == null) {
            throw new Exception("Passenger not found");
        }

        Ticket ticket = new Ticket(passengers.get(passport), trainSeat, cost, route, originStation, destinationStation, departureTime, arrivalTime);

        passenger.addTicket(ticket);
        trainSeat.setPassenger(passenger);

        tickets.add(ticket);

        // Update the current station
        setCurrentStation(ticket.getTravel().getArrivalStation());

        // Clear all the passengers
        train.clearAllSeats();
    }

    public void buyTicket(String passport, String name, String surname, LocalDate birthdate, String email,
                            String routeId, LocalTime departureTime, LocalTime arrivalTime, double cost, int originStationId, int destinationStationId, String selectedSeatType) throws Exception {
        addPassenger(passport, name, surname, birthdate, email);
        createTicket(passport, routeId, departureTime, arrivalTime, cost, originStationId, destinationStationId, selectedSeatType);
    }

    public List<String> getAllTickets() {
        List<String> ticketsInfo = new ArrayList<>();

        for (Ticket ticket : tickets) {
            ticketsInfo.add(ticket.toString());
        }

        return ticketsInfo;
    }

    public String getPassengerInfo(String passport) {
        Passenger passenger = passengers.get(passport);

        if (passenger == null) {
            return "";
        }

        return passenger.toString();
    }

    public String getTrainInfo(int trainId) {
        Train train = getTrainById(trainId);

        if (train == null) {
            return "";
        }

        return train.toString();
    }

    public List<String> getPassengerTickets(String passport) {
        List<String> passengerTickets = new ArrayList<>();
        Passenger passenger = passengers.get(passport);

        if (passenger == null) {
            return passengerTickets;
        }

        for (Ticket ticket : passenger.getTickets()) {
            passengerTickets.add(ticket.toString());
        }

        return passengerTickets;
    }

    public List<String> getRouteDeparturesInfo(String routeId) {
        List<String> routeDeparturesInfo = new ArrayList<>();
        Route route = getRouteById(routeId);

        if (route == null) {
            return routeDeparturesInfo;
        }

        for (StationTime stationTime : route.getStationTimes()) {
            routeDeparturesInfo.add(stationTime.toString());
        }

        return routeDeparturesInfo;
    }

    public int getCurrentStationId() {
        return currentStation.getId();
    }

    // AUXILIARY METHODS
    public void setCurrentStation(Station station) {
        this.currentStation = station;
    }

    private Station getStationById(int id) {
        for (Station station : stations) {
            if (station.getId() == id) {
                return station;
            }
        }

        return null;
    }

    private Route getRouteById(String routeId) {
        for (Route route : routes) {
            if (route.getId().equals(routeId)) {
                return route;
            }
        }

        return null;
    }

    private Train getTrainById(int trainId) {
        for (Train train : trains) {
            if (train.getId() == trainId) {
                return train;
            }
        }

        return null;
    }
}
