package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;



@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String listUsers(Model model) { //함수의 parameter 로 Model 인터페이스 사용 자동 (모델 객체 주입)
		//1. ModelAndView > 데이타 담고 , view 정의  
		//2. Model 인터페이스 명시 > 객체를 넣어줍니다 (spring)
		
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "user/list";
	}
	
	@GetMapping("/new")  // /users/new
	public String showCreateForm() {
		return "user/form";    //form.jsp
	}
	
	@PostMapping
	public String createUser(User user) {
		userService.createUser(user);
		
		return "redirect:/users";
	}
	//수정하기 
	//1. 기존 데이터 보여주기         select
	//2. 데이터 수정하고 저장하기      udpate
		
	@GetMapping("/{id}/edit")  //기존 데이터 보여주기 (한건 데이터 출력) 동일
	public String showEditForm(@PathVariable("id") long id , Model model) {
			User user = userService.getUserById(id);
			model.addAttribute("user", user);
			
		return "user/edit";
		//  /WEB-INF/views/ + user/edit + .jsp		
		}
		
		
	@PostMapping("/{id}/edit")  //   /users/${user.id}/edit
	public String updateUser(@PathVariable("id") long id , User user) {
			//user.setId(id);
			//user.setId(id);  문제 해결
			userService.updateUser(user);
			
			return "redirect:/users";
			
		}
		
	@GetMapping("/{id}/delete")
	public String deleteUser(@PathVariable("id") long id ) {
			userService.deleteUser(id);
			
			return "redirect:/users";
		}
}
