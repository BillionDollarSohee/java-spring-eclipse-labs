<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link href="<%=request.getContextPath()%>/style/default.css" rel="stylesheet">
</head>
<body>
<div id="inputcontent">
  <div id="inputmain">
    <h3>로그인</h3>
    <form method="post" action="<%=request.getContextPath()%>/login">
      <table>
        <tr>
          <th>이메일</th>
          <td><input type="text" name="email" required></td>
        </tr>
        <tr>
          <th>비밀번호</th>
          <td><input type="password" name="pwd" required></td>
        </tr>
      </table>
      <div class="buttons">
        <input type="submit" value="로그인">
      </div>
      <div style="color:red">
        <%= request.getAttribute("error") == null ? "" : request.getAttribute("error") %>
      </div>
    </form>
  </div>
</div>
</body>
</html>