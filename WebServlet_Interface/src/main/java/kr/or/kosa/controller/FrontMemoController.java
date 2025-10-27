package kr.or.kosa.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.service.MemoAddService;
import kr.or.kosa.service.MemoIdCheckService;
import kr.or.kosa.service.MemoListService;

import java.io.IOException;


@WebServlet("*.memo")  //  action="list.memo"
public class FrontMemoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public FrontMemoController() {
        super();
       
    }
    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//한글처리
    	//데이터 받고
    	//판단 (command , url)  command> list.do?cmd=list or write.do?cmd=write
    	//                     url> ..../list.do > /list.do 추출 ..... 목록보기 
    	//처리
    	//request.setCharacterEncoding("UTF-8");
    	
    	String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String urlCommand = requestUri.substring(contextPath.length());

        System.out.println("urlCommand = " + urlCommand); // /list.memo , /write.memo
        
        Action action = null;
        ActionForward forward = null;
        
        
        //기준
        //화면 
        //처리 (DB 처리  + 이동)
        
        if(urlCommand.equals("/MemoAdd.memo")) { //글쓰기처리 
        	//UI + 로직
        	 action = new MemoAddService(); //다형성
        	 forward = action.execute(request, response); //execute
       
        }else if(urlCommand.equals("/MemoList.memo")) { //목록보기
        	//UI + 로직
        	 action = new MemoListService(); //다형성
        	 forward = action.execute(request, response); //execute
        
        }else if(urlCommand.equals("/MemoId.memo")) { //목록보기
        	//TODAY POINT
        	//UI + 로직
        	//비동기 동일한 서비스 ... 별도의 서비스 (별도의 서비스 권장)
        	//고민 (페이지를 만들어서 .... 데이터 만들어서)
        	//데이터 처리 (ajax > 별도의 servlet 만들어서 편하게 처리)
        	//이러면 별도의 페이지 처리가 필요
	       	 action = new MemoIdCheckService(); //다형성
	       	 forward = action.execute(request, response); //execute
	       	 
        }else if(urlCommand.equals("/MemoView.memo")) {
        	//만약 로직 처리가 필요없다
        	//UI
        	forward = new ActionForward();
        	forward.setRedirect(false);
        	forward.setPath("/WEB-INF/views/memoview.jsp");
        }
        
        if(forward != null) {
        	if(forward.isRedirect()) { //true location.href ="페이지"
        		response.sendRedirect(forward.getPath());
        	}else {
        		  //forward (데이터 처리)
        		  RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
        		  dis.forward(request, response);
        	}
        }
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
