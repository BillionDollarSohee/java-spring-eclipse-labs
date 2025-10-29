package com.example.demo.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// 스프링 시큐리티에서 사용자 정보를 직접 정의(커스터마이징해서 유저디테일스 구현) 하겠다.
public class CustomerUser implements UserDetails{

	// 사용자 DTO
	private Users users;
	public CustomerUser(Users users) {
		this.users = users;
	}
	
	// 권한 정보를 수집해서 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return users.getAuthList()
					.stream()
					.map((auth) -> new SimpleGrantedAuthority(auth.getAuth()))
					.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return users.getUserPw();
	}

	@Override
	public String getUsername() {
		return users.getUserId();
	}

}
