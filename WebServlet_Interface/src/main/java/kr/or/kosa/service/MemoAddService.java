package kr.or.kosa.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.MemoDao;
import kr.or.kosa.dto.Memo;

public class MemoAddService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		    
			String id = request.getParameter("id");
	        String email = request.getParameter("email");
	        String content = request.getParameter("content");

	        Memo memo = new Memo(id, email, content);
	        MemoDao memoDao = new MemoDao();
	        
	        int result = memoDao.insertMemo(memo);

	        //규칙 (성공 , 실패하던 msg 작성하고 이동하는 페이지 생성)
	        String msg = "";
	        String url = "";
	        
	        if(result > 0) {
	        	msg = "등록성공";
	        	url = "MemoList.memo"; //요청 서블릿 주소
	        }else {
	        	msg = "등록실패";
	        	url = "memo.html";
	        }
	        
	        
	        request.setAttribute("board_msg", msg);
	        request.setAttribute("board_url", url);
	        
	       
	        ActionForward forward = new ActionForward();
	        forward.setRedirect(false);
	        forward.setPath("/WEB-INF/views/redirect.jsp");
		
	        return forward;
	}

}
