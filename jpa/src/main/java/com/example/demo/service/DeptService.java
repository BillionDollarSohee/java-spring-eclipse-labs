package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Dept;
import com.example.demo.repository.DeptRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeptService {
	private final DeptRepository deptRepository;

	public List<Dept> findAll() {
		return deptRepository.findAll();
	}
	
	public Dept findById(int deptno) {
		return deptRepository.findById(deptno).orElse(null);
	}
	
	public void save(Dept dept) {
		deptRepository.save(dept);
	}
	
	public void delete(int deptno) {
		deptRepository.deleteById(deptno);
	}
}
