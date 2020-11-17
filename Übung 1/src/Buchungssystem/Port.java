package Buchungssystem;

import Entities.Hotel;
import Interfaces.Caching;
import Interfaces.Hotelsuche;
import sun.rmi.runtime.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * Proxy-Pattern
 */

public class Port implements Hotelsuche {
    private HotelRetrieval hotelRetrieval;
    private BufferedWriter bw;

    public Port() {
        hotelRetrieval = new HotelRetrieval(new Caching<Hotel>() {
            private HashMap<String, List<Hotel>> cache = new HashMap<>();

            @Override
            public void cacheResult(String key, List<Hotel> value) {
                cache.put(key, value);
            }

            @Override
            public List<Hotel> readCache(String key) {
                return cache.get(key);
            }

            @Override
            public boolean isCached(String key) {
                return cache.containsKey(key);
            }
        });
    }

    public Port(Caching<Hotel> caching) {
        hotelRetrieval = new HotelRetrieval(caching);
    }

    @Override
    public List<Hotel> getHotelByName(String name) {
        log("Zugriff auf Buchungssystem über Methode getHotelByName. Suchwort: " + name, LogType.INFO);
        return hotelRetrieval.getHotelByName(name);
    }

    @Override
    public void openSession() {
        hotelRetrieval.openSession();
        try {
            bw = new BufferedWriter(new FileWriter("./log.txt", true));
            log("Ein Benutzer hat die Session gestartet", LogType.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeSession() {
        hotelRetrieval.closeSession();
        log("Ein Benutzer hat die Session beendet.", LogType.INFO);
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String message, LogType lt) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            bw.write("[" + lt.name() + " " + dtf.format(now) + "]: " + message);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
