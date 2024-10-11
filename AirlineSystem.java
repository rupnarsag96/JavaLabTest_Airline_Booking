package operation
import java.io.*;
import java.util.*;
import utility.*;

class AirlineSystem {
    private List<Flight> flights = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void bookFlight(String flightNumber, Passenger passenger) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                try {
                    flight.bookSeat();
                    bookings.add(new Booking(passenger, flight));
                    System.out.println("Booking successful: " + passenger.getName() + " for " + flightNumber);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return;
            }
        }
        System.out.println("Flight not found: " + flightNumber);
    }

    public void displayFlights() {
        System.out.println("Available Flights:");
        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(flights);
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            flights = (List<Flight>) ois.readObject();
            bookings = (List<Booking>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}
