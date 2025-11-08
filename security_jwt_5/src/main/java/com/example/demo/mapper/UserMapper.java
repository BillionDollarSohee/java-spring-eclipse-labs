package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.User;

/*
원칙 : @Mapper 가지는 interface (추상함수) / mapper.xml
예외적으로 합친 경우 (권장하지 않아요)
*/

@Mapper
public interface UserMapper {
	
	@Select("select * from user2 where username=#{username}")
	User findByUsername(String username);
	
	@Insert("insert into user2(username,password,role) values(#{username},#{password},#{role})" )
	void saveUser(User user);

}
