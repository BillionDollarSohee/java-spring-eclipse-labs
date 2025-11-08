package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.contants.SecurityConstants;
import com.example.demo.domain.AuthenticationRequest;
import com.example.demo.prop.JwtProps;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {
	
	@Autowired
	private JwtProps jwtProps;
	
	// 로그인 시 JWT 발급
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
		
		String username = request.getUsername();
		String password = request.getPassword();
		
		log.info("username = {}", username);
		log.info("password = {}", password);
		
		// (예시) DB 인증 후 권한 부여했다고 가정
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN");
		
		// 토큰 생성
		String secretKey = jwtProps.getSecretkey();
		byte[] signingKey = secretKey.getBytes();

		String jwt = Jwts.builder()
		        .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512)
		        .header().add("typ", SecurityConstants.TOKEN_TYPE).and()
		        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간
		        .claim("uid", username)
		        .claim("rol", roles)
		        .compact();
		
		return new ResponseEntity<>(jwt, HttpStatus.OK);
	}
	
	// JWT 파싱 테스트 (GET)
	@GetMapping("user/info")
	public ResponseEntity<?> userInfo(@RequestHeader(name="Authorization") String header) {
		
		// Header 예: Bearer eyJhbGciOiJIUzUxMiJ9...
		log.info("Authorization header = {}", header);
		
		String secretKey = jwtProps.getSecretkey();
		byte[] signingKey = secretKey.getBytes();
		
		// Bearer 제거
		String jwt = header.replace(SecurityConstants.TOKEN_PREFIX, "").trim();
		
		// 토큰 검증 및 파싱
		Jws<Claims> parsedToken = Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(signingKey))
				.build()
				.parseSignedClaims(jwt);
		
		Claims claims = parsedToken.getPayload();
		String username = claims.get("uid", String.class);
		Object roles = claims.get("rol");
		
		log.info("username from token = {}", username);
		log.info("roles from token = {}", roles);
		
		return new ResponseEntity<>(claims, HttpStatus.OK);
	}
}
