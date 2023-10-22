package com.jones.Doctorsapp.repository;


import com.jones.Doctorsapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientRepo extends JpaRepository<Patient, Long> {

    Patient findFirstByPatientEmail(String userEmail);
}
