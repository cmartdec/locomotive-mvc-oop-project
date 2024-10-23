package edu.uoc.locuocomotive.model.person;

import edu.uoc.locuocomotive.model.Ticket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Passenger {

    private String passport;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String email = "";

    private List<Ticket> tickets;

    public Passenger(String passport, String name, String surname, LocalDate birthdate) throws PassengerException {
        setPassport(passport);
        setName(name);
        setSurname(surname);
        setBirthdate(birthdate);

        tickets = new ArrayList<>();
    }

    public Passenger(String passport, String name, String surname, LocalDate birthdate, String email) throws PassengerException {
        this(passport, name, surname, birthdate);
        setEmail(email);
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) throws PassengerException {
        if (passport == null || passport.isEmpty()) {
            throw new PassengerException(PassengerException.INVALID_PASSPORT);
        }

        this.passport = passport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws PassengerException {
        if (name == null || name.isEmpty()) {
            throw new PassengerException(PassengerException.INVALID_NAME);
        }

        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws PassengerException {
        if (surname == null || surname.isEmpty()) {
            throw new PassengerException(PassengerException.INVALID_SURNAME);
        }

        this.surname = surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) throws PassengerException {
        if (birthdate == null) {
            throw new PassengerException(PassengerException.INVALID_BIRTHDATE);
        }

        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws PassengerException {
        if (email != null && !email.isEmpty() && !email.matches("^[a-zA-Z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,3}$")) {
            throw new PassengerException(PassengerException.INVALID_EMAIL);
        }

        this.email = email;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return getPassport() + "|" + getName() + "|" + getSurname() + "|" + getBirthdate() + "|" + getEmail();
    }

}
