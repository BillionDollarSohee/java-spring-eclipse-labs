package kr.or.kosa.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;

@Controller
public class visionController {
	// ChatClient AI 객체 선언
	private final ChatClient chatClient;
	
	public visionController(ChatClient.Builder Builder) {
		this.chatClient = Builder.build(); // open AI 연결한 객체의 주소
	}
	
	@GetMapping
	public String visionPage() {
		return "vision";
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public ResponseEntity<Map<String, String>> vision(@RequestParam("file") MultipartFile file,
													  @RequestParam("message") String message){
		Map<String, String> response = new HashMap<>();
		
		try {
			String useMessage = (message == null || message.isEmpty()) ? "이미지 분석해줘" : message;
			
			//AI 요청
			  String result = chatClient.prompt()
					                    .user(userSpec -> userSpec.text(useMessage)
					                    .media(MimeType.valueOf(Objects.requireNonNull(file.getContentType())),file.getResource()))
					                    .call()
					                    .content();
			  response.put("result", result); //분석 결과
			  System.out.println("결과 result : " + response);
			
			return ResponseEntity.ok(response);
			
		} catch(MaxUploadSizeExceededException e) {
			response.put("error", "최대 10MB까지 가능합니다.");
			return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
		} catch(Exception e) {
			response.put("error", "파일 업로드 중 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
}
