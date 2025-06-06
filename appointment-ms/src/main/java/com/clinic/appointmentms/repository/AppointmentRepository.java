package com.clinic.appointmentms.repository;

import com.clinic.appointmentms.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Custom query methods can be defined here if needed
}
