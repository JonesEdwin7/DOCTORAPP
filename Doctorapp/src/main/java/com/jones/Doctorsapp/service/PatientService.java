package com.jones.Doctorsapp.service;

import com.jones.Doctorsapp.dto.SignInInput;
import com.jones.Doctorsapp.dto.SignInOutput;
import com.jones.Doctorsapp.dto.SignUpInput;
import com.jones.Doctorsapp.dto.SignUpOutput;
import com.jones.Doctorsapp.model.AppointmentKey;
import com.jones.Doctorsapp.model.AuthenticationToken;
import com.jones.Doctorsapp.model.Doctor;
import com.jones.Doctorsapp.model.Patient;
import com.jones.Doctorsapp.repository.IPatientRepo;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    IPatientRepo patientRepo;

    @Autowired
    AuthenticationService tokenService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    AppointmentService appointmentService;
    public SignUpOutput signUp(SignUpInput signUpDto) {

        //check if user exists or not based on email
        Patient patient = patientRepo.findFirstByPatientEmail(signUpDto.getUserEmail());

        if(patient != null)
        {
            throw new IllegalStateException("Patient already exists!!!!...sign in instead");
        }

//      encryption
        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        patient = new Patient(signUpDto.getUserFirstName(), signUpDto.getUserLastName(),
                signUpDto.getUserEmail(), encryptedPassword , signUpDto.getUserContact());

        patientRepo.save(patient);

        return new SignUpOutput("Patient registered","Patient created successfully");

    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(userPassword.getBytes());
        byte[] digested = md5.digest();

//        String hash = DatatypeConverter.printHexBinary(digested);
        String hash = DatatypeConverter.printHexBinary(digested);

        return hash;

    }

    public SignInOutput signIn(SignInInput signInDto) {
        //check if user exists or not based on email
        Patient patient = patientRepo.findFirstByPatientEmail(signInDto.getPatientEmail());

        if(patient == null)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }

        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword(signInDto.getPatientPassword());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

        //match it with database encrypted password

        boolean isPasswordValid = encryptedPassword.equals(patient.getPatientPassword());

        if(!isPasswordValid)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }

        AuthenticationToken token = new AuthenticationToken(patient);

        tokenService.saveToken(token);

        //set up output response

        return new SignInOutput("Authentication Successfull !!!", token.getToken());

    }

    public List<Doctor> getAllDoctors() {

        return doctorService.getAllDoctors();

    }

    public void cancelAppointment(AppointmentKey key) {

        appointmentService.cancelAppointment(key);

    }
}