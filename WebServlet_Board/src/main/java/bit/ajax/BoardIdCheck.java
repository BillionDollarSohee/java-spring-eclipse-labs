package bit.ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import bit.dao.BoardDao;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 게시판 작성자(ID) 중복 체크
 *  - 요청 파라미터: writer
 *  - 응답: "true" (사용 가능) / "false" (이미 존재)
 */
@WebServlet("/BoardIdCheck")
public class BoardIdCheck extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BoardIdCheck() {
        super();
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String writer = request.getParameter("writer");

        BoardDao dao = new BoardDao();
        boolean isUsable = dao.isWriterAvailable(writer);  
        // true = 사용 가능 / false = 이미 존재
        out.print(isUsable ? "true" : "false");
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
