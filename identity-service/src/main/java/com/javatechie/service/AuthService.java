package com.javatechie.service;

import com.javatechie.dto.DoctorRequest;
import com.javatechie.dto.DoctorResponse;
import com.javatechie.dto.PatientResponse;
import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MailService mailService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SystemTokenProvider systemTokenProvider;

    public void forgotPassword(String email) {
        UserCredential user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        repository.save(user);

        mailService.sendResetToken(email, token);
    }

    public String resetPassword(String token, String newPassword) {
        UserCredential user = repository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide ou expiré"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        repository.save(user);
        return user.getEmail();
    }

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);

        boolean isPatient = credential.getRoles().stream()
                .anyMatch(role -> "PATIENT".equalsIgnoreCase(role.getName()));

        if (isPatient) {
            createPatientInPatientMs(credential);
        }
        boolean isDoctor = credential.getRoles().stream()
                .anyMatch(role -> "DOCTOR".equalsIgnoreCase(role.getName()));

        if (isDoctor) {
            createDoctorInDoctorMs(credential);
        }
        return "user added to the system";
    }

    private void createPatientInPatientMs(UserCredential user) {
        String url = "http://localhost:8082/api/patients";

        Map<String, Object> request = new HashMap<>();
        request.put("firstName", user.getFirstName());
        request.put("lastName", user.getLastName());
        request.put("email", user.getEmail());
        request.put("phone", user.getPhone());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(systemTokenProvider.getSystemToken());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<PatientResponse> response = restTemplate.postForEntity(url, entity, PatientResponse.class);
            Long patientId = response.getBody().getId();

            System.out.println("✅ Patient créé dans patient-ms : ID = " + patientId);

            // Sauvegarder patientId dans l'utilisateur
            user.setPatientId(patientId);
            repository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Erreur création patient dans patient-ms: " + e.getMessage());
        }
    }


    private void createDoctorInDoctorMs(UserCredential user) {
        String url = "http://localhost:8081/api/doctors";  // URL du microservice doctor-ms

        // Préparation de la requête avec les données du médecin à créer
        Map<String, Object> request = new HashMap<>();
        request.put("firstName", user.getFirstName());
        request.put("lastName", user.getLastName());
        request.put("email", user.getEmail());
        request.put("phone", user.getPhone());
        // Ajoute d'autres champs si nécessaire, par ex. specialization, etc.

        // Configuration des headers, notamment pour envoyer un token JWT technique
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(systemTokenProvider.getSystemToken());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            // Appel POST vers doctor-ms avec récupération d'un DoctorDto (qui contient l'ID)
            ResponseEntity<DoctorResponse> response = restTemplate.postForEntity(url, entity, DoctorResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Long doctorId = response.getBody().getId();
                System.out.println("✅ Doctor créé dans doctor-ms : ID = " + doctorId);

                // Sauvegarde de doctorId dans l'utilisateur local
                user.setDoctorId(doctorId);
                repository.save(user);
            } else {
                throw new RuntimeException("Création doctor a échoué, réponse inattendue : " + response.getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Erreur création doctor dans doctor-ms : " + e.getMessage());
        }
    }


    public String generateToken(String email) {
        UserCredential credential = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails userDetails = mapToUserDetails(credential);
        return jwtService.generateToken(userDetails);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    private UserDetails mapToUserDetails(UserCredential credential) {
        return User.withUsername(credential.getEmail())
                .password(credential.getPassword())
                .authorities(
                        credential.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
