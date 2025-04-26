package com.clinic.appointmentms.mappers;

import com.clinic.appointmentms.dto.AppointmentResponseDTO;
import com.clinic.appointmentms.entities.Appointment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentResponseDTO toResponseDTO(Appointment appointment) {

        AppointmentResponseDTO appointmentResponseDTO = new AppointmentResponseDTO();

        BeanUtils.copyProperties(appointment, appointmentResponseDTO);
        return  appointmentResponseDTO;
    }

}
