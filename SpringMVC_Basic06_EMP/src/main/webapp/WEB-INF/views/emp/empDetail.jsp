<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 상세보기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-5">
    <h2 class="text-center mb-4">사원 상세정보</h2>

    <table class="table table-bordered table-hover">
        <tr><th>사원번호</th><td>${emp.empno}</td></tr>
        <tr><th>이름</th><td>${emp.ename}</td></tr>
        <tr><th>직무</th><td>${emp.job}</td></tr>
        <tr><th>상사번호</th><td>${emp.mgr}</td></tr>
        <tr><th>입사일</th><td>${emp.hiredate}</td></tr>
        <tr><th>급여</th><td>${emp.sal}</td></tr>
        <tr><th>보너스</th><td>${emp.comm}</td></tr>
        <tr><th>부서번호</th><td>${emp.deptno}</td></tr>
    </table>

    <div class="text-center mt-4">
        <a href="empEdit.do?empno=${emp.empno}" class="btn btn-warning">수정</a>
        <a href="empDel.do?empno=${emp.empno}" class="btn btn-danger">삭제</a>
        <a href="emp.do" class="btn btn-dark">목록으로</a>
    </div>
</div>

</body>
</html>
