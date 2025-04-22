package com.clinic.appointmentms.controllers;

import com.clinic.appointmentms.entities.Appointment;
import com.clinic.appointmentms.repository.AppointmentRepository;
import com.clinic.appointmentms.services.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;


    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping
    public String createAppointment(@RequestBody Appointment appointment) {
        // Ici tu peux ajouter la logique de persistance si besoin
        String msg = "Nouveau RDV pour patient: " + appointment.getPatientId() + " le " + appointment.getAppointmentDateTime();
        kafkaProducerService.sendMessage(msg);
        return "Rendez-vous créé et notification envoyée.";
    }
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
        return appointmentRepository.findById(id)
                .map(appointment -> {
                    appointment.setAppointmentDateTime(updatedAppointment.getAppointmentDateTime());
                    appointment.setStatus(updatedAppointment.getStatus());
                    return appointmentRepository.save(appointment);
                })
                .orElseGet(() -> {
                    updatedAppointment.setId(id);
                    return appointmentRepository.save(updatedAppointment);
                });
    }
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
    }
}


