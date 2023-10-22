package com.jones.Doctorsapp.repository;


import com.jones.Doctorsapp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDoctorRepo extends JpaRepository<Doctor, Long> {


    Doctor findByDoctorId(Long docId);
}
