package com.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CookieController {
	
	@RequestMapping("/cookie/make.do")
	public String make(HttpServletResponse response) {
		
		response.addCookie(new Cookie("SpringAuth", "1004")); // 클라이언트 브라우저 write
		
		return "cookie/CookieMake"; // 허전하니까 그냥 view 페이지 보여주기. String이니까 뷰페이지 주소로 자동으로 인식
	}
	
	//클라이언트의 쿠키를 가져오고 싶다면
	// 방법 1 . 전통적인 방법
	//public String view(HttpServletRequest request) {}

	// 방법 2. 애너테이션. 만약 쿠키가 없다면을 대비한 디폴트 값으로 대체할게
	@RequestMapping("/cookie/view.do")
	public String view(@CookieValue(value = "SpringAuth", defaultValue = "1007") String auth) {
		System.out.println("Client 브라우저에서 read한 Cookie 값 : " + auth);
		return "cookie/CookieMake";
	}


	
}
