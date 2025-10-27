package com.controller;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
	
	public HelloController() {
		System.out.println("HelloController 생성됨");
	}
	
	// <a href="hrllo.do"> hello.do 요청하기 </a>
	@RequestMapping("/hello.do")
	public ModelAndView hello() {
		System.out.println("hello.do 요청에 대해서 method call");
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("greeting",getGreeting());
		mv.setViewName("Hello");
		return mv;
	}
	
	private String getGreeting() {
	    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	    String data = "";
	    if (hour >= 6 && hour <= 10) {
	        data = "학습시간";
	    } else if (hour >= 11 && hour <= 13) {
	        data = "배고픈시간";
	    } else if (hour >= 14 && hour <= 18) {
	        data = "졸려운시간";
	    } else {
	        data = "집에갈시간";
	    }
	    return data;
	}

}
