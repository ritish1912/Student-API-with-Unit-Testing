package com.project.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.entities.Address;
import com.project.entities.Student;

import com.project.handler.StudentNotFound;
import com.project.repositories.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class TestStudentServiceImpl {

	@Mock
	StudentRepository studentRepository;

	@InjectMocks
	StudentServiceImpl studentServiceImpl;
	

	@Test
	public void testSaveStudent() {

		Student student = buildTestingStudent();

		when(studentRepository.save(student)).thenReturn(student);

		Student result = studentServiceImpl.saveStudent(student);
		assertEquals(student, result);

	}
	
	@Test
	public void testSaveStudentException() {

		Student student = buildTestingStudent();
        when(studentRepository.findById(Mockito.anyString())).thenReturn(Optional.of(student));
        
		Optional<Student>testStudent = studentServiceImpl.checkAlreadyExist("Id");
		assertEquals(student.getName(), testStudent.get().getName());
	}
	
	@Test
	public void checkAlreadyExist() {

		Student student = buildTestingNewStudent();
		when(studentServiceImpl.checkAlreadyExist(Mockito.anyString())).thenReturn(Optional.of(student));
		Optional<Student>testStudent = studentServiceImpl.checkAlreadyExist("aiudf");
		assertEquals(student.getName(),testStudent.get().getName());
	}



	@Test
	 void testStudentById() {
		Student student = buildTestingStudent();

		when(studentRepository.findById("MAGIC0001")).thenReturn(Optional.of(student));

		Student returnedStudent = studentServiceImpl.studentById("MAGIC0001");

		assertEquals(student.getEnrollmentId(), returnedStudent.getEnrollmentId());

	}

	@Test
	 void testStudentByIdException() {
		String studentId = "MAGIC0001";
		when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
		StudentNotFound ex = assertThrows(StudentNotFound.class, ()->studentServiceImpl.studentById(studentId));
		assertEquals("No record found",ex.getMessage());
	
	}
	
	
	@Test
    void testDeleteStudent() {
	    
	    Student student = buildTestingStudent();
	    doNothing().when(studentRepository).deleteById(student.getEnrollmentId());

	    studentServiceImpl.deleteStudent(student.getEnrollmentId());
	    verify(studentRepository,times(1)).deleteById(Mockito.anyString());


	}

	
	@Test
	public void testCheckStudentBeforeDeleteWhenStudentIsNull() {
	    String studentId = "MAGIC0001";
	    when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
	   
	    StudentNotFound ex =assertThrows(StudentNotFound.class, ()->  studentServiceImpl.checkStudentBeforeDelete(studentId));
	    assertEquals("No record found",ex.getMessage());
	}
	
	
	@Test
	public void testCheckStudentBeforeDeleteWhenStudentIsNotNull() {

	    Student student = buildTestingNewStudent();
	    when(studentRepository.findById(Mockito.anyString())).thenReturn(Optional.of(student));
	    studentServiceImpl.checkStudentBeforeDelete(Mockito.anyString());
	    verify(studentRepository,times(1)).findById(Mockito.anyString());
	}
	
	
	
	@Test
	public void testUpdateStudentAddress() {
	    Student oldStudent = buildTestingStudent();

	    Student newStudent = buildTestingNewStudent();

	    Mockito.when(studentRepository.save(oldStudent)).thenReturn(newStudent);

	    studentServiceImpl.updateStudentAddress(oldStudent, newStudent);

	    assertEquals(oldStudent.getAddress(), newStudent.getAddress());

	    
	}
	

	private Student buildTestingStudent() {
		Student student = new Student();
		student.setEnrollmentId("MAGIC0001");

		Address address = new Address();
		//address.setId(10001L);
		address.setCity("Lucknow");
		address.setState("UP");
		address.setPincode("200001");
		student.setAddress(address);

		student.setName("Ritish Srivsatava");

		
		student.setDob("19/12/2000");

		

		student.setBranch("Computer science and Engg");

		return student;
	}
	
	private Student buildTestingNewStudent() {
		Student student = new Student();
		student.setEnrollmentId("MAGIC0001");

		Address address = new Address();
		//address.setId(10001L);
		address.setCity("Lucknow");
		address.setState("UP");
		address.setPincode("200777");
		student.setAddress(address);

		student.setName("Ritish Srivastava");

		student.setDob("19/12/2000");

		student.setBranch("Computer science and Engg");

		

		return student;
	}
}
