package com.project.services;

import java.util.Optional;

import com.project.entities.Student;

public interface StudentService {

	Student saveStudent(Student student);

	Student studentById(String studentId);

	void deleteStudent(String enrollmentId);

	void checkStudentBeforeDelete(String enrollmentId);

	void updateStudentAddress(Student oldStudent, Student newstudent);

	Optional<Student> checkAlreadyExist(String enrollmentId);

	

}
