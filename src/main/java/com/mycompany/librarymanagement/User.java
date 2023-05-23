 
package com.mycompany.librarymanagement;

import java.io.*;

public class User {
    private static final String FILE_PATH = "users.txt";
  
    public boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String storedUsername = parts[0];
                String storedPassword = parts[1];

                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + " " + password);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
