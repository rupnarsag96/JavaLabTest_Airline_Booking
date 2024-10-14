 import java.io.*;
import java.util.*;

class Room {
    private String roomNumber;
    private String type;
    private boolean available;

    public Room(String roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = true;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void book() {
        this.available = false;
    }

    public void free() {
        this.available = true;
    }

    @Override
    public String toString() {
        return roomNumber + " (" + type + ") - " + (available ? "Available" : "Booked");
    }
}

class Booking {
    private Room room;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;

    public Booking(Room room, String guestName, String checkInDate, String checkOutDate) {
        this.room = room;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room: " + room.getRoomNumber() + ", Check-in: " + checkInDate + ", Check-out: " + checkOutDate;
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Booking> bookings;

    public Hotel() {
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addRoom(String roomNumber, String type) {
        Room room = new Room(roomNumber, type);
        rooms.add(room);
        saveRoomToFile(room);
    }

    public void bookRoom(String roomNumber, String guestName, String checkInDate, String checkOutDate) throws Exception {
        Room room = findRoom(roomNumber);
        if (room == null) {
            throw new Exception("Room not found.");
        }
        if (!room.isAvailable()) {
            throw new Exception("Room is already booked.");
        }
        Booking booking = new Booking(room, guestName, checkInDate, checkOutDate);
        bookings.add(booking);
        room.book();
        saveBookingToFile(booking);
    }

    public void freeRoom(String roomNumber) throws Exception {
        Room room = findRoom(roomNumber);
        if (room == null) {
            throw new Exception("Room not found.");
        }
        if (room.isAvailable()) {
            throw new Exception("Room is not booked.");
        }
        room.free();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    private Room findRoom(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equalsIgnoreCase(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    private void saveRoomToFile(Room room) {
        try (FileWriter writer = new FileWriter("rooms.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(room.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving room to file: " + e.getMessage());
        }
    }

    private void saveBookingToFile(Booking booking) {
        try (FileWriter writer = new FileWriter("bookings.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(booking.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking to file: " + e.getMessage());
        }
    }

    public void loadRooms() {
        try (BufferedReader br = new BufferedReader(new FileReader("rooms.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\(");
                String roomNumber = parts[0];
                String type = parts[1].split("\\)")[0];
                addRoom(roomNumber, type);
            }
        } catch (IOException e) {
            System.out.println("Error reading rooms from file: " + e.getMessage());
        }
    }

    public void loadBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading bookings from file: " + e.getMessage());
        }
    }
}

public class HotelManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();
        hotel.loadRooms(); // Load existing rooms from file
        int choice;

        do {
            System.out.println("\nHotel Management System");
            System.out.println("1. View Rooms");
            System.out.println("2. Add Room");
            System.out.println("3. Book Room");
            System.out.println("4. Free Room");
            System.out.println("5. Load Bookings");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Available Rooms:");
                    for (Room room : hotel.getRooms()) {
                        System.out.println(room);
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Enter room number: ");
                        String roomNumber = scanner.nextLine();
                        System.out.print("Enter room type: ");
                        String type = scanner.nextLine();
                        hotel.addRoom(roomNumber, type);
                        System.out.println("Room added successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Enter room number to book: ");
                        String roomNumber = scanner.nextLine();
                        System.out.print("Enter guest name: ");
                        String guestName = scanner.nextLine();
                        System.out.print("Enter check-in date (YYYY-MM-DD): ");
                        String checkInDate = scanner.nextLine();
                        System.out.print("Enter check-out date (YYYY-MM-DD): ");
                        String checkOutDate = scanner.nextLine();
                        hotel.bookRoom(roomNumber, guestName, checkInDate, checkOutDate);
                        System.out.println("Room booked successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.print("Enter room number to free: ");
                        String roomNumber = scanner.nextLine();
                        hotel.freeRoom(roomNumber);
                        System.out.println("Room freed successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    hotel.loadBookings();
                    break;
                case 6:
                    System.out.println("Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
