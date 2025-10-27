package com.service;

import org.springframework.stereotype.Service;

import com.model.NewArticleCommand;
/*
@Service
public class ArticleService {
	
@Service 붙어있는 클래스는  컴포넌트 스캔이 자동으로 빈 객체 생성
<context:component-scan base-package="com.service">
*/

public class ArticleService {
	
	public ArticleService() {
		System.out.println("ArticleService 생성됨");
	}
	
	public void writeArticle(NewArticleCommand command) {
		// DAO가 있다고 가정
		// Dao dao = new Dao(); dao.insert(command);
		
		System.out.print("글쓰기 작업 완료" + command.toString());
	}
}
