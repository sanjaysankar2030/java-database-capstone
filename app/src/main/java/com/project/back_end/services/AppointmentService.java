package com.project.back_end.services;
import org.springframework.stereotype.Service;

import com.project.back_end.models.Appointment;

import com.project.back_end.controllers.AppointmentController;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.PatientRepository;

import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

@Service 
public class AppointmentService {
// 1. **Add @Service Annotation**:
//    - To indicate that this class is a service layer class for handling business logic.
//    - The `@Service` annotation should be added before the class declaration to mark it as a Spring service component.
//    - Instruction: Add `@Service` above the class definition.

// 2. **Constructor Injection for Dependencies**:
//    - The `AppointmentService` class requires several dependencies like `AppointmentRepository`, `Service`, `TokenService`, `PatientRepository`, and `y`.
//    - These dependencies should be injected through the constructor.
//    - Instruction: Ensure constructor injection is used for proper dependency management in Spring.
    private AppointmentRepository appointmentRepository;
    private TokenService tokenService;
    private Service service;
    private PatientRepository patientRepository;
    private AppointmentController appointmentController;

// 3. **Add @Transactional Annotation for Methods that Modify Database**:
//    - The methods that modify or update the database should be annotated with `@Transactional` to ensure atomicity and consistency of the operations.
//    - Instruction: Add the `@Transactional` annotation above methods that interact with the database, especially those modifying data.
    @Transactional
    public int bookappointment(@NonNull Appointment appointment){
        try{
            appointmentRepository.save(appointment);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

// 4. **Book Appointment Method**:
//    - Responsible for saving the new appointment to the database.
//    - If the save operation fails, it returns `0`; otherwise, it returns `1`.
//    - Instruction: Ensure that the method handles any exceptions and returns an appropriate result code.
    @Transactional
    public int updateAppointment(@NonNull Long appointmentId,Appointment updatedAppointment){
        try{
            Appointment existingAppointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found"));

        // 2. Update fields
        existingAppointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
        existingAppointment.setDoctor(updatedAppointment.getDoctor());
        existingAppointment.setPatient(updatedAppointment.getPatient());
        existingAppointment.setStatus(updatedAppointment.getStatus());

        // 3. Save updated appointment
        appointmentRepository.save(existingAppointment);

        return 1; // success
        }
        catch(Exception e){
            return 0;
    }
}


// 5. **Update Appointment Method**:
//    - This method is used to update an existing appointment based on its ID.
//    - It validates whether the patient ID matches, checks if the appointment is available for updating, and ensures that the doctor is available at the specified time.
//    - If the update is successful, it saves the appointment; otherwise, it returns an appropriate error message.
//    - Instruction: Ensure proper validation and error handling is included for appointment updates.

@Transactional
public int cancelAppointment(@NonNull Long appointmentId, Long patientId) {
    try {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getPatient().getId().equals(patientId)) {
            throw new RuntimeException("Unauthorized: Patient does not own this appointment");
        }

        appointmentRepository.delete(appointment);
        return 1;

    } catch (Exception e) {
        return 0;
    }
}
// 6. **Cancel Appointment Method**:
//    - This method cancels an appointment by deleting it from the database.
//    - It ensures the patient who owns the appointment is trying to cancel it and handles possible errors.
//    - Instruction: Make sure that the method checks for the patient ID match before deleting the appointment.


@Transactional
public Map<String, Object> getAppointments(Long doctorId, LocalDate date, String pname) {

    Map<String, Object> response = new HashMap<>();

    try {
        // 1. Define start and end of the day
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        // 2. Fetch appointments
        List<Appointment> appointments =
                appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                        doctorId,
                        startOfDay,
                        endOfDay
                );

        // 3. Filter by patient name if provided
        if (pname != null && !pname.isEmpty()) {
            appointments = appointments.stream()
                    .filter(a -> a.getPatient().getName().equalsIgnoreCase(pname))
                    .toList();
        }

        // 4. Put list into map
        response.put("status", 1);
        response.put("appointments", appointments);

    } catch (Exception e) {
        response.put("status", 0);
        response.put("appointments", new ArrayList<>());
        response.put("message", "Error fetching appointments");
    }

    return response;
}

// 7. **Get Appointments Method**:
//    - This method retrieves a list of appointments for a specific doctor on a particular day, optionally filtered by the patient's name.
//    - It uses `@Transactional` to ensure that database operations are consistent and handled in a single transaction.
//    - Instruction: Ensure the correct use of transaction boundaries, especially when querying the database for appointments.

// 8. **Change Status Method**:
//    - This method updates the status of an appointment by changing its value in the database.
//    - It should be annotated with `@Transactional` to ensure the operation is executed in a single transaction.
//    - Instruction: Add `@Transactional` before this method to ensure atomicity when updating appointment status.

}
