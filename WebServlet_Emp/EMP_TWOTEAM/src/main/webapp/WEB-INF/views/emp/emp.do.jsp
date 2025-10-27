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
	<div style="margin: 100px;">
		<form action="${pageContext.request.contextPath}/empinsert.do"
			method="post">
			<div class="mb-3">
				<label for="formGroupExampleInput" class="form-label">empno</label> <input
					type="text" class="form-control" name="empno" placeholder="empno입력"
					required="required">
			</div>
			<div class="mb-3">
				<label for="formGroupExampleInput" class="form-label">ename</label>
				<input type="text" class="form-control" name="ename"
					placeholder="ename입력" required="required">
			</div>
			<div class="mb-3">
				<label for="formGroupExampleInput" class="form-label">job</label>
				<input type="text" class="form-control" name="job"
					placeholder="job 입력" required="required">
			</div>
			<div class="mb-3">
				<label for="formGroupExampleInput" class="form-label">mgr</label>
				<input type="text" class="form-control" name="mgr"
					placeholder="mgr 입력" required="required">
			</div>
			<div class="mb-3">
				<label for="formGroupExampleInput" class="form-label">comm</label>
				<input type="text" class="form-control" name="comm"
					placeholder="Comm 입력" required="required">
			</div>
			<div class="mb-3">
				<label for="formGroupExampleInput" class="form-label">deptno</label>
				<input type="text" class="form-control" name="deptno"
					placeholder="deptno 입력" required="required">
			</div>
			<div class="mb-3">
				<label for="formGroupExampleInput" class="form-label">sal</label>
				<input type="text" class="form-control" name="sal"
					placeholder="sal 입력" required="required">
			</div>
			<div class="col-12">
				<button type="submit" class="btn btn-primary">사원 등록</button>
			</div>
			<div class="col-12">
				<button onclick="location.href = 'emplist.do'" type="button"
					class="btn btn-light">취소</button>
			</div>
		</form>
	</div>
</body>
</html>