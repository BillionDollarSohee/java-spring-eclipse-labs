package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

	/*
	1. 전통적인 방법 - HTTP서블렛리퀘스트 프레임워크에 종속적이지 않다.
	2. DTO - Insert, Update 할때쓰임
	3. 각각의 파라미터 - ?list.do?id=7902 -> search(String id) - select, delete 주로 쓰임
	4. @RequestParam - default값이 있어서 파라미터 값을 강제하고 싶거나 이름을 누락할 경우를 대비해서 쓰임
	5. REST방식 (비동기 처리) CRUD
	@PathVariable - URL 경로에서 값을 얻는 것
	 */
	
	
	/*
	JSP를 보면 아래와 같음
	<a href="search/external.do">external.do</a><br>
    <a href="search/external.do?p">external.do</a><br>
    <a href="search/external.do?query=world">external.do</a><br>
    <a href="search/external.do?p=555">external.do</a><br>
	 */
	
	// == public ModelAndView searchExternal(String query, int p) {}
	@RequestMapping("/search/external.do")
	public ModelAndView searchExternal(@RequestParam(value = "query", defaultValue = "kosa") String query,
			    					   @RequestParam(value = "q", defaultValue = "10") int p) {
		
		System.out.println("param query : " + query);
		System.out.println("param q     : " + p);
	
		return new ModelAndView("search/external"); // view 이름
		// /WEB-INF/views/ + search/external + .jsp

	}
}
