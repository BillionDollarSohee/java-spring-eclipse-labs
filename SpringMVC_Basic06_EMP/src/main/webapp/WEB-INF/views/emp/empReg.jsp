<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 등록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container mt-5">
    <h2 class="mb-4 text-center">사원 등록</h2>

    <form action="empReg.do" method="post" class="border p-4 rounded shadow-sm bg-light">
        <div class="mb-3">
            <label for="empno" class="form-label">사원번호</label>
            <input type="number" class="form-control" id="empno" name="empno" required>
        </div>
        <div class="mb-3">
            <label for="ename" class="form-label">이름</label>
            <input type="text" class="form-control" id="ename" name="ename" required>
        </div>
        <div class="mb-3">
            <label for="job" class="form-label">직무</label>
            <input type="text" class="form-control" id="job" name="job">
        </div>
        <div class="mb-3">
            <label for="mgr" class="form-label">상사번호(MGR)</label>
            <input type="number" class="form-control" id="mgr" name="mgr">
        </div>
        <div class="mb-3">
            <label for="sal" class="form-label">급여</label>
            <input type="number" class="form-control" id="sal" name="sal">
        </div>
        <div class="mb-3">
            <label for="comm" class="form-label">보너스(COMM)</label>
            <input type="number" class="form-control" id="comm" name="comm">
        </div>
        <div class="mb-3">
            <label for="deptno" class="form-label">부서번호</label>
            <input type="number" class="form-control" id="deptno" name="deptno">
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-primary">등록</button>
            <a href="emp.do" class="btn btn-secondary">목록으로</a>
        </div>
    </form>
</div>

</body>
</html>
