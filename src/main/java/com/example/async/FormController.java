package com.example.async;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class FormController {

    @GetMapping("/")
    public String showForm() {
        return "form"; // Refers to templates/practice_form.html
    }

    @PostMapping("/submit")
    @ResponseBody
    public String processForm(@RequestParam Map<String, String> formData) {
        formData.forEach((key, value) -> System.out.println(key + ": " + value));
        return "Form submitted successfully!";
    }
}



