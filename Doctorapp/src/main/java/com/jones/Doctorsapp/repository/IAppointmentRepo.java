package com.jones.Doctorsapp.repository;


import com.jones.Doctorsapp.model.Appointment;
import com.jones.Doctorsapp.model.AppointmentKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppointmentRepo extends JpaRepository<Appointment, AppointmentKey> {

//    public String findByIdAppId(Long id);
}
