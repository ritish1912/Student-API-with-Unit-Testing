package com.project.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Student;
import com.project.handler.StudentNotFound;
import com.project.repositories.StudentRepository;
import com.project.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;

	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}


	public Student studentById(String enrollmentId) throws StudentNotFound {
		 Optional<Student> student = studentRepository.findById(enrollmentId);
		 if(!student.isPresent())
			 throw new StudentNotFound("No record found");
		 return student.get();
	}
	
	public void checkStudentBeforeDelete(String enrollmentId) {
		Optional<Student>student = studentRepository.findById(enrollmentId);
		if(!student.isPresent()) {
			throw new StudentNotFound("No record found");
			}

		}

	
	
	public void deleteStudent(String enrollmentId) {
		studentRepository.deleteById(enrollmentId);
	}


	
	public void updateStudentAddress(Student oldStudent, Student newstudent) {
		oldStudent.setAddress(newstudent.getAddress());
		studentRepository.save(oldStudent);
	}


	
	public Optional<Student> checkAlreadyExist(String enrollmentId) {
	return studentRepository.findById(enrollmentId);
	
	}


	

}
