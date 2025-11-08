package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// POSTMAM 테스트는 같은 도메인으로 봐서 CORS 문제 발생 X
// react, vue처럼 프론트서버에서 접속하면 CORS 문제 발생 O
// 프론트 서버의 주소를 넣어놓음으로 허용
@Configuration
public class WebConfig {

	 @Bean
	  public WebMvcConfigurer corsConfigurer() {
	      return new WebMvcConfigurer() {
	          @Override
	          public void addCorsMappings(CorsRegistry registry) {
	              registry.addMapping("/**") // 모든 경로에 대해 CORS 적용
	                      .allowedOrigins("http://localhost:5173") // React 개발 서버 주소
	                      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                      .allowedHeaders("*") // 모든 헤더 허용
	                      .exposedHeaders("Authorization") // 클라이언트가 읽을 수 있게 함
	                      .allowCredentials(true); // 쿠키/헤더 인증정보 허용
	          }
	      };
	  }
}
