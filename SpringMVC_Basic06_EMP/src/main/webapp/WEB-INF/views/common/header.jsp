<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<nav class="navbar navbar-expand-lg navbar-light bg-light px-4">
  <div class="container-fluid">
    <a class="navbar-brand fw-bold" href="${ctx}/index.jsp">KOSA</a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
      aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link" href="${ctx}/emp/emp.do">사원 보기</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${ctx}/emp/empReg.do">사원 등록</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
