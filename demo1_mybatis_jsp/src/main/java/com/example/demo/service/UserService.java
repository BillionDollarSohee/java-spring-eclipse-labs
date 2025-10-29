package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

@Service
public class UserService {

	//userMapper 의존합니다
	@Autowired
	private UserMapper userMapper; //원칙 : setter , contructor , member field 주입도 가능
	
	public List<User> getAllUsers(){
		return userMapper.selectAll(); //@Mapper 연결 (주입)
	}
	public User getUserById(long id) {
		return userMapper.selectById(id);
	}
	
	public void createUser(User user) {
		userMapper.insert(user);
	}
	
	public void updateUser(User user) {
		userMapper.update(user);
	}
	
	public void deleteUser(long id) {
		userMapper.delete(id);
	}
}
