package com.example.demo.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties("com.example.demo") // 프로퍼티 파일에 접근
public class JwtProps {
	
	private String Secretkey; //롬복의 getter로 불러오기
}
