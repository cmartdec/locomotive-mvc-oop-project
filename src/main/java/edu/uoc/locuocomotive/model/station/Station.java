package edu.uoc.locuocomotive.model.station;

import edu.uoc.locuocomotive.model.Position;

public class Station {

    private int id;
    private String name;
    private String city;
    private int openingYear;
    private StationType type;
    private String image;
    private Position position;

    public Station(int id, String name, String city, int openingYear, StationType type, String image, int x, int y) {
        setId(id);
        setName(name);
        setCity(city);
        setOpeningYear(openingYear);
        setType(type);
        setImage(image);
        setPosition(x, y);
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setOpeningYear(int openingYear) {
        this.openingYear = openingYear;
    }

    public int getOpeningYear() {
        return openingYear;
    }

    public void setType(StationType type) {
        this.type = type;
    }

    public StationType getType() {
        return type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return getId() + "|" + getName() + "|" + getCity() + "|" + getImage() + "|" + getPosition().toString();
    }

}
