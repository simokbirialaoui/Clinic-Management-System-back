package com.clinic.appointmentms.feign;

import com.clinic.appointmentms.model.Doctor;
import com.clinic.appointmentms.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "patient-ms")
public interface PatientRestClient {
    @GetMapping("/api/patients/{id}")
    Patient getPatientById(@PathVariable Long id);
    @GetMapping("/api/patients")
    List<Patient> getAllPatients();
}
