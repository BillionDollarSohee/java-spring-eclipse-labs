package kr.or.kosa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity // 데이터를 담고(dto 역할) + 테이블을 생성하고 설정하는것 = entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userId;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private boolean isUser;
	
	@Column(name = "create_at")
	private LocalDateTime createAt;
	
	public ChatMessage(String userId, String content, boolean isUser) {
		super();
		this.userId = userId;
		this.content = content;
		this.isUser = isUser;
		this.createAt = LocalDateTime.now();
	}
}
