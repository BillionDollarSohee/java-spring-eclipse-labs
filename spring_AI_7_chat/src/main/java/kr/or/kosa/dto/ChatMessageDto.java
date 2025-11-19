package kr.or.kosa.dto;
// 사용자가 보낸 메시지인지 AI가 보낸 메세지인지 판단

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessageDto {
	private String sender;
	private String text;
}
