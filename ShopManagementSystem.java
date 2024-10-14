import java.io.*;
import java.util.*;

class Product {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        this.stock -= quantity;
    }

    @Override
    public String toString() {
        return name + " - $" + price + " (Stock: " + stock + ")";
    }
}

class Sale {
    private List<Product> products;

    public Sale() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity) {
        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
        product.reduceStock(quantity);
    }

    public double calculateTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Sale Items:\n");
        for (Product product : products) {
            sb.append(product.getName()).append("\n");
        }
        sb.append("Total: $").append(calculateTotal());
        return sb.toString();
    }
}

class Shop {
    private List<Product> inventory;
    private List<Sale> sales;

    public Shop() {
        this.inventory = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    public void addProduct(String name, double price, int stock) {
        Product product = new Product(name, price, stock);
        inventory.add(product);
        saveProductToFile(product);
    }

    public void makeSale(String productName, int quantity) throws Exception {
        Product product = findProduct(productName);
        if (product == null) {
            throw new Exception("Product not found.");
        }
        if (product.getStock() < quantity) {
            throw new Exception("Not enough stock available.");
        }
        Sale sale = new Sale();
        sale.addProduct(product, quantity);
        sales.add(sale);
        saveSaleToFile(sale);
    }

    public List<Product> getInventory() {
        return inventory;
    }

    private Product findProduct(String name) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    private void saveProductToFile(Product product) {
        try (FileWriter writer = new FileWriter("inventory.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(product.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving product to file: " + e.getMessage());
        }
    }

    private void saveSaleToFile(Sale sale) {
        try (FileWriter writer = new FileWriter("sales.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(sale.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving sale to file: " + e.getMessage());
        }
    }

    public void loadInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader("inventory.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - \\$");
                String name = parts[0];
                String[] priceAndStock = parts[1].split(" \\(Stock: ");
                double price = Double.parseDouble(priceAndStock[0]);
                int stock = Integer.parseInt(priceAndStock[1].replaceAll("\\)", ""));
                addProduct(name, price, stock);
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory from file: " + e.getMessage());
        }
    }

    public void loadSales() {
        try (BufferedReader br = new BufferedReader(new FileReader("sales.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading sales from file: " + e.getMessage());
        }
    }
}

public class ShopManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Shop shop = new Shop();
        shop.loadInventory(); // Load existing inventory from file
        int choice;

        do {
            System.out.println("\nShop Management System");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Product");
            System.out.println("3. Make a Sale");
            System.out.println("4. Load Sales");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Inventory:");
                    for (Product product : shop.getInventory()) {
                        System.out.println(product);
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter product price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter product stock: ");
                        int stock = scanner.nextInt();
                        shop.addProduct(name, price, stock);
                        System.out.println("Product added successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Enter product name to sell: ");
                        String productName = scanner.nextLine();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        shop.makeSale(productName, quantity);
                        System.out.println("Sale made successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    shop.loadSales();
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
in.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
[
