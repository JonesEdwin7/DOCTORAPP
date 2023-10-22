package com.jones.Doctorsapp.controller;

import com.jones.Doctorsapp.dto.SignInInput;
import com.jones.Doctorsapp.dto.SignInOutput;
import com.jones.Doctorsapp.dto.SignUpInput;
import com.jones.Doctorsapp.dto.SignUpOutput;
import com.jones.Doctorsapp.model.Doctor;
import com.jones.Doctorsapp.service.AuthenticationService;
import com.jones.Doctorsapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    AuthenticationService authService;

    @PostMapping("/signup")
    public SignUpOutput signUp(@RequestBody SignUpInput signUpDto){
        return patientService.signUp(signUpDto);
    }

    @PostMapping("/signin")
    public SignInOutput signIn(@RequestBody SignInInput signInDto){
        return patientService.signIn(signInDto);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors(@RequestParam String userEmail , @RequestParam String token){
        HttpStatus status;
        List<Doctor> allDoctors = null;

        //authenticate

        if(authService.authenticate(userEmail,token))
        {

            allDoctors =  patientService.getAllDoctors();
            status = HttpStatus.OK;
        }
        else
        {
            status = HttpStatus.FORBIDDEN;
        }



        return new ResponseEntity<List<Doctor>>(allDoctors, status);


    }


}