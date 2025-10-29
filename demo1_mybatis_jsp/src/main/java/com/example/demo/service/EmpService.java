package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.EmpMapper;
import com.example.demo.model.EmpDeptDto;

import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor  //lombok 통해서 주입  .... 편해요 개발 ^^
public class EmpService {
	
	private final EmpMapper empMapper;
	
	public EmpService(EmpMapper empMapper) {
		this.empMapper = empMapper;
	}
	
	public List<EmpDeptDto> getAllEmpWithDept(){
		return empMapper.findAllWithDept();
	}
	
}
