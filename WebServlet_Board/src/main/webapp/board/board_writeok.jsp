<%@page import="bit.service.BoardService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("UTF-8");
%>

<jsp:useBean id="board" class="bit.dto.Board" scope="request">
    <jsp:setProperty name="board" property="*"/>
</jsp:useBean>

<%
    String msg, url;

    try {
        BoardService service = BoardService.getInBoardService(); 
        int result = service.writeOk(board);

        if (result > 0) {
            msg = "insert success";
            url = "board_list.jsp";
            // PRG가 더 좋지만, redirect.jsp를 쓰는 구조면 아래처럼 세팅
        } else {
            msg = "insert fail";
            url = "board_write.jsp";
        }
    } catch (Exception e) {
        // 로깅 등
        msg = "insert error: " + e.getMessage();
        url = "board_write.jsp";
    }

    request.setAttribute("board_msg", msg);
    request.setAttribute("board_url", url);
%>
<jsp:forward page="redirect.jsp"/>
