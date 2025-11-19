package kr.or.kosa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import kr.or.kosa.dto.ChatMessageDto;
import kr.or.kosa.dto.ChatResponseDto;
import kr.or.kosa.entity.ChatMessage;
import kr.or.kosa.repository.ChatMessageRepository;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.chat.messages.AssistantMessage;

@Service
public class CustomerSupportService {
	// ChatClient AI 객체 선언
	private final ChatClient chatClient;
	private final ChatMessageRepository chatMessageRepository;
	
	public CustomerSupportService(ChatClient.Builder chatClientBuilder,
			ChatMessageRepository chatMessageRepository) {
		this.chatClient = chatClientBuilder.build(); // open AI 연결한 객체의 주소
		this.chatMessageRepository = chatMessageRepository;
	}
	
	// 챗봇과의 대화 내용을 저장하고 싶은 상황
	// String : hong [new Message()][new Message()][new Message()]...
	private final Map<String, List<Message>> chatHistory = new ConcurrentHashMap<>();
	
	private final String systemPrompt = """
			당신은 RPG 세계 ‘용사마트’의 전설적인 만능 상점 NPC입니다.
			유쾌하고 장난스러운 말투를 사용하여 대답해야 합니다.

			당신의 상점은 **무엇이든 판매하는 마법 상점**이며,
			사용자가 어떤 아이템을 물어보든, 아래 규칙에 따라 **즉석에서 아이템 정보를 생성하여 판매할 수 있습니다.**

			[기본 아이템 예시]  
			- 아이템: 불타는 대검 / 가격 500골드 / 공격력 +50, 화상 확률  
			- 아이템: 힐링 포션 / 가격 50골드 / HP 즉시 회복  
			- 아이템: 투명 망토 / 가격 999골드 / 10초간 투명화  

			[확장 규칙 – 아무 아이템이나 즉석 생성]  
			사용자가 다음을 요청하면 자동 생성하여 안내합니다:
			- 실제로 존재하는 RPG 장비(대검, 활, 장갑, 방패 등)
			- 음식, 재료, 소비 아이템
			- 스크롤, 주문서, 마법 주문
			- 말도 안 되는 특수 아이템(예: ‘운빨 상승 반지’, ‘코딩이 잘 되는 마법 펜’ 등)
			- 현대 물건(핸드폰, 노트북)도 ‘판타지 버전’으로 재해석하여 판매 가능

			[아이템 생성 시 포함할 항목]  
			- 아이템명  
			- 가격(골드)  
			- 능력치 또는 효과  
			- 희귀도(R, SR, SSR, UR 등)  
			- 재고 수량  
			- 짧은 재미있는 lore(세계관 설명)

			[불가능한 경우]  
			- 정말 말이 안 되는 요구 사항에는 장난스럽게 응대  
			예: “전설의 월세할인쿠폰 주세요” → 장난스러운 농담 포함 가능

			[대화 스타일]  
			- 항상 유쾌, 장난스러움  
			- 안내는 쉽고 명확하게  
			- 적당히 추천/농담 포함  
			- 사용자 고민 시 친절한 조언

			""";
	// 만약 목록에 없는 물건을 물으면,
	// 모험가에게 정중히 "그런 아이템은 존재하지 않는다네!" 라고 답해주세요.
	// 사용자가 고민하면 가끔은 추천도 해주고, 간단한 농담도 섞어주세요.
	
	
	/*
	chatHistory는 Map<String, List<Message>> 형태(보통)로,
	각 사용자(userId)의 이전 대화 목록(List<Message>)을 저장하고 있습니다.
	computeIfAbsent()는 map에 값이 없으면 자동으로 생성하는 메서드입니다.
	즉, userId의 대화 기록이 없으면 새로 ArrayList()를 만들고, 있으면 기존 기록을 꺼냄.
	*/
	public ChatResponseDto getChatResponse(String userId, String userMessage) {
		
		// 사용자의 이전 대화 내용 db에서 가져오기
		List<ChatMessage> previousMessages = chatMessageRepository.findByUserIdOrderByCreateAtAsc(userId); 
		
		// 대화 이력을 stream 으로 변환
		List<Message> history = previousMessages.stream()
				.map(cm -> cm.isUser() ? new UserMessage(cm.getContent())
									   : new AssistantMessage(cm.getContent()))
				.collect(Collectors.toList());
		
		
		// 시스템 프롬프트 메세지 + 사용자 메세지 + 기존 대화내용
		List<Message> conversion = new ArrayList<>();
		conversion.add(new SystemPromptTemplate(systemPrompt).createMessage());
		conversion.addAll(history);
		conversion.add(new UserMessage(userMessage));
		
		
		Prompt prompt = new Prompt(conversion);
		
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
		
		String aiResponseMessage = response.getResult().getOutput().getText(); // ai: 당신이 요청한 그림은...
		
		// 현재 질문(true)과 답변(false) 저장
		chatMessageRepository.save(new ChatMessage(userId, userMessage, true));
		chatMessageRepository.save(new ChatMessage(userId, aiResponseMessage, false));
		
		
		// json 구조로 변경해서 메세지 리스트 생성
		List<ChatMessageDto> messages = new ArrayList<>();
		
		for (ChatMessage msg : previousMessages) {
			String sender = msg.isUser() ? "user":"ai";
			String content = msg.getContent();
			
			messages.add(new ChatMessageDto(sender, content));
		}
		
		// 현재 메세지
		messages.add(new ChatMessageDto("user", userMessage));
		messages.add(new ChatMessageDto("ai", aiResponseMessage));
		return new ChatResponseDto(messages);
		
	}
	
	
}
