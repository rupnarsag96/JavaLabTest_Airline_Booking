import java.io.*;
import java.util.*;

class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - $" + price;
    }
}

class Order {
    private List<MenuItem> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order Items:\n");
        for (MenuItem item : items) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("Total: $").append(calculateTotal());
        return sb.toString();
    }
}

class Restaurant {
    private List<MenuItem> menu;
    private List<Order> orders;

    public Restaurant() {
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addMenuItem(String name, double price) {
        MenuItem item = new MenuItem(name, price);
        menu.add(item);
        saveMenuToFile(item);
    }

    public void placeOrder(Order order) {
        orders.add(order);
        saveOrderToFile(order);
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    private void saveMenuToFile(MenuItem item) {
        try (FileWriter writer = new FileWriter("menu.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(item.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving menu to file: " + e.getMessage());
        }
    }

    private void saveOrderToFile(Order order) {
        try (FileWriter writer = new FileWriter("orders.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(order.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving order to file: " + e.getMessage());
        }
    }

    public void loadMenu() {
        try (BufferedReader br = new BufferedReader(new FileReader("menu.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - \\$");
                if (parts.length == 2) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    addMenuItem(name, price);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading menu from file: " + e.getMessage());
        }
    }

    public void loadOrders() {
        try (BufferedReader br = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading orders from file: " + e.getMessage());
        }
    }
}

public class RestaurantManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Restaurant restaurant = new Restaurant();
        restaurant.loadMenu(); // Load existing menu items from file
        int choice;

        do {
            System.out.println("\nRestaurant Management System");
            System.out.println("1. View Menu");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Place an Order");
            System.out.println("4. Load Orders");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Menu:");
                    for (MenuItem item : restaurant.getMenu()) {
                        System.out.println(item);
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Enter menu item name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter menu item price: ");
                        double price = scanner.nextDouble();
                        restaurant.addMenuItem(name, price);
                        System.out.println("Menu item added successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    Order order = new Order();
                    boolean ordering = true;
                    while (ordering) {
                        System.out.print("Enter menu item name to add to order (or type 'done' to finish): ");
                        String itemName = scanner.nextLine();
                        if (itemName.equalsIgnoreCase("done")) {
                            ordering = false;
                        } else {
                            boolean found = false;
                            for (MenuItem item : restaurant.getMenu()) {
                                if (item.getName().equalsIgnoreCase(itemName)) {
                                    order.addItem(item);
                                    found = true;
                                    System.out.println(itemName + " added to order.");
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Item not found in menu.");
                            }
                        }
                    }
                    restaurant.placeOrder(order);
                    System.out.println("Order placed successfully!");
                    break;
                case 4:
                    restaurant.loadOrders();
                    break;
                case 5:
                    System.out.println("Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }
}

    }


                