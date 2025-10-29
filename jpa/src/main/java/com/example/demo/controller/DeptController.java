package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Dept;
import com.example.demo.service.DeptService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dept")
public class DeptController {
	
	private final DeptService deptService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("list", deptService.findAll());
		return "dept/list"; //templates/dept/list.html
	}
	
	@GetMapping("/detail/{deptno}")
	public String detail(@PathVariable("deptno") int deptno, Model model) {
	    Dept dept = deptService.findById(deptno);
	    model.addAttribute("dept", dept);
	    return "dept/detail";
	}


	
	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("dept", new Dept());
		return "dept/add";
	}
	
	@PostMapping("/add")
	public String add(Dept dept) {
		deptService.save(dept);
		return "redirect:/dept";
	}
	
	@GetMapping("/edit/{deptno}")
	public String editForm(@PathVariable("deptno") int deptno,
							Model model) {
		model.addAttribute("dept", deptService.findById(deptno));
		return "dept/edit";
	}
	
	@PostMapping("/edit")
	public String edit(Dept dept) {
		deptService.save(dept);
		return "redirect:/dept";
	}
	
	@GetMapping("/delete/{deptno}")
	public String delete(@PathVariable("deptno") int deptno) {
	    deptService.delete(deptno);
	    return "redirect:/dept";
	}
	
}
