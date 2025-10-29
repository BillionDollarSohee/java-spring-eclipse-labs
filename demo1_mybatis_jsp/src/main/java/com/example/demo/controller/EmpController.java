package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.EmpService;



@Controller
@RequestMapping("/emp")
public class EmpController {

	private final EmpService empService;
	
	public EmpController(EmpService empService) {
		this.empService = empService;
	}
	@GetMapping
	public String list(Model model) {
		model.addAttribute("emps", empService.getAllEmpWithDept());
		return "emp/list";   //list.jsp 출력
	}
}
