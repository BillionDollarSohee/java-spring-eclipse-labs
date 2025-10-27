package bit.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//생성되는 모든 서비스는 Action 인터페이스를 구현하게 하겠다
public interface Action {
	ActionForward execute(HttpServletRequest request, HttpServletResponse response);
}