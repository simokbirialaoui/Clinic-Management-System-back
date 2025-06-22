package com.javatechie.service;

import com.javatechie.dto.DoctorDto;
import com.javatechie.dto.PatientDto;
import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
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
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MailService mailService;
    @Value("${doctor.service.url}")
    private String doctorServiceUrl;

    @Value("${patient.service.url}")
    private String patientServiceUrl;
    public void forgotPassword(String email) {
        UserCredential user = userCredentialRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userCredentialRepository.save(user);

        mailService.sendResetToken(email, token);
    }

    public String resetPassword(String token, String newPassword) {
        UserCredential user = userCredentialRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide ou expiré"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userCredentialRepository.save(user);
        return user.getEmail(); // Retourner l'e-mail
    }





    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);

        credential.getRoles().forEach(role -> {
            String roleName = role.getName().toUpperCase();
            if (roleName.equals("DOCTOR")) {
                DoctorDto doctor = new DoctorDto(
                        credential.getId(),
                        credential.getFirstName(),
                        credential.getLastName(),
                        credential.getEmail(),
                        credential.getPhone(),                      // ✅ ce champ existe bien
                        "Généraliste",
                        List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                        LocalTime.of(9, 0),
                        LocalTime.of(17, 0)
                );

                restTemplate.postForEntity(doctorServiceUrl + "/doctors", doctor, Void.class);
            } else if (roleName.equals("PATIENT")) {
                PatientDto patient = new PatientDto(
                        credential.getId(),
                        credential.getFirstName(),
                        credential.getLastName(),
                        credential.getEmail()
                );
                restTemplate.postForEntity(patientServiceUrl + "/patients", patient, Void.class);
            }
        });

        return "user added to the system";
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
