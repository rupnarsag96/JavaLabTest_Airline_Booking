package main
import java.util.Scanner;
import operation.*;
import entity.*;
public class AirlineBookingManagement {
    public static void main(String[] args) {
        AirlineSystem airlineSystem = new AirlineSystem();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        do {
            System.out.println("\nAirline Management System");
            System.out.println("1. Add Flight");
            System.out.println("2. Display Flights");
            System.out.println("3. Book Flight");
            System.out.println("4. Save to File");
            System.out.println("5. Load from File");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
           

            switch (choice) {
                case 1:
                    // Add Flight
                    System.out.print("Enter flight number: ");
                    String flightNumber = scanner.next();
                    System.out.print("Enter destination: ");
                    String destination = scanner.next();
                    System.out.print("Enter available seats: ");
                    int seats = scanner.nextInt();
                    airlineSystem.addFlight(new Flight(flightNumber, destination, seats));
                    System.out.println("Flight added successfully.");
                    break;

                case 2:
                    // Display Flights
                    airlineSystem.displayFlights();
                    break;

                case 3:
                    // Book Flight
                    System.out.print("Enter flight number to book: ");
                    String flightToBook = scanner.nextLine();
                    System.out.print("Enter passenger name: ");
                    String passengerName = scanner.nextLine();
                    System.out.print("Enter contact info: ");
                    String contactInfo = scanner.nextLine();
                    Passenger passenger = new Passenger(passengerName, contactInfo);
                    airlineSystem.bookFlight(flightToBook, passenger);
                    break;

                case 4:
                    // Save to File
                    airlineSystem.saveToFile("airline_data.dat");
                    System.out.println("Data saved to file.");
                    break;

                case 5:
                    // Load from File
                    airlineSystem.loadFromFile("airline_data.dat");
                    System.out.println("Data loaded from file.");
                    break;

                case 6:
                    // Exit
                    exit = true;
                    System.out.println("Exiting the system.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while(!exit);

        
    }
}
