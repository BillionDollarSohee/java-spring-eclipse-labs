package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {
	
	//Mapper.xml 파일 규칙 
	// 1. namespace="com.example.demo.mapper.UserMapper"
	// 2. 함수이름과 id 동일
	
	List<User> selectAll();
	User selectById(Long id);
	void insert(User user);
	void update(User user);
	void delete(Long id);
}
