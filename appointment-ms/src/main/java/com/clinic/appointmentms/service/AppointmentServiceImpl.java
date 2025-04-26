package com.clinic.appointmentms.service;

import com.clinic.appointmentms.dto.AppointmentRequestDTO;
import com.clinic.appointmentms.dto.AppointmentResponseDTO;
import com.clinic.appointmentms.entities.Appointment;
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


    @Override
    public AppointmentResponseDTO addAppointment(AppointmentRequestDTO appointmentDTO) {
        Appointment appointment = Appointment.builder()
                .appointmentDateTime(appointmentDTO.getAppointmentDateTime())
                .status(appointmentDTO.getStatus())
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        AppointmentResponseDTO appointmentResponseDTO  = appointmentMapper.toResponseDTO(savedAppointment);

        return appointmentResponseDTO;
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentResponseDTO> appointmentResponseDTOs = appointments.stream()
                .map(appointmentMapper::toResponseDTO)
                .collect(Collectors.toList());
        return appointmentResponseDTOs;
    }
    @Override
    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        return appointmentMapper.toResponseDTO(appointment);
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO appointmentRequestDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        appointment.setAppointmentDateTime(appointmentRequestDTO.getAppointmentDateTime());
        appointment.setStatus(appointmentRequestDTO.getStatus());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
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
