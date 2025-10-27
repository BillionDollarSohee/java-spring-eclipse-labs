<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bit.dto.Board, java.util.List, bit.service.BoardService, bit.utils.ThePager" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%
    BoardService service = BoardService.getInBoardService();

    int totalCount = service.totalBoardCount();
    String ps = request.getParameter("ps");
    String cp = request.getParameter("cp");
    if(ps == null || ps.trim().isEmpty()) ps = "10";   // JDK11 호환용
    if(cp == null || cp.trim().isEmpty()) cp = "1";

    int pageSize = Integer.parseInt(ps);
    int curPage  = Integer.parseInt(cp);
    int pageCount = (totalCount + pageSize - 1) / pageSize;

    List<Board> list = service.list(curPage, pageSize);

    request.setAttribute("boardList", list);
    request.setAttribute("totalCount", totalCount);
    request.setAttribute("pageSize", pageSize);
    request.setAttribute("curPage", curPage);
    request.setAttribute("pageCount", pageCount);
%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시판 목록</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .board-table th { background:#f8f9fa; }
    .board-table tr:hover { background:#f1f1f1; cursor:pointer; }
    .depth-space { display:inline-block; width:20px; }
    .pager span, .pager a { margin:0 4px; text-decoration:none; }
    .pager .current { color:#dc3545; font-weight:bold; }
  </style>
</head>
<body>
  <c:import url="/include/header.jsp" />

  <div class="container mt-4">

    <div class="d-flex justify-content-between align-items-center mb-3">
      <h3>📋 게시판 목록</h3>
      <a href="board_write.jsp" class="btn btn-primary btn-sm">✍ 글쓰기</a>
    </div>

    <!-- 페이지 사이즈 선택 -->
    <form class="mb-3" method="get">
      <div class="d-inline-flex align-items-center gap-2">
        <span>한 페이지에</span>
        <select name="ps" onchange="this.form.submit()" class="form-select form-select-sm w-auto">
          <c:forEach var="i" begin="5" end="20" step="5">
            <option value="${i}" <c:if test="${pageSize==i}">selected</c:if>>${i}건</option>
          </c:forEach>
        </select>
      </div>
    </form>

    <!-- 게시글 테이블 -->
    <table class="table table-bordered table-hover board-table text-center align-middle">
      <thead>
        <tr>
          <th style="width:8%">번호</th>
          <th>제목</th>
          <th style="width:15%">작성자</th>
          <th style="width:15%">작성일</th>
          <th style="width:8%">조회</th>
        </tr>
      </thead>
      <tbody>
        <c:if test="${empty boardList}">
          <tr><td colspan="5">등록된 글이 없습니다.</td></tr>
        </c:if>

        <c:forEach var="board" items="${boardList}">
          <tr onclick="location.href='board_content.jsp?idx=${board.idx}&cp=${curPage}&ps=${pageSize}'">
            <td>${board.idx}</td>
            <td class="text-start">
		  <!-- depth 만큼 들여쓰기 -->
		  <c:forEach var="i" begin="1" end="${board.depth}" step="1">
		    <span class="depth-space"></span>
		  </c:forEach>
		  
		  <c:if test="${board.depth>0}">
		    <img src="${pageContext.request.contextPath}/images/re.gif" alt="re">
		  </c:if>
		  
		  <c:choose>
		    <c:when test="${not empty board.subject and fn:length(board.subject) > 25}">
		      ${fn:substring(board.subject, 0, 25)}...
		    </c:when>
		    <c:otherwise>
		      ${board.subject}
		    </c:otherwise>
		  </c:choose>
		  
		  <!-- 댓글 개수 -->
		  <c:if test="${board.replyCount > 0}">
		    <span class="badge bg-secondary ms-1">${board.replyCount}</span>
		  </c:if>
		
		  <c:if test="${board.filename != null}"> 📎</c:if>
		</td>
              

            <td>${board.writer}</td>
            <td>${board.writedate}</td>
            <td>${board.readnum}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <div class="d-flex justify-content-between align-items-center">
      <div>총 게시물 수 : ${totalCount}</div>

      <!-- 간단 페이징 -->
      <div class="pager">
        <c:if test="${curPage>1}">
          <a href="board_list.jsp?cp=${curPage-1}&ps=${pageSize}">이전</a>
        </c:if>

        <c:forEach var="i" begin="1" end="${pageCount}">
          <c:choose>
            <c:when test="${curPage==i}">
              <span class="current">[${i}]</span>
            </c:when>
            <c:otherwise>
              <a href="board_list.jsp?cp=${i}&ps=${pageSize}">[${i}]</a>
            </c:otherwise>
          </c:choose>
        </c:forEach>

        <c:if test="${curPage<pageCount}">
          <a href="board_list.jsp?cp=${curPage+1}&ps=${pageSize}">다음</a>
        </c:if>
      </div>
    </div>

    <!-- ThePager 사용 -->
    <div class="text-center mt-3">
      <%
        int pagerSize = 3;
        ThePager pager = new ThePager(totalCount, curPage, pageSize, pagerSize, "board_list.jsp");
        out.print(pager.toString());
      %>
    </div>
  </div>
</body>
</html>
