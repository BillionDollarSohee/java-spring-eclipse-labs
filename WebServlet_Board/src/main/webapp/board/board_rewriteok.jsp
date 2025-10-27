<%@page import="bit.service.BoardService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("UTF-8");
%>

<jsp:useBean id="board" class="bit.dto.Board" scope="request">
    <jsp:setProperty name="board" property="*"/>
</jsp:useBean>

<%
    int result = 0;
    try {
        BoardService service = BoardService.getInBoardService();
        result = service.rewriteok(board);
    } catch (Exception e) {
        result = 0; // 로깅 필요시 e.printStackTrace();
    }

    String cpage    = request.getParameter("cp");
    String pagesize = request.getParameter("ps");

    String msg, url;
    if (result > 0) {
        msg = "rewrite insert success";
        url = "board_list.jsp" 
              + (cpage != null && pagesize != null ? ("?cp=" + cpage + "&ps=" + pagesize) : "");
    } else {
        msg = "rewrite insert fail";
        url = "board_content.jsp?idx=" + board.getIdx()
              + (cpage != null && pagesize != null ? ("&cp=" + cpage + "&ps=" + pagesize) : "");
    }

    request.setAttribute("board_msg", msg);
    request.setAttribute("board_url", url);
%>
<jsp:forward page="redirect.jsp"/>