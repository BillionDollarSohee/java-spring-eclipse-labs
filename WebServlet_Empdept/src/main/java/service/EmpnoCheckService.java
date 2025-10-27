package service;

import action.Action;
import action.ActionForward;
import dao.EmpDao;
import dto.Emp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmpnoCheckService implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

        ActionForward forward = null;

        try {
            // 파라미터 받기
            int empno = Integer.parseInt(request.getParameter("empno"));

            // DAO 호출
            EmpDao empDao = new EmpDao();
            Emp emp = empDao.getEmpByEmpno(empno);

            // 결과값 세팅
            // emp 가 null 이면 사용 가능, null 아니면 이미 존재
            String empnoCheck = (emp == null) ? "true" : "false";

            System.out.println("empnoCheck = " + empnoCheck);
            request.setAttribute("empnoCheck", empnoCheck);

            // forward 설정
            forward = new ActionForward();
            forward.setRedirect(false);
            forward.setPath("/WEB-INF/views/empvalid.jsp"); // 비동기 응답 JSP

        } catch (Exception e) {
            e.printStackTrace();
        }

        return forward;
    }
}
