package Services;

import Models.AppUser;
import Models.Coffee;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {

    private final ArrayList<Coffee> coffees;
    private final String FILE_NAME = "coffee_list.csv";

    public CoffeeService() {
        coffees = new ArrayList<>();
        readFromDisk();
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public Coffee getCoffeeById(int id) {
        for (Coffee c : coffees) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    public void addCoffee(Coffee coffee) {
        coffee = new Coffee(
                getLastId() + 1,
                coffee.getName(),
                coffee.getType(),
                coffee.getSize(),
                coffee.getPrice(),
                coffee.getRoastLevel(),
                coffee.getOrigin(),
                coffee.isDecaf(),
                coffee.getStock(),
                coffee.getFlavorNotes(),
                coffee.getBrewMethod()
        );
        coffees.add(coffee);
        writeToDisk();
    }

    public void updateCoffee(int id, Coffee updated) {
        for (int i = 0; i < coffees.size(); i++) {
            if (coffees.get(i).getId() == id) {
                coffees.set(i, updated);
                writeToDisk();
                break;
            }
        }
    }

    public void deleteCoffee(int id) {
        coffees.removeIf(c -> c.getId() == id);
        writeToDisk();
    }

    public List<Coffee> searchCoffee(String keyword) {
        if (keyword.trim().isEmpty()) {
            return coffees;
        }
        return coffees.stream().filter(c -> {
                    return c.getName().toLowerCase().contains(keyword.toLowerCase())
                            || c.getType().toLowerCase().contains(keyword.toLowerCase())
                            || c.getOrigin().toLowerCase().contains(keyword.toLowerCase());
                }
        ).collect(Collectors.toList());
    }

    private int getLastId() {
        if (coffees.isEmpty()) {
            return 0;
        }
        return coffees.get(coffees.size() - 1).getId();
    }

    public void writeToDisk() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Coffee c : coffees) {
                bw.write(c.getId() + ","
                        + c.getName() + ","
                        + c.getType() + ","
                        + c.getSize() + ","
                        + c.getPrice() + ","
                        + c.getRoastLevel() + ","
                        + c.getOrigin() + ","
                        + c.isDecaf() + ","
                        + c.getStock() + ","
                        + String.join("|", c.getFlavorNotes()) + ","
                        + c.getBrewMethod());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Uh-oh! Error: " + e.getMessage());
        }
    }

    public void readFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("file not found");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                List<String> flavorNotes = new ArrayList<>();
                if (data.length > 9) {
                    String[] notes = data[9].split("\\|");
                    for (String note : notes) {
                        flavorNotes.add(note);
                    }
                }

                Coffee c = new Coffee(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        Double.parseDouble(data[4]),
                        data[5],
                        data[6],
                        Boolean.parseBoolean(data[7]),
                        Integer.parseInt(data[8]),
                        flavorNotes,
                        data.length > 10 ? data[10] : ""
                );
                coffees.add(c);
            }
        } catch (IOException e) {
            System.out.println("Uh-oh! Error: " + e.getMessage());
        }
    }

    public void saveToCsv(List<Coffee> coffeeList) {
    }

    public AppUser findByUsername(String username) {
        return null;
    }
}
