package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import action.Action;
import action.ActionForward;
import dao.EmpDao;
import dto.Emp;

public class EmpAddService implements Action {
	
	@Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        
        // ✅ form에서 넘어오는 값 받기
        int empno = Integer.parseInt(request.getParameter("empno"));
        String ename = request.getParameter("ename");
        String job = request.getParameter("job");
        int mgr = Integer.parseInt(request.getParameter("mgr"));
        int sal = Integer.parseInt(request.getParameter("sal"));
        int comm = Integer.parseInt(request.getParameter("comm"));
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        
        // hiredate는 현재시간(LocalDateTime.now)으로 기본 설정
        Emp emp = new Emp(empno, ename, job, mgr, LocalDateTime.now(), sal, comm, deptno);
        EmpDao empDao = new EmpDao();
        
        int result = empDao.insertEmp(emp);

        // ✅ 등록 성공/실패 메시지와 이동 경로 설정
        String msg;
        String url;
        
        if(result > 0) {
            msg = "사원 등록 성공";
            url = "EmpList.emp";   // 사원 목록으로 이동
        } else {
            msg = "사원 등록 실패";
            url = "emp_add.jsp";   // 다시 입력 페이지로 이동
        }
        
        // JSP에서 사용할 속성 등록
        request.setAttribute("board_msg", msg);
        request.setAttribute("board_url", url);
        
        // forward 방식으로 redirect.jsp 이동
        ActionForward forward = new ActionForward();
        forward.setRedirect(false);
        forward.setPath("/WEB-INF/views/redirect.jsp");
    
        return forward;
    }

}
