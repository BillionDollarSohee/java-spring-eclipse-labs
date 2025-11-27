package kr.or.kosa.tool;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;

public class SpringTools {
	
	// 의존 없이 순수한 자바 클래스로 시스템 날짜
	@Tool
	String getCuttentDateTime() {
		return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
	}
	
	// DB 흉내, 사원들의 취미 모음
	private static final Map<String, String> dictionary =
			new HashMap<String, String>();
	
	static {
		dictionary.put("김소희", "코딩");
		dictionary.put("이왕수", "운동");
		dictionary.put("김민수", "농구");
		dictionary.put("이지은", "독서");
		dictionary.put("박준형", "축구");
		dictionary.put("최유리", "영화 감상");
	}
	
	@Tool
	 public String UserHobby(String name) {
		 return dictionary.getOrDefault(name, "해당 직원 없습니다.");
	 }

}
