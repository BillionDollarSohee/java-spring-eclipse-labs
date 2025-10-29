package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration     
@EnableWebSecurity 
@RequiredArgsConstructor 
public class SecurityConfig {
	
	private final DataSource dataSource; // 프로퍼티에 있는 datasource 롬복으로 주입
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth
                					.requestMatchers("/admin/**").hasRole("ADMIN")
                					.requestMatchers("/user/**").hasAnyRole("USER","ADMIN") //ROLE_USER , ROLE_ADMIN
                					.requestMatchers("/css/**" , "/js/**" , "/images/**").permitAll()
            						.anyRequest().permitAll())
									.formLogin(withDefaults()); //로그인 방식 기본값 유지
		
		http.logout(logout -> logout.logoutUrl("/logout")           // 로그아웃 요청 받을 URL
									.logoutSuccessUrl("/")          // 로그아웃 성공 후 URL
									.deleteCookies("JSESSIONID")    // 세션 쿠키 삭제
									.invalidateHttpSession(true));  // 세션 객체 무효화
		
		
		return http.build();
		
		
		/*
		http
	   	   .formLogin(form -> form
	       .loginPage("/login")              // 내가 만든 로그인 페이지 URL
	       .loginProcessingUrl("/loginProc") // 로그인 요청을 처리할 URL (Controller 필요 없음)
	       .usernameParameter("userId")      // 로그인 폼에서 name="userId" 인 입력 필드 사용
	       .passwordParameter("userPw")      // name="userPw"
	       .defaultSuccessUrl("/home", true) // 로그인 성공 시 이동
	       .failureUrl("/login?error=true")  // 실패 시 이동
	       .permitAll()
	       loginProcessingUrl()은 Spring Security가 직접 처리하기 때문에
	       Controller에서 /loginProc 매핑을 만들 필요가 없습니다.
	        );
		  
		*/
		
	}
	
	@Bean 
	public UserDetailsService userDetailsService() {  //사용자 정보 (원칙 DB 가지고.... in memory TEST
		
		//JDBC 연결
	    JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        // 사용자 정보 쿼리
        String sql1 = "SELECT user_id AS username, user_pw AS password, enabled FROM user WHERE user_id = ?";
        // 권한 정보 쿼리
        String sql2 = "SELECT user_id AS username, auth FROM user_auth WHERE user_id = ?";

        // 세터로 주입
        userDetailsManager.setUsersByUsernameQuery(sql1);
        userDetailsManager.setAuthoritiesByUsernameQuery(sql2);

        return userDetailsManager;
	}
	
	//UserDetailsService를 구현했으니 authenticationManager 도 구현하기
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration ) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
