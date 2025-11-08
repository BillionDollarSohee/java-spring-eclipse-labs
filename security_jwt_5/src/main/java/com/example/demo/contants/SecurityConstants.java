package com.example.demo.contants;

/*
1. 옛날방식으로 static 상수로
2. enum 생성

Bearer 는
단순히 토큰 인증 방식의 한 종류를 식별하기 위한 키워드
Bearer는 영어로 “소지자(보유자)” 라는 뜻이다
즉, 이 토큰을 가진 사람(Bearer)이 곧 인증된 사용자라는 의미
*/

public final class SecurityConstants {

	// 헤더이름
	public static final String TOKEN_METHOD="Authorization";
	
	// 토큰 접두사 뒤에 공백까지
	public static final String TOKEN_PREFIX="Bearer "; 

	// 토큰 타입
	public static final String TOKEN_TYPE="JWT";
}
