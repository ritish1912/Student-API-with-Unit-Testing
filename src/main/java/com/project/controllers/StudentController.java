   package com.project.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.Student;
import com.project.handler.EnrollmentIdAlreadyExist;
import com.project.handler.StudentNotFound;
import com.project.services.StudentService;

@RestController
@RequestMapping("student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@PostMapping()
	public ResponseEntity<Student> addStudent(@Valid @RequestBody Student getStudent) {
		Optional<Student>student = studentService.checkAlreadyExist(getStudent.getEnrollmentId());
		if(student.isPresent()) {
			throw new EnrollmentIdAlreadyExist("Enrollment Already Exists");
		}
		studentService.saveStudent(getStudent);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
		
	@GetMapping()
	public ResponseEntity<Student> getUserById(@RequestParam String enrollmentId) throws  StudentNotFound{
		Student student = null;
		try {
			 student = studentService.studentById(enrollmentId);
		}
		catch(StudentNotFound ex)
		{
			throw new StudentNotFound("Record not found");
		}
		return ResponseEntity.ok(student);
	}
	
	@DeleteMapping("{enrollmentId}")
	public ResponseEntity<Void> deleteStudentById(@PathVariable String enrollmentId) {
	    studentService.checkStudentBeforeDelete(enrollmentId);
	    studentService.deleteStudent(enrollmentId);
	    return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PatchMapping("{enrollmentId}")
	public ResponseEntity<Void> updateStudentAddress(@PathVariable String enrollmentId, @RequestBody Student newstudent) {
	    Student oldStudent = studentService.studentById(enrollmentId);
	    studentService.updateStudentAddress(oldStudent, newstudent);
	    return ResponseEntity.status(HttpStatus.OK).build();
	}



}
