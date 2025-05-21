package Services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private String username;
    private String password;

    private static List<UserService> appUsers;

    @PostConstruct
    public void init() throws IOException {
        appUsers = new ArrayList<>();
        File file = new File("users.csv");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        reader.readLine(); // skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                UserService user = new UserService();
                user.setUsername(parts[0]);
                user.setPassword(parts[1]);
                appUsers.add(user);
            }
        }
        reader.close();
    }

    public UserService findByUsername(String username) {
        return appUsers.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void save(UserService user) {
        appUsers.add(user);
    }

    public List<UserService> getAllUsers() {
        return appUsers;
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
