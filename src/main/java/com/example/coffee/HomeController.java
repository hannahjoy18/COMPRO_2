package com.example.coffee;

/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Controller class for handling coffee-related operations in a Spring Boot application.
 * Provides functionality to list, add, edit, update, and delete coffee entries.
 */
@Controller
public class HomeController {
    private List<Coffee> coffeeList = new ArrayList<>();


    /**
     * Initializes the controller with a default list of coffee objects.
     */
    public HomeController() {
        coffeeList.add(new Coffee(1, "Espresso", "Arabica", "Small", 3.50, "Dark", "Ethiopia", false, 10, Arrays.asList("Chocolate", "Nutty"), "Espresso"));
        coffeeList.add(new Coffee(2, "Latte", "Arabica", "Medium", 4.50, "Medium", "Brazil", false, 8, Arrays.asList("Creamy", "Sweet"), "Drip"));
        coffeeList.add(new Coffee(3, "Cappuccino", "Robusta", "Large", 5.00, "Medium", "Colombia", false, 12, Arrays.asList("Fruity", "Bold"), "French Press"));
        coffeeList.add(new Coffee(4, "Mocha", "Arabica", "Medium", 4.75, "Dark", "Guatemala", false, 6, Arrays.asList("Chocolate", "Smooth"), "Espresso"));
        coffeeList.add(new Coffee(5, "Americano", "Robusta", "Large", 3.25, "Light", "Kenya", false, 15, Arrays.asList("Citrus", "Balanced"), "Drip"));
    }
    /**
     * Retrieves the list of available coffees and adds them to the model.
     * @param model the model to store coffee data.
     * @return the index page displaying the coffee list.
     */
    @GetMapping("/")
    public String getCoffees(Model model) {
        model.addAttribute("coffees", coffeeList);
        return "index";
    }
    /**
     * Deletes a coffee entry based on the provided ID.
     * @param id the ID of the coffee to be deleted.
     * @return a redirect to the coffee list page.
     */
    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id) {
        coffeeList.removeIf(coffee -> coffee.getId() == id);
        return "redirect:/";
    }
    /**
     * Displays the form for adding a new coffee entry.
     * @return the view name for the add coffee form.
     */
    @GetMapping("/add")
    public String addCoffeeForm() {
        return "new";
    }
    /**
     * Saves a new coffee entry with the provided details.
     * @param name the name of the coffee.
     * @param price the price of the coffee.
     * @param type the type of the coffee (e.g., Arabica, Robusta).
     * @param size the size of the coffee (Small, Medium, Large).
     * @return a redirect to the coffee list page.
     */
    @PostMapping("/save")
    public String saveCoffee(@RequestParam String name, @RequestParam double price,
                             @RequestParam String type, @RequestParam String size) {
        int newId = coffeeList.isEmpty() ? 1 : coffeeList.get(coffeeList.size() - 1).getId() + 1;
        coffeeList.add(new Coffee(newId, name, type, size, price, "Medium", "Unknown", false, 0, new ArrayList<>(), "Drip"));
        return "redirect:/";
    }
    /**
     * Retrieves a coffee entry for editing based on its ID.
     * @param id the ID of the coffee to be edited.
     * @param model the model to store the coffee details.
     * @return the edit coffee view or a redirect if the coffee is not found.
     */
    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model) {
        for (Coffee coffee : coffeeList) {
            if (coffee.getId() == id) {
                model.addAttribute("coffee", coffee);
                return "edit";
            }
        }
        return "redirect:/";
    }
    /**
     * Updates an existing coffee entry with the provided details.
     * @param id the ID of the coffee to be updated.
     * @param name the new name of the coffee.
     * @param price the new price of the coffee.
     * @return a redirect to the coffee list page.
     */
    @PostMapping("/update")
    public String updateCoffee(@RequestParam int id, @RequestParam String name, @RequestParam double price) {
        for (Coffee coffee : coffeeList) {
            if (coffee.getId() == id) {
                coffee.setName(name);
                coffee.setPrice(price);
                break;
            }
        }
        return "redirect:/";
    }
}
