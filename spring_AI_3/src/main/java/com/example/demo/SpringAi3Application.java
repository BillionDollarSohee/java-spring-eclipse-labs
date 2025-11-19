package com.example.demo;

import com.example.demo.service.OllamaChatService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringAi3Application {

	 public static void main(String[] args) {
	        ConfigurableApplicationContext context = SpringApplication.run(SpringAi3Application.class, args);

	        OllamaChatService ollamaChatService = context.getBean(OllamaChatService.class);
	        String response = ollamaChatService.getQuestion();
	        System.out.println("결과 : \n " + response);
	    }

}
