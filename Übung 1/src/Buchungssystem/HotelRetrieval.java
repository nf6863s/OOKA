package Buchungssystem;

import Entities.Hotel;
import Interfaces.Caching;
import Interfaces.Hotelsuche;

import java.util.List;

public class HotelRetrieval implements Hotelsuche {
    private DBAccess dbAccess = new DBAccess();
    private boolean sessionOpened = false;
    private Caching cache;

    public HotelRetrieval(Caching cacheMethod) {
        this.cache = cacheMethod;
    }

    @Override
    public List<Hotel> getHotelByName(String name) {
        if(cache != null && cache.isCached(name)) {
            return cache.readCache(name);
        } else {
            if (!sessionOpened) {
                throw new IllegalArgumentException("The Session for the DB has not been opened yet or has been closed already. Open a Session before continuing!");
            }

            List<Hotel> result = dbAccess.getObjects(0, name);
            if (cache != null) {
                cache.cacheResult(name, result);
            }

            return result;
        }
    }

    @Override
    public void openSession() {
        dbAccess.openConnection();
        sessionOpened = true;
    }

    @Override
    public void closeSession() {
        dbAccess.closeConnection();
        sessionOpened = false;
    }
}
