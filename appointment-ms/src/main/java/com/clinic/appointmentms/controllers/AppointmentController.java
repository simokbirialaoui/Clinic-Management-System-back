package com.clinic.appointmentms.controllers;

import com.clinic.appointmentms.dto.AppointmentRequestDTO;
import com.clinic.appointmentms.dto.AppointmentResponseDTO;
import com.clinic.appointmentms.entities.Appointment;
import com.clinic.appointmentms.repository.AppointmentRepository;
import com.clinic.appointmentms.service.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentServiceImpl appointmentService;

    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping
    public AppointmentResponseDTO createAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        return appointmentService.addAppointment(appointmentRequestDTO);
    }
    @GetMapping("/{id}")
    public AppointmentResponseDTO getAppointmentById(@PathVariable Long id) {
        return  appointmentService.getAppointmentById(id);
    }



    @PutMapping("/{id}")
    public AppointmentResponseDTO updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
      return  appointmentService.updateAppointment(id, appointmentRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
         appointmentService.deleteAppointment(id);
    }
}


