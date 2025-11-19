package kr.or.kosa.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponseDto {
	private List<ChatMessageDto> response;
}
