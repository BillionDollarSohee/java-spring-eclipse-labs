<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 수정</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">
<jsp:include page="../common/header.jsp"/>

<div class="container mt-5">
    <h2 class="text-center mb-4">사원 정보 수정</h2>

    <form action="empEdit.do" method="post" class="p-4 border rounded bg-light shadow-sm">
        <div class="mb-3">
            <label class="form-label">사원번호</label>
            <input type="text" name="empno" class="form-control" value="${emp.empno}" readonly>
        </div>

        <div class="mb-3">
            <label class="form-label">이름</label>
            <input type="text" name="ename" class="form-control" value="${emp.ename}">
        </div>

        <div class="mb-3">
            <label class="form-label">직무</label>
            <input type="text" name="job" class="form-control" value="${emp.job}">
        </div>

        <div class="mb-3">
            <label class="form-label">상사번호</label>
            <input type="number" name="mgr" class="form-control" value="${emp.mgr}">
        </div>

        <div class="mb-3">
            <label class="form-label">급여</label>
            <input type="number" name="sal" class="form-control" value="${emp.sal}">
        </div>

        <div class="mb-3">
            <label class="form-label">보너스</label>
            <input type="number" name="comm" class="form-control" value="${emp.comm}">
        </div>

        <div class="mb-3">
            <label class="form-label">부서번호</label>
            <input type="number" name="deptno" class="form-control" value="${emp.deptno}">
        </div>

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-success">수정 완료</button>
            <a href="empDetail.do?empno=${emp.empno}" class="btn btn-secondary">취소</a>
        </div>
    </form>
</div>

</body>
</html>
