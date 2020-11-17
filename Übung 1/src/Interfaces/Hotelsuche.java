package Interfaces;

import Entities.Hotel;

import java.util.List;

public interface Hotelsuche {
    public List<Hotel> getHotelByName(String name);
    public void openSession();
    public void closeSession();
}
