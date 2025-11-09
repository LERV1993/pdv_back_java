package com.pdv.project.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pdv.project.entity.AuthCredentialsEntity;
import com.pdv.project.enums.Role;
import com.pdv.project.repository.AuthCredentialsRepository;
import com.pdv.project.security.AuthPassword;

import jakarta.transaction.Transactional;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final AuthCredentialsRepository authCredentialsRepository;
    
    public DataLoader(AuthCredentialsRepository authCredentialsRepository) {
        this.authCredentialsRepository = authCredentialsRepository;
    }
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createUserTest();
    }
    
    private void createUserTest() {
        createAdminUser();
        createUser();
    }
    
    private void createAdminUser() {
        String email = "admin@reservas.com";
        if (!authCredentialsRepository.existsByEmail(email)) {
            String password = AuthPassword.generatePassword("admin123");

            AuthCredentialsEntity admin = new AuthCredentialsEntity(
                null,
                email,
                password,
                Role.ADMIN
            );
            authCredentialsRepository.save(admin);
            System.out.println("User admin created sucessfully.");
        }
    }
    
    private void createUser() {
        String email = "usuario@test.com";
        if (!authCredentialsRepository.existsByEmail(email)) {
            String password = AuthPassword.generatePassword("user123");
            AuthCredentialsEntity user = new AuthCredentialsEntity(
                null,
                email,
                password,
                Role.USER
            );
            authCredentialsRepository.save(user);
            System.out.println("Normal user created successfully.");
        }
    }
}
