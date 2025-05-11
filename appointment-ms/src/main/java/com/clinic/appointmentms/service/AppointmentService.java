package com.clinic.appointmentms.service;

import com.clinic.appointmentms.dto.AppointmentRequestDTO;
import com.clinic.appointmentms.dto.AppointmentResponseDTO;
import com.clinic.appointmentms.feign.DoctorRestClient;
import com.clinic.appointmentms.feign.PatientRestClient;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseDTO addAppointment(AppointmentRequestDTO appointmentDTO);

    List<AppointmentResponseDTO> getAllAppointments();

    AppointmentResponseDTO getAppointmentById(Long id);

    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO appointmentRequestDTO);

    void deleteAppointment(Long id);
}
