<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--  
	초급자의 실수 1순위
	enctype="multipart/form-data"
	전송: 텍스트 + 파일 
	
	그냥 multipart만 하면 텍스트만 넘어옴
	사람 운반에서 -> 자동차도 운반가능하게
	장점 : DTO로 파일을 받을 수 있다

-->

	<form method="post" enctype="multipart/form-data">
		이름:<input type="text" name="name"><br>
		나이:<input type="text" name="age"><br>
		사진:<input type="file" name="file"><br>
		<input type="submit" value="파일 업로드">
	</form>
</body>
</html>