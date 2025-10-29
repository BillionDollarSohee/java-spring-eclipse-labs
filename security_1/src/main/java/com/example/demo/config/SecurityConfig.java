package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// 스프링 부트는 xml가 아닌 애너테이션을 사용한 자바파일로 설정을 한다.
// 버전 5, 6 변화가 크다

/*
spring regacy 사용한 security 3.x.x
  <security:intercept-url pattern="/customer/noticeDetail.do" access="hasRole('ROLE_USER')" />
  <security:intercept-url pattern="/customer/noticeReg.do"    access="hasRole('ROLE_ADMIN')" />

spring boot   security 5.x.x  
  http.authorizeRequests()
		.antMatchers("/admin","/admin/**").hasRole("ADMIN")
		.antMatchers("/user","/user/**").hasAnyRole("USER","ADMIN")
		.antMatchers("/css/**" , "/js/**" , "/imges/**").permitAll()
		.antMatchers("/**").permitAll()
		.anyRequest().authenticated();
		
spring boot   security 6.x.x 람다표현식
버전을 프레임워크 6, 부트 3, 시큐리티 6에 자바까지 해야한다.
  
*/

@Configuration     
@EnableWebSecurity // 이클래스는 스프링 시큐리티 설정이 가능합니다.
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//인증과 권한을 한 곳에서 처리하기를 원함
		//자원에 대해서 (특정페이지, 특정파일 등)
		//예를들어 url이 도메인/admin 에는 ROLE_USER는 안되고 ROLE_ADMIN만 접근 가능
		
		http.authorizeHttpRequests(auth -> auth
								.requestMatchers("/admin/**").hasRole("ADMIN")
								.requestMatchers("/users/**").hasAnyRole("USER", "ADMIN")
								.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
								.requestMatchers("/", "/**").permitAll()
								.anyRequest().authenticated()
				).formLogin(From -> From.permitAll()
				).logout(logout -> logout.permitAll());
		
		return http.build();
	}
	
	/*
	예전에 인메모리 방식으로 사용자(암호, 권한) 만들어서 테스트 하던 방식
	인증, 권한을 in-memory 방식으로 인터셉터해서 처리
	<security:user-service>
		<security:user name="hong"  password="1004" authorities="ROLE_USER"/>
		<security:user name="admin" password="1004" authorities="ROLE_USER,ROLE_ADMIN"/>
	</security:user-service>
	*/
	
	@Bean
	public UserDetailsService userDetailsService() {  //사용자 정보 (원칙 DB 가지고.... in memory TEST
		
		// 인메모리 방식
		UserDetails user = User.builder()
						   .username("user")
						   .password(passwordEncoder().encode("1004"))
						   .roles("USER")		   
						   .build();
		
		UserDetails admin = User.builder()
						   .username("admin")
						   .password(passwordEncoder().encode("1004"))
						   .roles("ADMIN", "USER")		   
						   .build();
						 
		return new InMemoryUserDetailsManager(user, admin);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
