package com.ochwada.authservice;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * *******************************************************
 * Package: com.ochwada.authservice
 * File: AuthSocialsApplication.java
 * Author: Ochwada
 * Date: Tuesday, 05.Aug.2025, 1:25 PM
 * Description:
 * Objective:
 * *******************************************************
 */

@SpringBootApplication
public class AuthSocialsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthSocialsApplication.class, args);
    }


    static {
        // Load environment variables from .env file
        // Ignores file if missing (useful for production environments like Heroku)
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // List of expected keys to load from the .env file
        String[] envVars =
                {
                        "GOOGLE_CLIENT_ID",
                        "GOOGLE_CLIENT_SECRET"
                };
        // Iterate through keys and set them as JVM system properties if found
        for (String key : envVars) {
            String value = dotenv.get(key);

            if (value != null) {
                System.setProperty(key, value);  // Makes it accessible via System.getProperty
                System.out.println("✅ " + key + " loaded and set.");
            } else {
                System.out.println("⚠️" + key + " not found in .env file. Skipping System.");
            }
        }
    }
}
