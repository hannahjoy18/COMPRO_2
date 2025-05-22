package coffeeshop.Controller;

import coffeeshop.Models.AppUser;
import coffeeshop.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class AuthController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new AppUser());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid AppUser formUser,
                        BindingResult bindingResult,
                        HttpSession session,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        AppUser foundUser = userService.findByUsername(formUser.getUsername());

        System.out.println("Form password: " + formUser.getPassword());
        System.out.println("Stored hashed password: " + (foundUser != null ? foundUser.getPassword() : "null"));

        if (foundUser != null
                && passwordEncoder.matches(formUser.getPassword().trim(), foundUser.getPassword())) {
            session.setAttribute("user", foundUser);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid AppUser newUser,
                           BindingResult bindingResult,
                           Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userService.findByUsername(newUser.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        userService.save(newUser);  // No hashing here — handled in service
        return "redirect:/login";
    }
}
