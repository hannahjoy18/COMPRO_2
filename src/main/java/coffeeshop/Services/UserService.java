package coffeeshop.Services;

import coffeeshop.Models.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    private List<AppUser> appUsers;
    private final BCryptPasswordEncoder passwordEncoder;
    private final File userFile = new File("data/users.csv");  // unified file path

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        appUsers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    AppUser appUser = new AppUser();
                    appUser.setUsername(parts[0].trim());
                    appUser.setPassword(parts[1].trim());
                    appUsers.add(appUser);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User file not found. Starting fresh.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AppUser findByUsername(String username) {
        if (username == null) return null;
        return appUsers.stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst()
                .orElse(null);
    }

    public void save(AppUser appUser) throws IOException {
        if (appUser == null || appUser.getUsername() == null || appUser.getPassword() == null) {
            throw new IllegalArgumentException("User, username, and password must not be null");
        }

        String username = appUser.getUsername().trim();
        String rawPassword = appUser.getPassword().trim();

        if (findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        AppUser userToSave = new AppUser();
        userToSave.setUsername(username);
        userToSave.setPassword(hashedPassword);

        appUsers.add(userToSave);
        saveToFile();
    }

    private void saveToFile() throws IOException {
        if (!userFile.getParentFile().exists()) {
            userFile.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            writer.write("username,password");
            writer.newLine();
            for (AppUser user : appUsers) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        }
    }
}
