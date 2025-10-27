package service;

import java.sql.SQLException;
import java.util.List;

import action.Action;
import action.ActionForward;
import dao.EmpDao;
import dto.Emp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmpListService implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward forward = null;
		EmpDao dao = new EmpDao();
		
		try {
			List<Emp> empList = dao.getEmpAllList();
			
			request.setAttribute("empList", empList);
			
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/views/emplist.jsp");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return forward;
	}

}
