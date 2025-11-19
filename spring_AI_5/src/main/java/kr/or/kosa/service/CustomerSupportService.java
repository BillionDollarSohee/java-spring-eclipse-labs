package kr.or.kosa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;

@Service
public class CustomerSupportService {
	// ChatClient AI 객체 선언
	private final ChatClient chatClient;
	
	public CustomerSupportService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build(); // open AI 연결한 객체의 주소
	}
	
	// 챗봇과의 대화 내용을 저장하고 싶은 상황
	// String : hong [new Message()][new Message()][new Message()]...
	private final Map<String, List<Message>> chatHistory = new ConcurrentHashMap<>();
	
	// 챗봇 시스템 프롬프트 정의할 텍스트
	private final String systemPrompt = """
			당신은 "용사마트"의 전설적인 NPC 상점 직원입니다.
			항상 유쾌하고 약간 장난스러운 말투로 답변해야 합니다.
			사용자가 장비를 묻는다면, 아래 아이템 정보를 기반으로 답해주세요.

			- 아이템명: 불타는 대검
			- 가격: 500 골드
			- 특징: 공격력 +50, 확률적으로 적에게 화상 데미지를 준다
			- 재고: 충분함

			- 아이템명: 힐링 포션
			- 가격: 50 골드
			- 특징: HP 50 즉시 회복
			- 재고: 3개 남음

			- 아이템명: 투명 망토
			- 가격: 999 골드
			- 특징: 10초간 투명화 가능
			- 재고: 품절(전설의 용사가 훔쳐갔음)

			만약 목록에 없는 물건을 물으면,
			모험가에게 정중히 "그런 아이템은 존재하지 않는다네!" 라고 답해주세요.
			사용자가 고민하면 가끔은 추천도 해주고, 간단한 농담도 섞어주세요.
			""";
	
	/*
	chatHistory는 Map<String, List<Message>> 형태(보통)로,
	각 사용자(userId)의 이전 대화 목록(List<Message>)을 저장하고 있습니다.
	computeIfAbsent()는 map에 값이 없으면 자동으로 생성하는 메서드입니다.
	즉, userId의 대화 기록이 없으면 새로 ArrayList()를 만들고, 있으면 기존 기록을 꺼냄.
	*/
	public String getChatResponse(String userId, String userMessage) {
		// Map에 사용자와의 대화 기록이 존재하는지 확인하고 있으면 끌고 오고 없으면 새로 배열을 만든다. k = userId
		List<Message> history = chatHistory.computeIfAbsent(userId, k -> new ArrayList<>());
		
		// 시스템 프롬프트 메세지 + 사용자 메세지 + 기존 대화내용
		List<Message> conversion = new ArrayList<>();
		conversion.add(new SystemPromptTemplate(systemPrompt).createMessage());
		conversion.add(new UserMessage(userMessage));
		conversion.addAll(history); // 특정사용자의 대화 목록
		
		// ChatClient로 AI에게 요청하기
		// [SystemMessage, userMessage, history]
		// [[너는여행가이드야, 북해도의여행추천해줘, 얼음축제가 좋아요] 맛집도 추천해줘]
		Prompt prompt = new Prompt(conversion);
		
//		ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
		// 모델 옵션(창의성 증가)
		OpenAiChatOptions options = OpenAiChatOptions.builder()
		        .temperature(0.9)   // 높을수록 다양(0.7~1.2 범위 권장)
		        .topP(0.9)          // 확률 다양화
		        .maxTokens(200)     // 적당히 제어 가능
		        .build();

		// AI 응답 호출 시 옵션 포함
		ChatResponse response = chatClient.prompt(prompt)
		        .options(options)
		        .call()
		        .chatResponse();
		
		// 유효성 검사
		if (response == null || response.getResult() == null || response.getResult().getOutput() == null) {
			System.err.println("AI 응답이 유효하지 않습니다.");
			return "현재 AI가 네트워크 문제로 답을 할 수 없습니다.";
		}
		
		// 다시 응답을 history에 추가
		history.add(new UserMessage(userMessage));       // 유저의 질문
		history.add(response.getResult().getOutput());   // AI의 답변
				
		// 대화가 길때 최신 기록 10개만 남기기 - 사용자가 1명이라고 가정하고 단순화
		if (history.size() > 10) {
			history.subList(0, history.size()-10).clear();
		}
		
		System.out.println("[ 사용자 : " + userId + " 대화 기록 ]");
		List<Message> messages = chatHistory.get(userId);
		
		
		for (int i = 0; i < messages.size(); i+=2) {
			Message question = messages.get(i);
			System.out.println("Question : " + question.getText());
			
			if (i + 1 < messages.size()) {
				Message answer = messages.get(i+1);
				System.out.println("Answer : " + answer.getText());
			}
		}
		
		return response.getResult().getOutput().getText();
	}
	
	
}
