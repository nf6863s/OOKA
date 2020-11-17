import Buchungssystem.Port;

public class Test {
    public static void main(String[] args) {
        Port port = new Port(null);
        port.openSession();
        port.getHotelByName("test");
        port.closeSession();
    }
}
