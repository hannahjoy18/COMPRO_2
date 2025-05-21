package Models;

import Services.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private CoffeeService coffeeService;

    @Override
    public void run(String... args) throws Exception {
        // Dummy password hash generation
//        String hash = new BCryptPasswordEncoder().encode("secret");
//        System.out.println(hash);
//
//        // Fetch user data from the CSV file
//        AppUser user = userService.findByUsername("john");
//        if (user != null) {
//            System.out.println(user.getUsername());
//        } else {
//            System.out.println("User not found.");
//        }
    }
}
