<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 등록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<!-- 공통 네비게이션 -->
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<div class="container mt-5" style="max-width:700px;">
  <h2 class="mb-4 text-center">사원 등록</h2>

  <form action="empInsert.do" method="post" class="needs-validation" novalidate>
    <!-- 사원번호 -->
    <div class="mb-3">
      <label for="empno" class="form-label">사원번호</label>
      <input type="number" class="form-control" id="empno" name="empno" placeholder="예: 7369" required>
      <div class="invalid-feedback">사원번호를 입력하세요.</div>
    </div>

    <!-- 이름 -->
    <div class="mb-3">
      <label for="ename" class="form-label">이름</label>
      <input type="text" class="form-control" id="ename" name="ename" placeholder="예: SMITH" required>
      <div class="invalid-feedback">이름을 입력하세요.</div>
    </div>

    <!-- 직무 -->
    <div class="mb-3">
      <label for="job" class="form-label">직무</label>
      <input type="text" class="form-control" id="job" name="job" placeholder="예: CLERK" required>
      <div class="invalid-feedback">직무를 입력하세요.</div>
    </div>

    <!-- 급여 -->
    <div class="mb-3">
      <label for="sal" class="form-label">급여</label>
      <input type="number" class="form-control" id="sal" name="sal" placeholder="예: 1200" required>
      <div class="invalid-feedback">급여를 입력하세요.</div>
    </div>

    <!-- 부서번호 -->
    <div class="mb-3">
      <label for="deptno" class="form-label">부서번호</label>
      <input type="number" class="form-control" id="deptno" name="deptno" placeholder="예: 20" required>
      <div class="invalid-feedback">부서번호를 입력하세요.</div>
    </div>

    <div class="d-grid gap-2 mt-4">
      <button type="submit" class="btn btn-primary">등록</button>
      <a href="emplist.do" class="btn btn-outline-secondary">목록으로</a>
    </div>
  </form>
</div>

<script>
  // Bootstrap 기본 폼 검증
  (() => {
    'use strict';
    const forms = document.querySelectorAll('.needs-validation');
    Array.from(forms).forEach(form => {
      form.addEventListener('submit', event => {
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  })();
</script>

</body>
</html>
