package com.project.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entities.Address;
import com.project.entities.Student;
import com.project.handler.StudentNotFound;
import com.project.services.impl.StudentServiceImpl;



@WebMvcTest(StudentController.class)
public class TestStudentController {
	@MockBean
	private StudentServiceImpl studentServiceImpl;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void testGetStudentById() throws Exception {
	    Student student = buildTestingStudent();
	    when(studentServiceImpl.studentById(student.getEnrollmentId())).thenReturn(student);

	    mockMvc.perform(get("/student").param("enrollmentId", "MAGIC0001"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.enrollmentId", is(student.getEnrollmentId())))
        .andExpect(jsonPath("$.name", is(student.getName())))
        .andReturn();
}
	@Test
	public void testGetStudentByIdException() throws Exception {
	    Student student = buildTestingStudent();
	    when(studentServiceImpl.studentById(student.getEnrollmentId())).thenThrow(StudentNotFound.class);

	    mockMvc.perform(get("/student").param("enrollmentId", "MAGIC0001"))
	    .andExpect(status().isBadRequest());
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//        .andExpect(jsonPath("$.enrollmentId", is(student.getEnrollmentId())))
//        .andExpect(jsonPath("$.name", is(student.getName())))
//        .andReturn();
}

	
	@Test
	public void createStudent_success() throws Exception {
		Student student = buildTestingStudent();
		Mockito.when(studentServiceImpl.studentById(student.getEnrollmentId())).thenReturn(null);
		
		Mockito.when(studentServiceImpl.saveStudent(any(Student.class))).thenReturn(student);
		
		mockMvc.perform(post("/student")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(student)))
				.andExpect(status().isOk());

	}
	
	@Test
	void addStudentIdAlreadyExistFailure() throws Exception{
	    Student student = buildTestingStudent();
	    
	    Mockito.when(studentServiceImpl.checkAlreadyExist(student.getEnrollmentId())).thenReturn(Optional.of(student));
	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(this.objectMapper.writeValueAsString(student));
	    
	    mockMvc.perform(mockRequest)
	    .andExpect(status().isBadRequest());
	}
	
	@Test
    void testAddStudentValidationFailure() throws Exception{
        
        Student student= new Student();
        student.setEnrollmentId("MAGIC0023");
        
        Address address = new Address();
        address.setCity("");
        address.setState("");
        address.setPincode("238888");
        student.setAddress(address);
        student.setName("");
        student.setBranch("Computer science and Engg");
        student.setDob("");
        
        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(student));
        
        mockMvc.perform(mockRequest)
        .andExpect(status().isBadRequest());
    }

	@Test
	void testDeleteStudentById() throws Exception {
	    
	    Student student = buildTestingStudent();
	    mockMvc.perform(delete("/student/{studentId}", student.getEnrollmentId()))
	            .andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateStudentAddress() throws Exception {
	    Student oldStudent = buildTestingStudent();

	    Student newStudent = buildTestingNewStudent();

	    when(studentServiceImpl.studentById("MAGIC0001")).thenReturn(oldStudent);
	    
	    Mockito.doNothing().when(studentServiceImpl).updateStudentAddress(oldStudent, newStudent);

	    mockMvc.perform(patch("/student/MAGIC0001")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"enrollmentId\":\"MAGIC0001\",\"name\":\"Rishabh Kumar\",\"dob\":\"01/05/1999\",\"branch\":\"Computer science and Engg\",\"address\":{\"city\":\"Meerut\",\"state\":\"UP\",\"pincode\":\"200777\"}}"))
	            .andExpect(status().isOk());

	    
	}
	

	private Student buildTestingStudent() {
		Student student = new Student();
		student.setEnrollmentId("MAGIC0001");

		Address address = new Address();
		//address.setId(10001L);
		address.setCity("Ghaziabad");
		address.setState("UP");
		address.setPincode("200001");
		student.setAddress(address);

		student.setName("Rishabh Kumar");

		
		student.setDob("01/05/1999");

		student.setBranch("Computer science and Engg");

		return student;
	}
	
	private Student buildTestingNewStudent() {
		Student student = new Student();
		student.setEnrollmentId("MAGIC0001");

		Address address = new Address();
		//address.setId(10001L);
		address.setCity("Meerut");
		address.setState("UP");
		address.setPincode("200777");
		student.setAddress(address);

		student.setName("Rishabh Kumar");

		
		student.setDob("01/05/1990");

		student.setBranch("Computer science and Engg");

		return student;
	}
}
