package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmpAddService;
import service.EmpnoCheckService;
import service.EmpListService;
import action.Action;
import action.ActionForward;

import java.io.IOException;

@WebServlet("*.emp")
public class EmpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmpController() { super(); }

    private void doProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String urlCommand = requestUri.substring(contextPath.length());

        System.out.println("urlCommand = " + urlCommand);

        Action action = null;
        ActionForward forward = null;

        if (urlCommand.equals("/EmpAdd.emp")) { 
            action = new EmpAddService();
            forward = action.execute(request, response);

        } else if (urlCommand.equals("/EmpList.emp")) {   
            action = new EmpListService();
            forward = action.execute(request, response);

        } else if (urlCommand.equals("/EmpnoCheck.emp")) {  
            action = new EmpnoCheckService();
            forward = action.execute(request, response);

        } else if (urlCommand.equals("/EmpView.emp")) {
            forward = new ActionForward();
            forward.setRedirect(false);
            forward.setPath("/WEB-INF/views/empview.jsp");
        }

        if (forward != null) {
            if (forward.isRedirect()) {
                response.sendRedirect(forward.getPath());
            } else {
                RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
                dis.forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doProcess(request, response);
    }
}
