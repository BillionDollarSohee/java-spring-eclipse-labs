package bit.controller;

import bit.dao.UserDao;
import bit.dto.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import javax.naming.NamingException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");

        try {
            UserDao userDao = new UserDao();
            User loginUser = userDao.login(email, pwd);

            if (loginUser != null) { 
                // 로그인 성공 → 세션 저장
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", loginUser);

                // 게시판 목록으로 리다이렉트
                response.sendRedirect(request.getContextPath() + "/board/board_list.jsp");
            } else {
                // 로그인 실패 → 메시지 띄우고 다시 로그인 페이지
                request.setAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }
}
