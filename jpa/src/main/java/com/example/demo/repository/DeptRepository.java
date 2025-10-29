package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Dept;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer>{

}
