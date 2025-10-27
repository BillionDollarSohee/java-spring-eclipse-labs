<%@page import="bit.dto.Board"%>
<%@page import="bit.service.BoardService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // 필수 파라미터
    String idx = request.getParameter("idx");
    if (idx == null || idx.trim().isEmpty()) {
        response.sendRedirect("board_list.jsp");
        return;
    }

    // 페이징 파라미터
    String cp = request.getParameter("cp");
    String ps = request.getParameter("ps");

    BoardService service = BoardService.getInBoardService();
    Board board = service.board_EditContent(idx);
    if (board == null) {
        out.println("데이터 오류");
        out.println("<hr><a href='board_list.jsp'>목록가기</a>");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시글 수정</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/default.css">
  <script>
    function editCheck() {
      const f = document.edit;
      if (!f.writer.value.trim()) { alert("이름을 입력하세요"); f.writer.focus(); return false; }
      if (!f.pwd.value.trim())    { alert("비밀번호를 입력해야 합니다."); f.pwd.focus(); return false; }
      if (!f.email.value.trim())  { alert("이메일을 입력해야 합니다."); f.email.focus(); return false; }
      if (!f.subject.value.trim()){ alert("제목을 입력하세요"); f.subject.focus(); return false; }
      if (!f.content.value.trim()){ alert("글 내용을 입력하세요"); f.content.focus(); return false; }
      f.submit();
    }
  </script>
</head>
<body>
  <%
    // header include
    RequestDispatcher rd = request.getRequestDispatcher("/include/header.jsp");
    rd.include(request, response);
  %>

  <div id="pageContainer">
    <div style="padding-top:25px; text-align:center;">
      <!-- 파일 업로드를 하려면 multipart/form-data + 서블릿 @MultipartConfig 필요 -->
      <form name="edit" action="board_editok.jsp" method="post">
        <input type="hidden" name="idx" value="<%= idx %>">
        <% if (cp != null) { %><input type="hidden" name="cp" value="<%= cp %>"><% } %>
        <% if (ps != null) { %><input type="hidden" name="ps" value="<%= ps %>"><% } %>

        <center>
          <table width="90%" border="1" cellspacing="0" cellpadding="6">
            <tr>
              <td width="20%" align="center"><b>글번호</b></td>
              <td width="30%"><%= idx %></td>
              <td width="20%" align="center"><b>작성일</b></td>
              <td><%= board.getWritedate() %></td>
            </tr>
            <tr>
              <td align="center"><b>글쓴이</b></td>
              <td>
                <input type="text" name="writer" value="<%= board.getWriter() != null ? board.getWriter() : "" %>" required>
              </td>
              <td align="center"><b>홈페이지</b></td>
              <td>
                <input type="url" name="homepage" value="<%= board.getHomepage() != null ? board.getHomepage() : "" %>">
              </td>
            </tr>
            <tr>
              <td align="center"><b>비밀번호(기존)</b></td>
              <td>
                <input type="password" name="pwd" required>
              </td>
              <td align="center"><b>이메일</b></td>
              <td>
                <input type="email" name="email" value="<%= board.getEmail() != null ? board.getEmail() : "" %>" required>
              </td>
            </tr>
            <tr>
              <td align="center"><b>제목</b></td>
              <td colspan="3">
                <input type="text" name="subject" value="<%= board.getSubject() != null ? board.getSubject() : "" %>" size="40" required>
              </td>
            </tr>
            <tr>
              <td align="center"><b>글내용</b></td>
              <td colspan="3">
                <textarea rows="7" cols="50" name="content" required><%= board.getContent() != null ? board.getContent() : "" %></textarea>
              </td>
            </tr>
            <tr>
              <td align="center"><b>첨부파일</b></td>
              <td colspan="3">
                <%= board.getFilename() != null ? board.getFilename() : "" %>
                (<%= board.getFilesize() %> bytes)<br>
                <input type="file" name="filename">
                <!-- 주의: 이 폼은 multipart가 아니므로 새 파일 업로드는 서버 로직 추가 필요 -->
              </td>
            </tr>
            <tr>
              <td colspan="4" align="center">
                <input type="button" value="수정하기" onclick="editCheck();">
                <input type="reset"  value="다시쓰기">
              </td>
            </tr>
            <tr>
              <td colspan="4" align="center">
                <a href="board_list.jsp<%= (cp!=null&&ps!=null) ? ("?cp="+cp+"&ps="+ps) : "" %>">목록가기</a>
              </td>
            </tr>
          </table>
        </center>
      </form>
    </div>
  </div>
</body>
</html>
