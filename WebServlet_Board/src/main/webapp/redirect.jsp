<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%
    request.setCharacterEncoding("UTF-8");

    String writer = request.getParameter("writer");
    String pwd = request.getParameter("pwd");
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");
    String email = request.getParameter("email");
    String homepage = request.getParameter("homepage");
    // 파일 업로드는 commons-fileupload 같은 라이브러리를 써야 함 (여기선 생략)

    int result = 0;
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        Class.forName("oracle.jdbc.OracleDriver");
        conn = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe", "kosa", "1004");

        String sql = "INSERT INTO board(idx, writer, pwd, subject, content, email, homepage) "
                   + "VALUES(board_idx.nextval, ?, ?, ?, ?, ?, ?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, writer);
        pstmt.setString(2, pwd);
        pstmt.setString(3, subject);
        pstmt.setString(4, content);
        pstmt.setString(5, email);
        pstmt.setString(6, homepage);

        result = pstmt.executeUpdate();
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        try{ if(pstmt!=null) pstmt.close(); }catch(Exception e){}
        try{ if(conn!=null) conn.close(); }catch(Exception e){}
    }

    if(result > 0){
        request.setAttribute("board_msg", "글이 등록되었습니다.");
        request.setAttribute("board_url", "list.jsp");
    }else{
        request.setAttribute("board_msg", "등록 실패! 다시 시도하세요.");
        request.setAttribute("board_url", "write.jsp");
    }
%>

<%-- 공통 알림/이동 JSP include (또는 직접 작성) --%>
<%
	String msg = (String)request.getAttribute("board_msg");
	String url = (String)request.getAttribute("board_url");

    if(msg != null && url != null){
%>    	
	<script>
		alert('<%=msg%>');
		location.href='<%=url%>';
	</script>
<%
    }
%>
