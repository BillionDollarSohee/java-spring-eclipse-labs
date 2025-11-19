package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

	@Autowired
	private ChatModel chatModel;
	
	
	public void run() {
		ChatClient chatClient = ChatClient.builder(chatModel).build();
		
		String response = chatClient.prompt("스티븐 잡스의 명언을 세개 알려주").call().content();
		System.out.println("결과 : \n" + response);
		
	}
}
