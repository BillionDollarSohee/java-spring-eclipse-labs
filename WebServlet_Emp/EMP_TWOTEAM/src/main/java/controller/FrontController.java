package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.EmpDao;
import dto.Emp;


@WebServlet("*.do") //list.do , write.do
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FrontController() {
        super();
    } 

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 한그처리
		//2. 데이터 받기
		//3. 모든 요청을 받고 처리 하겠다
		//3.1 요청에 대한 판단
		//3.1.1 command 방식 list.do?cmd=list , write.do?cmd-insert
		
		//url 방식 (뒷 주속값을 추룰해서 비교)
		//마지막 주소 문자열 : localhost:8090/WEB/list.do
		//>>>>   /list.do   /insert.do     추출 .... 
		
		
		//데이터 받기
		//URL 방식
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String urlCommand = requestUri.substring(contextPath.length());
		
		System.out.println(urlCommand);
		
		//3. 요청하기
		String viewPage= "";
		
		EmpDao empDao = new EmpDao();
		
		//요구분석 (UI 보여주 , 데이터 처리해주)
		if (urlCommand.equals("/emp.do")) {
		    // 입력 화면
		    viewPage = "/WEB-INF/views/emp/emp_insert.jsp";

		} else if (urlCommand.equals("/empinsert.do")) {
		    request.setCharacterEncoding("UTF-8");

		    int empno  = Integer.parseInt(request.getParameter("empno"));
		    String ename = request.getParameter("ename");
		    String job   = request.getParameter("job");
		    int mgr    = Integer.parseInt(request.getParameter("mgr"));
		    int comm   = Integer.parseInt(request.getParameter("comm"));
		    int deptno = Integer.parseInt(request.getParameter("deptno"));
		    int sal    = Integer.parseInt(request.getParameter("sal"));

		    
		    Date now = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String hiredateStr = sdf.format(now);

		    Emp emp = new Emp();
		    emp.setEmpno(empno);
		    emp.setEname(ename);
		    emp.setJob(job);
		    emp.setMgr(mgr);
		    emp.setHiredate(new Date()); 
		    emp.setComm(comm);
		    emp.setDeptno(deptno);
		    emp.setSal(sal);


		    empDao.insertEmp(emp);

		    request.setAttribute("empdetail", emp);

		    viewPage = "/WEB-INF/views/emp/emp_detail.jsp";
		}else if(urlCommand.equals("/empupdate.do")) {
		    //memoupdate.do?id=방가
			//수정하기 화면 (기존 데이터 출력) > select 
			int empno = Integer.parseInt(request.getParameter("empno"));
			try {
    		Emp empDetail = empDao.getEmpListById(empno);
    		System.out.println(empDetail);
    		
    		request.setAttribute("empdetail", empDetail);
			}catch(Exception e) {
				
			}
    		viewPage = "/WEB-INF/views/emp/emp_update.jsp";
    		
    		
    		
		}else if (urlCommand.equals("/empupdateok.do")) {
    		// 변경된 내용 수정 > update ….
			
		    int empno = Integer.parseInt(request.getParameter("empno"));
		    System.out.println(empno);
		    String ename = request.getParameter("ename");
		    String job = request.getParameter("job");
		    int mgr = Integer.parseInt(request.getParameter("mgr"));
		    System.out.println(mgr);
		    int sal = Integer.parseInt(request.getParameter("sal"));
		    System.out.println(sal);
		    int comm = Integer.parseInt(request.getParameter("comm"));
		    System.out.println(comm);
		    int deptno = Integer.parseInt(request.getParameter("deptno"));
		    System.out.println(deptno);
    		
		    Emp emp = new Emp(empno, ename, job, mgr, new Date(),comm,  deptno,sal);
    		empDao.updateEmp(emp);
    	
    		request.setAttribute("empdetail", emp);	
    		viewPage = "/WEB-INF/views/emp/emp_detail.jsp";
    	}
		else if(urlCommand.equals("/emplist.do")){
			List<Emp> empList;
			try {
				empList = empDao.getEmpList();
				  request.setAttribute("empList", empList);
				  viewPage="/WEB-INF/views/emp/emp_list.jsp";
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(urlCommand.equals("/empdetail.do")) {
			
			  int empno = Integer.parseInt(request.getParameter("empno"));
			  Emp emp;
			try {
				emp= empDao.getEmpListById(empno);
				 request.setAttribute("empdetail", emp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			  viewPage="/WEB-INF/views/emp/emp_detail.jsp";
			  
		}
		else if(urlCommand.equals("/empdelete.do")) {
	          int empno = Integer.parseInt(request.getParameter("empno"));

	          try {
	              int row = empDao.deleteEmp(empno);
	              if(row > 0) {
	                  // 삭제 성공 → 목록 페이지로 redirect
	                  response.sendRedirect(request.getContextPath() + "/emplist.do");
	                  return;
	              } else {
	                  // 실패 → 다시 상세보기로
	                  response.sendRedirect(request.getContextPath() + "/empdetail.do?empno=" + empno);
	                  return;
	              }
	          } catch (Exception e) {
	              e.printStackTrace();
	              response.sendRedirect(request.getContextPath() + "/memolist.do");
	              return;
	          }
	      }
		// 5. 뷰지정
    	RequestDispatcher dis = request.getRequestDispatcher(viewPage);
    	
    	// 6. 뷰 forward
    	dis.forward(request, response);
    	
    	// 7. 뷰페이지 화면 출력..
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess( request,  response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess( request,  response);
	}

}
