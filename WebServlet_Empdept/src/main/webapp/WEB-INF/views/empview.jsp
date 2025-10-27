<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>사원 등록</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="text/javascript">
	  $(document).ready(function(){
		  $('#checkEmpno').click(function(){
			  if($('#empno').val() == "") {
				  alert("사원번호를 입력하세요");
				  $('#empno').focus();
			  } else{
				  // 비동기 서버 요청 (사번 중복 체크)
				  $.ajax({
						url: "EmpnoCheck.emp",   // 매핑된 사번 체크 서블릿/컨트롤러 주소
						data: {empno: $('#empno').val()},
						dataType: "html",
						success: function(responseData){
							console.log(responseData); //"true" 또는 "false"
							let data = responseData.trim();
							if(data === "true"){
								alert("사용 가능한 사원번호입니다.");
								$('#ename').focus();
							}else{
								alert("이미 존재하는 사원번호입니다.");
								$('#empno').val("");
								$('#empno').focus();
							}
						}
				  });
			  }
		  });
	  });
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div style="margin-top:70px;"></div> 

<!-- 사원 등록 폼 -->
<form name="f" action="EmpAdd.emp" method="post"> 
 <div align="center"> 
    <table width="600" border="0" cellpadding="7"> 
        <tr align="center" bgcolor="gold" height="50"> 
            <td colspan="2"> 
                <font size="4" color="#0033CC" face="굴림체"> 
                <b>사원 등록</b> 
                </font> 
            </td> 
        </tr> 

        <tr> 
            <td width="25%" align="center"><b>사번</b></td> 
            <td> 
                <input type="text" size="40" name="empno" id="empno" maxlength="10"> 
                <input type="button" value="중복확인" id="checkEmpno">
            </td> 
        </tr>

        <tr> 
            <td width="25%" align="center"><b>이름</b></td> 
            <td><input type="text" size="40" id="ename" name="ename" maxlength="60"></td> 
        </tr> 

        <tr> 
            <td width="25%" align="center"><b>직무</b></td> 
            <td><input type="text" size="40" id="job" name="job" maxlength="60"></td> 
        </tr> 

        <tr> 
            <td width="25%" align="center"><b>매니저</b></td> 
            <td><input type="number" size="40" id="mgr" name="mgr" maxlength="10"></td> 
        </tr> 

        <tr> 
            <td width="25%" align="center"><b>급여</b></td> 
            <td><input type="number" size="40" id="sal" name="sal" maxlength="10"></td> 
        </tr> 

        <tr> 
            <td width="25%" align="center"><b>커미션</b></td> 
            <td><input type="number" size="40" id="comm" name="comm" maxlength="10"></td> 
        </tr> 

        <tr> 
            <td width="25%" align="center"><b>부서번호</b></td> 
            <td><input type="number" size="40" id="deptno" name="deptno" maxlength="10"></td> 
        </tr> 

        <tr bgcolor="gold"> 
            <td colspan="2" align="center" class="c2"> 
                <input type="submit" value="등록">
                <input type="reset" value="취소"> 
            </td> 
        </tr> 
    </table> 
 </div>
</form>

<hr>
<a href="EmpList.emp">사원 목록 보기</a>
</body>
</html>
