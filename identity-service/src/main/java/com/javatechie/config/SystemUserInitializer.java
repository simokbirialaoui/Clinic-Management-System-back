package com.javatechie.config;

import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SystemUserInitializer implements CommandLineRunner {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    public SystemUserInitializer(UserCredentialRepository userCredentialRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userCredentialRepository = userCredentialRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String email = "system@clinic.com";
        if (userCredentialRepository.findByEmail(email).isEmpty()) {
            UserCredential user = new UserCredential();
            user.setEmail(email);
            user.setFirstName("System");
            user.setLastName("User");
            user.setPassword(passwordEncoder.encode("SecurePassword!")); // à sécuriser ensuite
            user.setDeleted(false);
            user.setPhone("0000000000");

            userCredentialRepository.save(user);
            System.out.println("✅ Utilisateur système créé.");
        } else {
            System.out.println("ℹ️ Utilisateur système déjà existant.");
        }
    }
}
