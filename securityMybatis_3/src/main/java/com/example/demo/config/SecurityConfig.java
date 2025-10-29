package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.demo.controller.AdminController;
import com.example.demo.security.CustomerDetailService;

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

    private final AdminController adminController;
	
	private final DataSource dataSource; // 프로퍼티에 있는 datasource 롬복으로 주입
    private final CustomerDetailService customerDetailService; 
    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth
                					.requestMatchers("/admin/**").hasRole("ADMIN")
                					.requestMatchers("/user/**").hasAnyRole("USER","ADMIN") //ROLE_USER , ROLE_ADMIN
                					.requestMatchers("/css/**" , "/js/**" , "/images/**").permitAll()
            						.anyRequest().permitAll())
									.logout(withDefaults());        // 로그아웃 기본값 유지
									//.formLogin(withDefaults());   // 로그인 방식 기본값 유지
									
		
		http.logout(logout -> logout.logoutUrl("/logout")           // 로그아웃 요청 받을 URL
									.logoutSuccessUrl("/")          // 로그아웃 성공 후 URL
									.deleteCookies("JSESSIONID")    // 세션 쿠키 삭제
									.invalidateHttpSession(true));  // 세션 객체 무효화
		
		http.formLogin(from -> from
							  .loginPage("/login")
							  .loginProcessingUrl("/")
							  .usernameParameter("id") // username을 바꾸는 것이 아니에요
							  .passwordParameter("pw") // login.html를 확인하세요
							  .successHandler(null)    // 로그인 성공시 이메일을 보낸다던가
							  .permitAll());
		
		http.userDetailsService(customerDetailService); // mybatis 사용자 정의 인증방식
		http.exceptionHandling(exveptions -> exveptions.accessDeniedHandler(accessDeniedHandler()));
		//http.csrf(csrf -> csrf.disable()); 개발중에 사용
		return http.build();
		
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
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return null;
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
