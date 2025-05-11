package com.clinic.appointmentms.service;

import com.clinic.appointmentms.dto.AppointmentRequestDTO;
import com.clinic.appointmentms.dto.AppointmentResponseDTO;
import com.clinic.appointmentms.entities.Appointment;
import com.clinic.appointmentms.feign.DoctorRestClient;
import com.clinic.appointmentms.feign.PatientRestClient;
import com.clinic.appointmentms.mappers.AppointmentMapper;
import com.clinic.appointmentms.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private  AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    DoctorRestClient doctorRestClient;
    @Autowired
    PatientRestClient patientRestClient;

    @Override
    public AppointmentResponseDTO addAppointment(AppointmentRequestDTO appointmentDTO) {
        Appointment appointment = Appointment.builder()
                .appointmentDateTime(appointmentDTO.getAppointmentDateTime())
                .status(appointmentDTO.getStatus())
                .patientId(appointmentDTO.getPatientId())
                .doctorId(appointmentDTO.getDoctorId())
                .doctor(doctorRestClient.getDoctorById(appointmentDTO.getDoctorId()))
                .patient(patientRestClient.getPatientById(appointmentDTO.getPatientId()))
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        AppointmentResponseDTO appointmentResponseDTO  = appointmentMapper.toResponseDTO(savedAppointment);

        return appointmentResponseDTO;
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();

        appointments.forEach(appointment -> {
            appointment.setDoctor(doctorRestClient.getDoctorById(appointment.getDoctorId()));
            appointment.setPatient(patientRestClient.getPatientById(appointment.getPatientId()));
        });

        return appointments.stream()
                .map(appointmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    @Override
    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        appointment.setDoctor(doctorRestClient.getDoctorById(appointment.getDoctorId()));
        appointment.setPatient(patientRestClient.getPatientById(appointment.getPatientId()));
        return appointmentMapper.toResponseDTO(appointment);
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO appointmentRequestDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        appointment.setAppointmentDateTime(appointmentRequestDTO.getAppointmentDateTime());
        appointment.setStatus(appointmentRequestDTO.getStatus());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        updatedAppointment.setDoctor(doctorRestClient.getDoctorById(updatedAppointment.getDoctorId()));
        updatedAppointment.setPatient(patientRestClient.getPatientById(updatedAppointment.getPatientId()));
        return appointmentMapper.toResponseDTO(updatedAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

}
