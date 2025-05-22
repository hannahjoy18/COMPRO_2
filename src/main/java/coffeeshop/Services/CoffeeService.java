package coffeeshop.Services;

import coffeeshop.Models.Coffee;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {

    private final List<Coffee> coffeeList = new ArrayList<>();

    // Constructor: load coffee data from file on startup
    public CoffeeService() {
        loadCoffeesFromFile();
    }

    public List<Coffee> findAll() {
        return new ArrayList<>(coffeeList);
    }

    public List<Coffee> searchCoffee(String keyword) {
        if (keyword == null || keyword.isEmpty()) return findAll();
        return coffeeList.stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Coffee getCoffee(int id) {
        return coffeeList.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addCoffee(@Valid Coffee coffee) {
        coffeeList.add(coffee);
        saveCoffeesToFile();
    }

    public void updateCoffee(int id, @Valid Coffee updatedCoffee) {
        for (int i = 0; i < coffeeList.size(); i++) {
            if (coffeeList.get(i).getId() == id) {
                coffeeList.set(i, updatedCoffee);
                saveCoffeesToFile();
                break;
            }
        }
    }

    public void deleteCoffee(int id) {
        coffeeList.removeIf(c -> c.getId() == id);
        saveCoffeesToFile();
    }

    public int getLastId() {
        return coffeeList.isEmpty() ? 0 : coffeeList.get(coffeeList.size() - 1).getId();
    }

    // Save coffee list to CSV file
    private void saveCoffeesToFile() {
        try (FileWriter writer = new FileWriter("coffee_list.csv")) {
            for (Coffee c : coffeeList) {
                writer.append(c.getId() + ","
                        + c.getName() + ","
                        + c.getType() + ","
                        + c.getSize() + ","
                        + c.getPrice() + ","
                        + c.getRoastLevel() + ","
                        + c.getOrigin() + ","
                        + c.isDecaf() + ","
                        + c.getStock() + ","
                        + c.getFlavorNotes() + ","
                        + c.getBrewMethod() + ","
                        + (c.getCoffeePicture() == null ? "" : c.getCoffeePicture()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load coffee list from CSV file
    public void loadCoffeesFromFile() {
        coffeeList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("coffee_list.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 12) continue; // skip invalid lines

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String type = parts[2];
                String size = parts[3];
                double price = Double.parseDouble(parts[4]);
                String roastLevel = parts[5];
                String origin = parts[6];
                boolean decaf = Boolean.parseBoolean(parts[7]);
                int stock = Integer.parseInt(parts[8]);
                String flavorNotes = parts[9];
                String brewMethod = parts[10];
                String coffeePicture = parts[11].isEmpty() ? null : parts[11];

                Coffee coffee = new Coffee(id, name, type, size, price, roastLevel, origin,
                        decaf, stock, flavorNotes, brewMethod, coffeePicture);
                coffeeList.add(coffee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
