<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Employee List</title>
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: 'Arial', sans-serif;
        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
        min-height: 100vh;
        padding: 20px;
    }

    .container {
        max-width: 1200px;
        margin: 0 auto;
    }

    .header {
        text-align: center;
        margin-bottom: 30px;
    }

    .header h1 {
        color: #2c3e50;
        font-size: 2.5rem;
        margin-bottom: 10px;
        font-weight: 300;
    }

    .header p {
        color: #7f8c8d;
        font-size: 1.1rem;
    }

    .table-wrapper {
        background: white;
        border-radius: 12px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        overflow: hidden;
    }

    .table-header {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        padding: 20px;
        text-align: center;
    }

    .table-title {
        color: white;
        font-size: 1.5rem;
        font-weight: 400;
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    thead tr {
        background: #f8f9fa;
    }

    th {
        padding: 15px 12px;
        text-align: center;
        font-weight: 600;
        color: #495057;
        text-transform: uppercase;
        font-size: 0.85rem;
        letter-spacing: 0.5px;
        border-bottom: 2px solid #e9ecef;
    }

    tbody tr {
        transition: all 0.2s ease;
        cursor: pointer;
    }

    tbody tr:nth-child(even) {
        background-color: #f8f9fa;
    }

    tbody tr:hover {
        background-color: #667eea;
        color: white;
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
    }

    td {
        padding: 15px 12px;
        text-align: center;
        border-bottom: 1px solid #e9ecef;
        transition: all 0.2s ease;
    }

    .emp-no {
        font-weight: 600;
        color: #667eea;
    }

    tbody tr:hover .emp-no {
        color: white;
    }

    .emp-name {
        font-weight: 500;
        text-align: left;
    }

    .job-badge {
        background: #e3f2fd;
        color: #1976d2;
        padding: 4px 12px;
        border-radius: 15px;
        font-size: 0.85rem;
        font-weight: 500;
    }

    tbody tr:hover .job-badge {
        background: rgba(255, 255, 255, 0.2);
        color: white;
    }

    .salary {
        font-weight: 600;
        color: #28a745;
    }

    tbody tr:hover .salary {
        color: #d4edda;
    }

    .date-cell {
        font-family: 'Courier New', monospace;
        font-size: 0.9rem;
        color: #6c757d;
    }

    tbody tr:hover .date-cell {
        color: rgba(255, 255, 255, 0.9);
    }



    @keyframes fadeIn {
        from {
            opacity: 0;
            transform: translateY(20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    .table-wrapper {
        animation: fadeIn 0.6s ease-out;
    }

    tbody tr {
        animation: fadeIn 0.6s ease-out;
    }

    @media (max-width: 768px) {
        .container {
            padding: 10px;
        }
        
        .header h1 {
            font-size: 2rem;
        }
        
        table {
            font-size: 0.85rem;
        }
        
        th, td {
            padding: 10px 8px;
        }
    }
</style>
</head>
<body>
    <jsp:include page="/header.jsp"/>
    
    <div class="container">
        <div class="header">
            <h1>직원 목록</h1>
            <p>전체 직원 정보를 확인하세요</p>
        </div>

        <div class="table-wrapper">
            <div class="table-header">
                <div class="table-title">Employee List</div>
            </div>


                    <table>
                        <thead>
                            <tr>
                                <th>사번</th>
                                <th>이름</th>
                                <th>직책</th>
                                <th>상사</th>
                                <th>입사일</th>
                                <th>커미션</th>
                                <th>부서번호</th>
                                <th>급여</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="emp" items="${requestScope.empList}">
                                <tr onclick="location.href='empdetail.do?empno=${emp.empno}'" 
                                    title="${emp.ename} 상세정보 보기">
                                    <td class="emp-no">${emp.empno}</td>
                                    <td class="emp-name">${emp.ename}</td>
                                    <td><span class="job-badge">${emp.job}</span></td>
                                    <td>${emp.mgr == 0 ? '-' : emp.mgr}</td>
                                    <td class="date-cell">${emp.hiredate}</td>
                                    <td>${emp.comm == 0 ? '-' : emp.comm}</td>
                                    <td>${emp.deptno}</td>
                                    <td class="salary">${emp.sal}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

        </div>
    </div>

    <script>
        // 테이블 행 애니메이션
        document.addEventListener('DOMContentLoaded', function() {
            const rows = document.querySelectorAll('tbody tr');
            rows.forEach((row, index) => {
                row.style.animationDelay = `${index * 0.1}s`;
            });
        });
    </script>
</body>
</html>