 import java.io.*;
import java.util.*;

class Vehicle {
    private String name;
    private String type;
    private boolean available;

    public Vehicle(String name, String type) {
        this.name = name;
        this.type = type;
        this.available = true;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void rent() {
        this.available = false;
    }

    public void returnVehicle() {
        this.available = true;
    }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + (available ? "Available" : "Rented");
    }
}

class Rental {
    private Vehicle vehicle;
    private String renterName;
    private String rentalPeriod;

    public Rental(Vehicle vehicle, String renterName, String rentalPeriod) {
        this.vehicle = vehicle;
        this.renterName = renterName;
        this.rentalPeriod = rentalPeriod;
    }

    @Override
    public String toString() {
        return "Renter: " + renterName + ", Vehicle: " + vehicle.getName() + ", Rental Period: " + rentalPeriod;
    }
}

class VehicleRental {
    private List<Vehicle> inventory;
    private List<Rental> rentals;

    public VehicleRental() {
        this.inventory = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    public void addVehicle(String name, String type) {
        Vehicle vehicle = new Vehicle(name, type);
        inventory.add(vehicle);
        saveVehicleToFile(vehicle);
    }

    public void rentVehicle(String vehicleName, String renterName, String rentalPeriod) throws Exception {
        Vehicle vehicle = findVehicle(vehicleName);
        if (vehicle == null) {
            throw new Exception("Vehicle not found.");
        }
        if (!vehicle.isAvailable()) {
            throw new Exception("Vehicle is currently rented.");
        }
        Rental rental = new Rental(vehicle, renterName, rentalPeriod);
        rentals.add(rental);
        vehicle.rent();
        saveRentalToFile(rental);
    }

    public void returnVehicle(String vehicleName) throws Exception {
        Vehicle vehicle = findVehicle(vehicleName);
        if (vehicle == null) {
            throw new Exception("Vehicle not found.");
        }
        if (vehicle.isAvailable()) {
            throw new Exception("Vehicle is not rented.");
        }
        vehicle.returnVehicle();
    }

    public List<Vehicle> getInventory() {
        return inventory;
    }

    private Vehicle findVehicle(String name) {
        for (Vehicle vehicle : inventory) {
            if (vehicle.getName().equalsIgnoreCase(name)) {
                return vehicle;
            }
        }
        return null;
    }

    private void saveVehicleToFile(Vehicle vehicle) {
        try (FileWriter writer = new FileWriter("vehicles.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(vehicle.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving vehicle to file: " + e.getMessage());
        }
    }

    private void saveRentalToFile(Rental rental) {
        try (FileWriter writer = new FileWriter("rentals.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(rental.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving rental to file: " + e.getMessage());
        }
    }

    public void loadInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader("vehicles.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\(");
                String name = parts[0];
                String type = parts[1].split("\\)")[0];
                addVehicle(name, type);
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory from file: " + e.getMessage());
        }
    }

    public void loadRentals() {
        try (BufferedReader br = new BufferedReader(new FileReader("rentals.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading rentals from file: " + e.getMessage());
        }
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VehicleRental rentalService = new VehicleRental();
        rentalService.loadInventory(); // Load existing vehicles from file
        int choice;

        do {
            System.out.println("\nVehicle Rental System");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Vehicle");
            System.out.println("3. Rent Vehicle");
            System.out.println("4. Return Vehicle");
            System.out.println("5. Load Rentals");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Inventory:");
                    for (Vehicle vehicle : rentalService.getInventory()) {
                        System.out.println(vehicle);
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Enter vehicle name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter vehicle type: ");
                        String type = scanner.nextLine();
                        rentalService.addVehicle(name, type);
                        System.out.println("Vehicle added successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Enter vehicle name to rent: ");
                        String vehicleName = scanner.nextLine();
                        System.out.print("Enter renter name: ");
                        String renterName = scanner.nextLine();
                        System.out.print("Enter rental period: ");
                        String rentalPeriod = scanner.nextLine();
                        rentalService.rentVehicle(vehicleName, renterName, rentalPeriod);
                        System.out.println("Vehicle rented successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.print("Enter vehicle name to return: ");
                        String vehicleName = scanner.nextLine();
                        rentalService.returnVehicle(vehicleName);
                        System.out.println("Vehicle returned successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    rentalService.loadRentals();
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
