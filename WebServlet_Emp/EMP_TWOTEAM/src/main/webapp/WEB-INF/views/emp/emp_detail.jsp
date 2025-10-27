<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/header.jsp" />

	<div class="card" style="margin:10rem;">
		<div class="card-body">
			empno <h5 class="card-title">${requestScope.empdetail.empno}</h5>
			<hr>
			ename <p class="card-text">${requestScope.empdetail.ename}</p>
			<hr>
			job <p class="card-text"> ${requestScope.empdetail.job}</p>
			<hr>
			mgr <p class="card-text">${requestScope.empdetail.mgr}</p>
			<hr>
			comm <p class="card-text">${requestScope.empdetail.comm}</p>
			<hr>
			deptno <p class="card-text">${requestScope.empdetail.deptno}</p>
			<hr>
			sal <p class="card-text">${requestScope.empdetail.sal}</p>
			<hr>
			date <p class="card-text">${requestScope.empdetail.hiredate}</p>
			<a class="btn btn-primary" href="empdelete.do?empno=${requestScope.empdetail.empno}">삭제하기</a>
		<a class="btn btn-primary" href="empupdate.do?empno=${requestScope.empdetail.empno}">수정하기</a>
		</div>
	</div>
</body>
</html>