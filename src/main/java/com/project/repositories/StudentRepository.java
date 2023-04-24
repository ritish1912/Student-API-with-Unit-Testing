package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entities.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

}
