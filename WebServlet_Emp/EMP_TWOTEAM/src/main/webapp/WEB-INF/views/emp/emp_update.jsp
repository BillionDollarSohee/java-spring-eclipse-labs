<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EMP 수정</title>
</head>
<body>
  <jsp:include page="/header.jsp" />

  <div class="container" style="max-width:860px; margin:100px auto;">
    <h3 class="mb-4 fw-bold">EMP 수정</h3>

    <!-- empdetail 로 전달된 값을 이용해 수정 폼 구성 -->
    <form action="${pageContext.request.contextPath}/empupdateok.do?empno=${requestScope.empdetail.empno}" method="post">
      <!-- empno는 기본키: 읽기전용으로 노출 + 서버로 전송되도록 name 포함 -->
      <div class="mb-3">
        <label class="form-label">EMPNO</label>
        <input type="text" class="form-control" name="empno"
               value="${requestScope.empdetail.empno}" readonly required>
      </div>

      <div class="row g-3">
        <div class="col-md-6">
          <label class="form-label">ENAME</label>
          <input type="text" class="form-control" name="ename"
                 value="${requestScope.empdetail.ename}" required>
        </div>
        <div class="col-md-6">
          <label class="form-label">JOB</label>
          <input type="text" class="form-control" name="job"
                 value="${requestScope.empdetail.job}" required>
        </div>

        <div class="col-md-6">
          <label class="form-label">MGR</label>
          <input type="number" class="form-control" name="mgr"
                 value="${requestScope.empdetail.mgr}">
        </div>
        <div class="col-md-6">
          <label class="form-label">DEPTNO</label>
          <input type="number" class="form-control" name="deptno"
                 value="${requestScope.empdetail.deptno}" required>
        </div>

        <div class="col-md-6">
          <label class="form-label">SAL</label>
          <input type="number" step="1" class="form-control" name="sal"
                 value="${requestScope.empdetail.sal}" required>
        </div>
        <div class="col-md-6">
          <label class="form-label">COMM</label>
          <input type="number" step="1" class="form-control" name="comm"
                 value="${requestScope.empdetail.comm}">
        </div>

        <div class="col-md-6">
          <label class="form-label">HIREDATE</label>
          <!-- 문자열 형식이 yyyy-MM-dd 가 아닐 수 있으니 text로 두고 서버에서 파싱 -->
          <input type="text" class="form-control" name="hiredate"
                 placeholder="예) 1981-06-09"
                 value="${requestScope.empdetail.hiredate}">
        </div>
      </div>

      <div class="d-flex gap-2 mt-4">
        <button type="submit" class="btn btn-primary">수정하기</button>
        <button type="button" class="btn btn-light" onclick="location.href='emplist.do'">취소</button>
      </div>
    </form>
  </div>
</body>
</html>
