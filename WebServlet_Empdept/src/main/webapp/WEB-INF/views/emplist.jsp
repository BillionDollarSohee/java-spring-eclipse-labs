<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>
<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

th {
    border: 1px solid #dddddd;
    text-align: center;
    padding: 8px;
}

td {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}

tr:hover {
    background-color: #b2d8d8;
    cursor: pointer;
}
</style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
    <div align="center">
        <hr color="green" width="400">
        <h2>사원 목록</h2>
        <hr color="green" width="400">

        <table style="width: 90%">
            <tr>
                <th>사번</th>
                <th>이름</th>
                <th>직무</th>
                <th>매니저</th>
                <th>입사일</th>
                <th>급여</th>
                <th>커미션</th>
                <th>부서번호</th>
            </tr>
            <!-- ✅ EL & JSTL 로 데이터 출력 -->
            <c:forEach var="emp" items="${requestScope.empList}">
                <tr onclick="location.href='empdetail.emp?empno=${emp.empno}'">
                    <td>${emp.empno}</td>
                    <td>${emp.ename}</td>
                    <td>${emp.job}</td>
                    <td>${emp.mgr}</td>
                    <td>${emp.hiredate}</td>
                    <td>${emp.sal}</td>
                    <td>${emp.comm}</td>
                    <td>${emp.deptno}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
