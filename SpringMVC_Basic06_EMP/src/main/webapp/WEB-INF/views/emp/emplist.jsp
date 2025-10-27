<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
/* 테이블 각 행의 높이를 고정 (예: 56px) */
.table tbody tr {
  height: 56px;
  vertical-align: middle;
}

/* 셀 내부 텍스트 줄바꿈 방지 */
.table td {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
</head>

<body class="p-4">

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-5">
    <h2 class="mb-4 text-center">사원 목록</h2>
    
    <table class="table table-striped table-hover text-center align-middle">
        <thead class="table-dark">
            <tr>
                <th>사원번호</th>
                <th>이름</th>
                <th>직무</th>
                <th>급여</th>
                <th>부서번호</th>
                <th>상세보기</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="emp" items="${list}">
                <tr>
                    <td>${emp.empno}</td>
                    <td>${emp.ename}</td>
                    <td>${emp.job}</td>
                    <td>${emp.sal}</td>
                    <td>${emp.deptno}</td>
                    <td><a href="empDetail.do?empno=${emp.empno}" class="btn btn-sm btn-outline-primary">보기</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- ✅ 페이징 영역 -->
    <div class="d-flex justify-content-center mt-4">
        <nav aria-label="Page navigation">
            <ul class="pagination">

                <!-- 이전 버튼 -->
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="emp.do?page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <!-- 현재 페이지 표시 -->
                <li class="page-item active">
                    <a class="page-link" href="#">${currentPage}</a>
                </li>

                <!-- 다음 버튼 (현재 리스트가 10개 꽉 찼을 때만 표시) -->
                <c:if test="${list.size() == 10}">
                    <li class="page-item">
                        <a class="page-link" href="emp.do?page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>

            </ul>
        </nav>
    </div>
</div>

</body>
</html>
