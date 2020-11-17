package Entities;

public class Hotel {
    private int id;
    private String name;
    private String location;

    public Hotel(int id, String name, String location) {
        setId(id);
        setName(name);
        setLocation(location);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "[ID: " + getId() + ", Name: " + getName() + ", Location: " + getLocation() + "]";
    }
}
