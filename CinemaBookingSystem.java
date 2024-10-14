import java.io.*;
import java.util.*;
class Booking {
    private String name;
    private int row;
    private int column;

    public Booking(String name, int row, int column) {
        this.name = name;
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Seat: [" + row + ", " + column + "]";
    }
}

class Cinema {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    private boolean[][] seats = new boolean[ROWS][COLUMNS];
    private List<Booking> bookings = new ArrayList<>();

    public void showAvailableSeats() {
        System.out.println("Available Seats:");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (seats[i][j]) {
                    System.out.print("[X] "); // Booked
                } else {
                    System.out.print("[O] "); // Available
                }
            }
            System.out.println();
        }
    }

    public void bookTicket(String name, int row, int column) throws Exception {
        if (row < 0 || row >= ROWS || column < 0 || column >= COLUMNS) {
            throw new IllegalArgumentException("Invalid seat number.");
        }

        if (seats[row][column]) {
            throw new Exception("This seat is already booked.");
        } else {
            seats[row][column] = true;
            Booking booking = new Booking(name, row, column);
            bookings.add(booking);
            saveBookingToFile(booking);
            System.out.println("Ticket booked successfully for seat [" + row + ", " + column + "]!");
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

public class CinemaBookingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cinema cinema = new Cinema();
        int choice;

        do {
            System.out.println("\nCinema Booking System");
            System.out.println("1. Show Available Seats");
            System.out.println("2. Book a Ticket");
            System.out.println("3. Load Bookings from File");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    cinema.showAvailableSeats();
                    break;
                case 2:
                    try {
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter row (0 to 4): ");
                        int row = scanner.nextInt();
                        System.out.print("Enter column (0 to 4): ");
                        int column = scanner.nextInt();
                        cinema.bookTicket(name, row, column);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    cinema.loadBookings();
                    break;
                case 4:
                    System.out.println("Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        
    }
}

                              