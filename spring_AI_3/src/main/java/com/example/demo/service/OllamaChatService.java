package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OllamaChatService {
	private final ChatClient chatClient;
	
	public OllamaChatService(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}
	
	public String getQuestion() {
		return chatClient.prompt("예수가 실존한다는 증거를 알려줘").call().content();
	}
	
}
