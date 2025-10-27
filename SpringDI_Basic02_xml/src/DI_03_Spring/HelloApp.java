package DI_03_Spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		
		/*
		MessageBean_en en = new MessageBean_en();
		en.sayHello("hong");
		
		MessageBean_kr kr = new MessageBean_kr();
		kr.sayHello("hong");
		*/
		
		// 인터페이스 (다형성) 부모타입 > 느슨한 구조 
		// 앞으로 무조건 인터페이스가 좋은 거라고 여기면 90%는 맞는다.
		
		//MessageBean messageBean = new MessageBean_kr();
		//messageBean.sayHello("hong"); 재정의 했으면 내려가므로
		
		//스프링 원하는 설계 방식 (인터페이스를 통한 다형성 : 느슨한 구조)
		
		//1. Spring 컨테이너 만들기
		//2. 컨테이너 안에 생성할 객체정보를 가지고 있는 xml 만들기
		//3. 필용한 객체 얻어내기
		// 앞이 제너릭이 붙으면 classpath:경로/클래스명
		// 컨테이너에서 원하는 빈을 끄집어낼때 쓰는 함수가 getbean이고 타입이 오브젝트라서 타입캐스팅을 해주었다.
		// getBean은 객체 생성 기능이 있을까 없을까? 
		// -> 원칙은 생성하지 않는데 프로토타입을 설정하면 생성하기도 한다.(바람직하지 않는다.)
		
		ApplicationContext context = 
				new  GenericXmlApplicationContext("classpath:DI_03_Spring/DI_03.xml");
		
		//interface 사용(다형성) .... 좋은 코드
		MessageBean messageBean = context.getBean("messageBean",MessageBean.class);
		messageBean.sayHello("hong");
	}

}
