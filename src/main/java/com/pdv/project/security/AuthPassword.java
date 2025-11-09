package com.pdv.project.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthPassword {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Verify whether a plaintext password matches a BCrypt hash
     * 
     * @param rawPassword     password in plain text (input from user)
     * @param encodedPassword Hash BCrypt stored in db
     * @return true if password matches, false if not
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Generate a new hashed password to store in the database.
     * 
     * @param password
     * @return pasword hashed
     */
    public static String generatePassword(String password) {
        return encoder.encode(password);
    }

}
