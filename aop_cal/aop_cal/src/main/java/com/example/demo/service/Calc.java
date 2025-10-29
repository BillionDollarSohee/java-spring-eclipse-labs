package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component
public class Calc {
	
	public int Add(int x, int y) {
		
		return x + y;
	}
	
	public int Sub(int x, int y) {
        return x - y;
    }
	
	public int Mul(int x, int y) {
		
		return x * y;
	}
	
	public int Div(int x, int y) {
        // 예외도 AOP에서 잡아 로그 남기도록 일부러 나눗셈 그대로 둠
        return x / y;
    }
}
