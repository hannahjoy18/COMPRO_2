package Controller;

import Models.AppUser;
import Models.Coffee;
import Services.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CoffeeController {

    private final List<Coffee> coffeeList = new ArrayList<>();

    private CoffeeService coffeeService;

    @Autowired
    public void HomeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;

        coffeeList.add(new Coffee(1, "Espresso", "Arabica", "Small", 99.00, "Dark", "Ethiopia", false, 10, Arrays.asList("Chocolate", "Nutty"), "Espresso"));
        coffeeList.add(new Coffee(2, "Latte", "Arabica", "Medium", 99.00, "Medium", "Brazil", false, 8, Arrays.asList("Creamy", "Sweet"), "Drip"));
        coffeeList.add(new Coffee(3, "Cappuccino", "Robusta", "Large", 99.00, "Medium", "Colombia", false, 12, Arrays.asList("Fruity", "Bold"), "French Press"));
        coffeeList.add(new Coffee(4, "Mocha", "Arabica", "Medium", 99.00, "Dark", "Guatemala", false, 6, Arrays.asList("Chocolate", "Smooth"), "Espresso"));
        coffeeList.add(new Coffee(5, "Americano", "Robusta", "Large", 99.00, "Light", "Kenya", false, 15, Arrays.asList("Citrus", "Balanced"), "Drip"));

        coffeeService.saveToCsv(coffeeList);
    }


    @GetMapping("/")
    public String getCoffees(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Coffee> filteredCoffees = coffeeList;

        if (search != null && !search.isEmpty()) {
            filteredCoffees = coffeeList.stream()
                    .filter(coffee -> coffee.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("coffees", filteredCoffees);
        model.addAttribute("search", search);
        return "index";
    }

    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id) {
        coffeeList.removeIf(coffee -> coffee.getId() == id);
        coffeeService.saveToCsv(coffeeList);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addCoffee(Model model) {
        model.addAttribute("newCoffee", new Coffee());
        model.addAttribute("allFlavorOptions", Arrays.asList("Chocolate", "Citrus", "Nutty", "Fruity"));
        return "new";
    }

    @PostMapping("/save")
    public String saveCoffee(@ModelAttribute Coffee newCoffee,
                             @RequestParam(required = false) List<String> flavorNotes) {
        if (flavorNotes == null) {
            flavorNotes = new ArrayList<>();
        }
        newCoffee.setFlavorNotes(flavorNotes);

        int newId = coffeeList.isEmpty() ? 1 : coffeeList.getLast().getId() + 1;
        newCoffee.setId(newId);

        coffeeList.add(newCoffee);
        coffeeService.saveToCsv(coffeeList);

        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model) {
        Coffee found = coffeeList.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        if (found != null) {
            model.addAttribute("coffee", found);
            model.addAttribute("allFlavorOptions", Arrays.asList("Chocolate", "Citrus", "Nutty", "Fruity"));
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateCoffee(@ModelAttribute Coffee updatedCoffee,
                               @RequestParam(required = false) List<String> flavorNotes) {
        if (flavorNotes == null) {
            flavorNotes = new ArrayList<>();
        }
        updatedCoffee.setFlavorNotes(flavorNotes);

        for (Coffee coffee : coffeeList) {
            if (coffee.getId() == updatedCoffee.getId()) {
                coffee.setName(updatedCoffee.getName());
                coffee.setType(updatedCoffee.getType());
                coffee.setSize(updatedCoffee.getSize());
                coffee.setPrice(updatedCoffee.getPrice());
                coffee.setRoastLevel(updatedCoffee.getRoastLevel());
                coffee.setOrigin(updatedCoffee.getOrigin());
                coffee.setDecaf(updatedCoffee.isDecaf());
                coffee.setStock(updatedCoffee.getStock());
                coffee.setFlavorNotes(updatedCoffee.getFlavorNotes());
                coffee.setBrewMethod(updatedCoffee.getBrewMethod());
                break;
            }
        }

        coffeeService.saveToCsv(coffeeList);
        return "redirect:/";
    }
}
