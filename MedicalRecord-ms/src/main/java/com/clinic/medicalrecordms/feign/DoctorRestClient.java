package com.clinic.medicalrecordms.feign;


import com.clinic.medicalrecordms.model.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "doctor-ms")
public interface DoctorRestClient {

    @GetMapping("/api/doctors/{id}")
    Doctor getDoctorById(@PathVariable Long id);

    @GetMapping("/api/doctors")
    List<Doctor> getAllDoctors();

}
