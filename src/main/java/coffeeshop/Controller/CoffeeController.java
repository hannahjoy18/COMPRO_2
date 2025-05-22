package coffeeshop.Controller;

import coffeeshop.Models.Coffee;
import coffeeshop.Services.CoffeeService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class CoffeeController {

    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    // Home page with optional search
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model, HttpSession session) {
        if (session.getAttribute ( "user" ) == null) {
            return "redirect:/login";
        }
        model.addAttribute ( "coffees", coffeeService.searchCoffee ( search ) );
        return "index";
    }

    // Add Coffee form
    @GetMapping("/add")
    public String addCoffee(Model model, HttpSession session) {
        if (session.getAttribute ( "user" ) == null) {
            return "redirect:/login";
        }
        model.addAttribute ( "coffee", new Coffee () );
        return "add";
    }

    // Coffee catalog
    @GetMapping("/catalog")
    public String catalogPage(HttpSession session, Model model) {
        if (session.getAttribute ( "user" ) == null) {
            return "redirect:/login";
        }
        model.addAttribute ( "coffees", coffeeService.findAll () );
        return "catalog";
    }

    // Delete coffee by ID
    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id, HttpSession session) {
        if (session.getAttribute ( "user" ) == null) {
            return "redirect:/login";
        }
        coffeeService.deleteCoffee ( id );
        return "redirect:/";
    }

    // Edit coffee form
    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model, HttpSession session) {
        if (session.getAttribute ( "user" ) == null) {
            return "redirect:/login";
        }
        Coffee coffee = coffeeService.getCoffee ( id );
        if (coffee != null) {
            model.addAttribute ( "coffee", coffee );
            return "edit";
        }
        return "redirect:/";
    }

    // Save new coffee
    @PostMapping("/save")
    public String saveCoffee(@ModelAttribute("coffee") @Valid Coffee coffee,
                             BindingResult bindingResult,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             HttpSession session) {
        if (session.getAttribute ( "user" ) == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors ()) {
            return "add";
        }

        handleImageUpload ( coffee, imageFile );

        coffee.setId ( coffeeService.getLastId () + 1 );
        coffeeService.addCoffee ( coffee );
        return "redirect:/";
    }

    // Update existing coffee
    @PostMapping("/update")
    public String updateCoffee(@ModelAttribute("coffee") @Valid Coffee coffee,
                               BindingResult bindingResult,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               Model model,
                               HttpSession session) {
        if (session.getAttribute ( "user" ) == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors ()) {
            model.addAttribute ( "coffee", coffee );
            return "edit";
        }

        Coffee existingCoffee = coffeeService.getCoffee ( coffee.getId () );
        if (existingCoffee != null) {
            if (!imageFile.isEmpty ()) {
                handleImageUpload ( coffee, imageFile );
            } else {
                coffee.setCoffeePicture ( existingCoffee.getCoffeePicture () );
            }

            coffeeService.updateCoffee ( coffee.getId (), coffee );
        }

        return "redirect:/";
    }

    private void handleImageUpload(Coffee coffee, MultipartFile imageFile) {
        if (!imageFile.isEmpty ()) {
            // Path to your static images directory inside the project
            String path = new File ( "src/main/resources/static/images/" ).getAbsolutePath () + "/";
            File uploadFolder = new File ( path );
            if (!uploadFolder.exists ()) {
                uploadFolder.mkdirs ();
            }

            // Generate a unique file name
            String fileName = UUID.randomUUID ()
                    + imageFile.getOriginalFilename ().substring ( imageFile.getOriginalFilename ().lastIndexOf ( "." ) );

            try {
                // Save file to static/images/
                imageFile.transferTo ( new File ( path + fileName ) );

                // Save file name only — since /images/ path is hardcoded in Thymeleaf
                coffee.setCoffeePicture ( fileName );

            } catch (IOException e) {
                System.out.println ( "File upload error: " + e.getMessage () );
            }
        }
    }
}
