package kr.or.kosa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.or.kosa.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
	
	List<ChatMessage> findByUserIdOrderByCreateAtAsc(String userId);
	
	
	
	
	/*
	jpa가 기본적인 crud 메소드를 제공한다.
	그외에는 쿼리메소드를 작성하여 이름을 토대로 SQL구문이 만들어진다.
	그것으로도 부족하면 쿼리 애너테이션에 네이티브한 쿼리를 직접 적어주자.
	
    find
	데이터를 조회한다는 의미
	select * from ... 와 동일 의미
	
	2) By
	조건절(WHERE)을 시작하는 키워드
	
	3) UserId
	WHERE 절의 컬럼 이름
	→ where user_id = ?
	
	4) OrderBy
	ORDER BY 절 시작
	
	5) CreatedAt
	정렬 기준 컬럼
	
	6) Asc
	오름차순 정렬
	(Asc 또는 Desc) 

 */
}
