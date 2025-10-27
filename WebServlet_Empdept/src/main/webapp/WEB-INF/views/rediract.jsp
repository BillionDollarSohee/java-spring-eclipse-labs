<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty board_msg and not empty board_url}">
    <script>
        alert('${board_msg}');
        location.href='${board_url}';
    </script>
</c:if>
