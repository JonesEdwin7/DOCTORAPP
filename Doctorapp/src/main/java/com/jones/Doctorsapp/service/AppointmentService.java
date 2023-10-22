package com.jones.Doctorsapp.service;

import com.jones.Doctorsapp.model.Appointment;
import com.jones.Doctorsapp.model.AppointmentKey;
import com.jones.Doctorsapp.repository.IAppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    IAppointmentRepo appointmentRepo;
    public void bookAppointment(Appointment appointment) {

        Optional<Appointment> myAppointment = appointmentRepo.findById(appointment.getId());
        if(myAppointment.isEmpty()){
            appointmentRepo.save(appointment);
        }else{
            throw new IllegalStateException("Appointment with id already present");
        }

    }

    public void cancelAppointment(AppointmentKey key) {
        appointmentRepo.deleteById(key);
    }
}
