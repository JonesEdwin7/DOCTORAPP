package com.jones.Doctorsapp.controller;


import com.jones.Doctorsapp.model.Appointment;
import com.jones.Doctorsapp.model.AppointmentKey;
import com.jones.Doctorsapp.service.AppointmentService;
import com.jones.Doctorsapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("appointment")
public class AppointmentController  {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AuthenticationService authService;

    @PostMapping()
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment){
        String msg=null;
        HttpStatus status;
        try{
            appointmentService.bookAppointment(appointment);
            msg = " Appointment booked successfully";
            status = HttpStatus.OK;
        }catch(Exception e){
            msg = "Book Another Appointment as this appointment is already booked";
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(msg, status);
    }

    @DeleteMapping("appointment")
    ResponseEntity<Void> cancelAppointment(@RequestParam String userEmail, @RequestParam String token, @RequestBody AppointmentKey key)
    {

        HttpStatus status;
        if(authService.authenticate(userEmail,token))
        {
            appointmentService.cancelAppointment(key);
            status = HttpStatus.OK;
        }
        else
        {
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<Void>(status);

    }



}
