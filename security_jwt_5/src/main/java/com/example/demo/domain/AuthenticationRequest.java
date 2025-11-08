package com.example.demo.domain;

import lombok.Data;
// 클라이언트의 username, pw를 객체로 받아서 가공

@Data
public class AuthenticationRequest {
	private String username;
	private String password;
}
