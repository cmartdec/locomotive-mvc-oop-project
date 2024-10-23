package edu.uoc.locuocomotive.model.person;

public class PassengerException extends Exception {

    public static final String INVALID_PASSPORT = "Passport cannot be null or empty";
    public static final String INVALID_NAME = "Name cannot not be null or empty";
    public static final String INVALID_SURNAME = "Surname cannot not be null or empty";
    public static final String INVALID_BIRTHDATE = "Birthdate cannot not be null";
    public static final String INVALID_EMAIL = "Invalid email format";

    public PassengerException(String message) {
        super(message);
    }

}
