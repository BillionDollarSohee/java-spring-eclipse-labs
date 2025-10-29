package com.example.demo.service;

import com.example.demo.dto.UserAuth;
import com.example.demo.dto.Users;


public interface UserService { // crud 추상함수

	// 로그인 사용자 인증하는 함수
	Users login(String username);
	
	// 회원가입
	int join(Users users) throws Exception;
	
	// 회원가입 권한 등록 (트랜잭션이나 트리거를 사용해서 처리)
	int insertAuth(UserAuth userAuth) throws Exception;
	
}
