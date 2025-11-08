package com.example.demo.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.contants.SecurityConstants;
import com.example.demo.prop.JwtProps;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/*

 ******************************************************************
 *JwtAuthenticationFilter Token  발급된 사용자를 사이트 접속시 .... 
 *비밀번호 확인 필요없어요 ... Token 자체 시그니쳐 확인 되면 통과 
 *인증되어서 토큰을 가지고 있고 ... 내 사이트 특정 주소를 요청을 보내면 ... 검증 
 ******************************************************************
 
 
 1. 이 필터는 OncePerRequestFilter를 상속하므로 HTTP 요청마다 한 번만 실행.
 2. Authorization: Bearer <JWT> 형식의 헤더에서 JWT를 추출하고 검증.
 3. 유효한 JWT가 있는 경우 → 사용자 정보 조회 + 인증 처리
 4. 예외 URL (/login , /register) 필터 적용 제외
 
 
 처리)
 1. UserDetailsService 서비스 사용
*/

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired 
	private UserDetailsService userDetailsService;  // CustomerUserDetailsService

	@Autowired
	private JwtProps jwtProps; // 비밀키 가져오기
	
	private static final List<String> EXCLUDE_URLS = List.of("/login", "/register");

	private boolean shouldExclude(String requestURI) {
	    return EXCLUDE_URLS.stream().anyMatch(requestURI::startsWith);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// react axios.Get("localhost:8090/admin")
		// Header: { Authorization: `Bearer ${localStorage.getItem('token')}` }
		
		String authorizationHeader = request.getHeader("Authorization");
		String requestURI = request.getRequestURI();
		
		if (shouldExclude(requestURI)) {
			filterChain.doFilter(request, response);
			return; //필터 적용하지 않아야 하므로 통과
		}
		
		try {
			String secretkey = jwtProps.getSecretkey(); // 서버가 가지고 있는 비밀키
			byte[] signingkey = jwtProps.getSecretkey().getBytes(); //실제 토큰에 들어간 바이트 값
			String jwt = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "");
			// Bearer 제거

			// 토큰 해석
			Jws<Claims> parsedToken = Jwts.parser()
			    .verifyWith(Keys.hmacShaKeyFor(signingkey))
			    .build()
			    .parseSignedClaims(jwt);

			log.info("doFilterInternal : *************** " );

			// username 변수 미정의 → 아래 한 줄 추가
			String username = parsedToken.getPayload().getSubject();

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
