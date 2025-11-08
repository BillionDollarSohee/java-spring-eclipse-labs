package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.contants.SecurityConstants;
import com.example.demo.domain.AuthenticationRequest;
import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
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
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserMapper userMapper;
	
	
	// 로그인 시 JWT 발급
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
		
		String username = request.getUsername();
		String password = request.getPassword();
		
		log.info("username = {}", username);
		log.info("password = {}", password);
		
		//DB연결 ..select 확인 .. 권한 정보 ... 했다고 하고
		/////////////////////////////////////////////////////////////////////////////////////////
		//회원가입 완료 ....
		//회원가입된 회원이 로그인 시도
		
		//사용자정의 mybatis  public UserDetails loadUserByUsername(String username) 사용
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		//userDetails 사용자 정보
		if(userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
		//사용자 인증 실패
		return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
		//////////////////////////////////////////////////////////////////////////////////////////
		//회원이구나 
		//인증서 발급 (인증서 우리사이트 에서 놀아 : ) JWT (header , payload (권한) ,...)
		
		//payload 구성하기 위한 정보 ....userDetails
		List<String> roles = new ArrayList<>();
		
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		
		//한명의 사용자가 여러개의 권한을 가질 수 있다  (admin > ROLE_USER , ROLE_ADMIN
		for(GrantedAuthority authority : authorities) {
		//권한명을 추가
		roles.add(authority.getAuthority());
		}
		
		/*
		JWT 토큰 : payload > 사용자ID, 권한정보 , 만료타입 
		
		회원가입 (ROLE 이 정해지지 않아요)
		1. 회원가입  : user (insert) , role(insert) @Transactional 
		2. 회원가입  : user (insert) , role (Trigger)
		
		오라클 트리거
		create or replace trigger users_roll
		after insert on users
		for each row
		
		begin
		INSERT INTO roll VALUES(:new.username,'ROLE_USER');
		if :new.userid = 'admin' then
		INSERT INTO roll VALUES(:new.userid,'ROLE_ADMIN');
		end if;
		
		end;  
		 */
	
		
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
	

	@GetMapping("/admin/info")
	public ResponseEntity<String> adminUserInfo() {
		return new ResponseEntity<String>("관리자님 방가", HttpStatus.OK);
	}
	
	
	// /register   회원가입   POSTMAN 테스트
	@PostMapping("/register")
	@Transactional
	public ResponseEntity<String> register(@RequestBody AuthenticationRequest request){

	    String username = request.getUsername();
	    String rawpassword = request.getPassword();
	    
	    // 중복 체크
	    if (userMapper.findByUsername(username) != null) {
	    	return new ResponseEntity<String>("User already exists", HttpStatus.BAD_REQUEST);
	    }
	    
	    // 가입
	    String encodingPassword = passwordEncoder.encode(rawpassword);
	    
	    // DB저장
	    User user = new User();
	    user.setUsername(username);
	    user.setPassword(encodingPassword);
	    user.setRole("ROLE_USER");
	    
	    userMapper.saveUser(user);
	    
	    return new ResponseEntity<String>("User registered successful", HttpStatus.CREATED);
	    
	}

}
