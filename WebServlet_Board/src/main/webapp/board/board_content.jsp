<%@page import="bit.dto.Reply"%>
<%@page import="java.util.List"%>
<%@page import="bit.dto.Board"%>
<%@page import="bit.service.BoardService"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // 필수 파라미터
    String idx = request.getParameter("idx");
    if (idx == null || idx.trim().isEmpty()) {
        response.sendRedirect("board_list.jsp");
        return;
    }
    idx = idx.trim();

    // 페이징 파라미터 (기본값)
    String cpage    = request.getParameter("cp");
    String pagesize = request.getParameter("ps");
    if (cpage == null || cpage.trim().isEmpty())    cpage = "1";
    if (pagesize == null || pagesize.trim().isEmpty()) pagesize = "5";

    BoardService service = BoardService.getInBoardService();

    // 조회수 증가 (옵션)
    boolean isread = service.addReadNum(idx);

    // 게시글 조회
    Board board = service.content(Integer.parseInt(idx));
    if (board == null) {
        out.print("데이터 오류");
        out.print("<hr><a href='board_list.jsp?cp=" + cpage + "&ps=" + pagesize + "'>목록가기</a>");
        return;
    }

    // 제목 URL 인코딩 (답글 링크용)
    String subject = board.getSubject() == null ? "" : board.getSubject();
    String subjectEnc = URLEncoder.encode(subject, "UTF-8");

    // 본문 안전 출력 (간단 이스케이프 + 개행 처리)
    String content = board.getContent();
    if (content == null) content = "";
    // 최소 XSS 방지 (필요 시 Apache Commons Text 사용 권장)
    content = content.replace("&", "&amp;")
                     .replace("<", "&lt;")
                     .replace(">", "&gt;")
                     .replace("\"", "&quot;");
    content = content.replaceAll("\\r?\\n", "<br>");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>board_content</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/default.css">
  <script>
    function reply_check() {
      var frm = document.reply;
      if (!frm.reply_writer.value.trim() || !frm.reply_content.value.trim() || !frm.reply_pwd.value.trim()) {
        alert("리플 내용, 작성자, 비밀번호를 모두 입력해야 합니다.");
        return false;
      }
      frm.submit();
    }
    function reply_del(frm) {
      if (!frm.delPwd.value.trim()) {
        alert("비밀번호를 입력하세요");
        frm.delPwd.focus();
        return false;
      }
      frm.submit();
    }
  </script>
</head>
<body>
<%
    pageContext.include("/include/header.jsp");
%>
<div id="pageContainer">
  <div style="padding-top:30px; text-align:center">
    <center>
      <b>게시판 글내용</b>
      <table width="80%" border="1" cellspacing="0" cellpadding="6">
        <tr>
          <td width="20%" align="center"><b>글번호</b></td>
          <td width="30%"><%= idx %></td>
          <td width="20%" align="center"><b>작성일</b></td>
          <td><%= board.getWritedate() %></td>
        </tr>
        <tr>
          <td align="center"><b>글쓴이</b></td>
          <td><%= board.getWriter() %></td>
          <td align="center"><b>조회수</b></td>
          <td><%= board.getReadnum() %></td>
        </tr>
        <tr>
          <td align="center"><b>홈페이지</b></td>
          <td><%= board.getHomepage() %></td>
          <td align="center"><b>첨부파일</b></td>
          <td><%= board.getFilename() %></td>
        </tr>
        <tr>
          <td align="center"><b>제목</b></td>
          <td colspan="3"><%= subject %></td>
        </tr>
        <tr>
          <td align="center"><b>글내용</b></td>
          <td colspan="3"><%= content %></td>
        </tr>
        <tr>
          <td colspan="4" align="center">
            <a href="board_list.jsp?cp=<%=cpage%>&ps=<%=pagesize%>">목록가기</a> |
            <a href="board_edit.jsp?idx=<%=idx%>&cp=<%=cpage%>&ps=<%=pagesize%>">편집</a> |
            <a href="board_delete.jsp?idx=<%=idx%>&cp=<%=cpage%>&ps=<%=pagesize%>">삭제</a> |
            <a href="board_rewrite.jsp?idx=<%=idx%>&cp=<%=cpage%>&ps=<%=pagesize%>&subject=<%=subjectEnc%>">답글</a>
          </td>
        </tr>
      </table>

      <!-- 댓글 작성 -->
      <form name="reply" action="board_replyok.jsp" method="post">
        <input type="hidden" name="idx" value="<%= idx %>">
        <input type="hidden" name="userid" value="">
        <!-- 페이징 유지 -->
        <input type="hidden" name="cp" value="<%= cpage %>">
        <input type="hidden" name="ps" value="<%= pagesize %>">

        <table width="80%" border="1" cellspacing="0" cellpadding="6">
          <tr><th colspan="2">덧글 쓰기</th></tr>
          <tr>
            <td align="left">
              작성자 : <input type="text" name="reply_writer"><br>
              내&nbsp;&nbsp;용 :
              <textarea name="reply_content" rows="2" cols="50"></textarea>
            </td>
            <td align="left">
              비밀번호:
              <input type="password" name="reply_pwd" size="6">
              <input type="button" value="등록" onclick="reply_check()">
            </td>
          </tr>
        </table>
      </form>

      <br>

      <!-- 댓글 목록 -->
<%
    List<Reply> replylist = service.replyList(idx);
    if (replylist != null && !replylist.isEmpty()) {
%>
      <table width="80%" border="1" cellspacing="0" cellpadding="6">
        <tr><th colspan="2">REPLY LIST</th></tr>
<%
        for (Reply reply : replylist) {
%>
        <tr align="left">
          <td width="80%">
            [<%= reply.getWriter() %>] : <%= reply.getContent() %><br>
            작성일: <%= reply.getWritedate() %>
          </td>
          <td width="20%">
            <form action="boardreply_deleteOk.jsp" method="post">
              <input type="hidden" name="no"  value="<%= reply.getNo() %>">
              <input type="hidden" name="idx" value="<%= idx %>">
              <!-- 페이징 유지 -->
              <input type="hidden" name="cp" value="<%= cpage %>">
              <input type="hidden" name="ps" value="<%= pagesize %>">

              password: <input type="password" name="delPwd" size="6">
              <input type="button" value="삭제" onclick="reply_del(this.form)">
            </form>
          </td>
        </tr>
<%
        } // end for
%>
      </table>
<%
    } // end if
%>
    </center>
  </div>
</div>
</body>
</html>
