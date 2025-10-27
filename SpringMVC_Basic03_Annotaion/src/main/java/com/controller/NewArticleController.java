package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.NewArticleCommand;
import com.service.ArticleService;

/*
클라이언트 요청
1. 화면주세요 (글쓰기, 로그인화면): write.do
2. 처리해주세요 (글쓰기 업무 처리, 로그인 업무 처리): writeok.do

요청주소가 : write.do
요청주소가 : writeok.do
---------------------------------------
Spring 에서는 생각^^
클라이언트 요청 판단
method 단위
GET, POST (form method="post")

** 요청의 주소가 동일하더라도 (1개의 요청 주소로) > 화면 , 처리 판다 > 전송방식(GET, POST)
http:// ...../newArticle.do
요청방식 
GET 화면 > view 
POST 서비스 처리 >> DB 연동 >> 데이터 처리 >> view
*/


@Controller
@RequestMapping("/article/newArticle.do")
public class NewArticleController {
	
	// NewArticleController는 ArticleService에 의존합니다 (=주소가 필요헤)
	
	private ArticleService articleService;
	
	@Autowired //IOC컨테이너 ArticleService 서비스 타입의 객제 존채 자동 주입
	public void setArticleService(ArticleService articleService) {
	    this.articleService = articleService;
	}
	
	@GetMapping
	public String form() {
		//public ModelAndView from() {
		//	ModelAndView mv = new MocelAndView();
		//	mv.setViewName = "hello.jsp"
		//}
		// 규칙: 함수의 return type이 String 이면 리턴값은 view 주소
		return "article/newArticleForm";
	}
	
	@PostMapping
	public String submit(NewArticleCommand command) { //Post 처리되는 함수 (로직 처리)
		System.out.println("Post 처리");
		System.out.println("Command : " + command.toString());
		this.articleService.writeArticle(command);
		
		/* 생략된 코드
		NewArticleCommand article = new NewArticleCommand();
		article.setParentId( Integer.parseInt(request.getParameter("parentId")));
		article.setTitle(request.getParameter("title"));
		article.setContent(request.getParameter("content"));
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("newArticleCommand", article);  //request.setAttribute("newArticleCommand", article)
		mv.setViewName("article/newArticleSubmitted");
		
		// 데이터 담는 작업 매개변수클래스 앞을 소문자로 바꿔서 뷰까지 보내는 것이 자동
		// mv.setViewName("article/newArticleSubmitted");
		*/
		return "article/newArticleSubmitted";
	}

}


/*
호랑이 담배 피던 시절에 했던 코드 = 가장 안정적 = 가장 전통적 => HttpServletRequest request >> spring 고민 고민 .... 
@PostMapping  //5.x.x
public ModelAndView sumbit(HttpServletRequest request) { //처리 
	System.out.println("POST 처리해주세요");
	
	NewArticleCommand article = new NewArticleCommand();
	article.setParentId( Integer.parseInt(request.getParameter("parentId")));
	article.setTitle(request.getParameter("title"));
	article.setContent(request.getParameter("content"));
	
	
	this.articleService.writeArticle(article);
	ModelAndView mv = new ModelAndView();
	mv.addObject("newArticleCommand", article);  //request.setAttribute("newArticleCommand", article)
	mv.setViewName("article/newArticleSubmitted");
	
	
	return mv;

*/

/*
2. Spring 에서 parameter DTO 객체로 받기
2.1 자동화 >> 선행조건 >> input 태그의 name값이 DTO 객체의 memberfield 명과 동일
 
@PostMapping  //5.x.x
public ModelAndView sumbit(NewArticleCommand command) { //처리 
	System.out.println("POST 처리해주세요");
	
	//1. 자동 DTO 객체 생성 : NewArticleCommand command = new NewArticleCommand();
	//2. 넘어온 parameter  setter 함수를 통해서 자동 주입  => article.setParentId 자동
	//3. NewArticleCommand command 객체 자동 IOC 컨테이너 안에 등록 : id=newArticleCommand
	//   <bean id="newArticleCommand"  class="....
	
	this.articleService.writeArticle(command);
	ModelAndView mv = new ModelAndView();
	mv.addObject("newArticleCommand", command);  //request.setAttribute("newArticleCommand", article)
	mv.setViewName("article/newArticleSubmitted");
	
	
	return mv;


*/


/*

1. 전통적인 방법
public ModelAndView searchExternal(HttpServletRequest request) {
   String id= request.getParameter("id")
} 
 
2. DTO 객체를 통한 전달 방법(게시판, 회원가입 데이터) ^^
 public ModelAndView searchExternal(MemberDto member){
 public String searchExternal(MemberDto member){
     
     return String view 주소 전달
     return String view (데이터 출력) > 페이지 > forward > memberDto 객체 만들어 > 자도 forward
     
     /search/external.do?id=hong&name=김유신&age=100
     2.1 DTO 있는 member field 이름이 >>
     private String id;
     private String name;
      
 }
 
 약속 : return String .. Model 만들면 데이터 전달
       return String  .. 화면 UI
 
 
 
 3. 가장 만만한 방법 
  public ModelAndView searchExternal(String id, String name , int age){
      /search/external.do?id=hong&name=김유신&age=100
      ** 각각의 parameter 에 자동 매핑
  }
  
 4. @RequestParam  annotation 사용하기
  4.1 유효성 처리
  4.2 기본값 처리 
 
  
 5.REST 방식 (비동기 처리) method= GET , POST , PUT , DELETE
  @PathVariable >>  /member/{memberid} >>  /member/100
  
  100 추출해서 parameter  사용
*/