package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 객체를 생성하고 주입할 수 있는 자바파일이다.
@EnableWebSecurity
public class securityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// http 프로토콜을 통해 들어오는 모든 요청은 이 SecurityFilterChain을 거친다
		
		// http.formLogin().disable(); // 메소드 체인방식 5v에만 가능 6버전은 람다식으로
		http.formLogin((login) -> login.disable()); // 람다 DSL표기 형식
		
		// 스프링 시큐리티가 기본적으로 제공하는 자동화 기능 비활성화
		http.httpBasic((basic) -> basic.disable());
		
		// CORF(Hidden 태그에 암호화된 문자열 -> 위변조 방지 -> 비활성화)
		http.csrf((csrf) -> csrf.disable());
		
		// JWT를 쓸거니까 세션을 통한 정보관리 비활성화
		http.sessionManagement(session -> 
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
	}
	
}
