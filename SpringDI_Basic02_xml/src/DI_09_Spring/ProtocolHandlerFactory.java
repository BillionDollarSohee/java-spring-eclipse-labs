package DI_09_Spring;

import java.util.Map;

public class ProtocolHandlerFactory {
	public ProtocolHandlerFactory() {
		System.out.println("ProtocolHandlerFactory 기본 생성자 호출");
	}
	
	// POINT 핸들러들이 setter로 주입받아서 주소가 담길 수 있다.
	private Map<String, ProtocolHandler> handlers;  //JAVA API 제공하는 객체 

	public void setHandlers(Map<String, ProtocolHandler> handlers) {
		this.handlers = handlers;
		System.out.println("setHandlers 주입 성공" + this.handlers);
	}
	
	
}
