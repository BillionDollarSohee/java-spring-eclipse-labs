<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="text/javascript">
	  $(document).ready(function(){
		  $('#message').click(function(){
			  if($('#id').val() == "") {
				  alert("ID 입력");
				  $('#id').focus();
			  } else{
				  //비동기 서버 요청
				 /*
				 FrontController > Service > uservalid.jsp > 
				  $.ajax(
						{
							url:"MemoId.memo",
							data:{id:$('#id').val()},
							dataType:"html",
							success:function(responseData){
								console.log(responseData); //"true" 또는 "false"
								let data = responseData.trim();
								if(data == "true"){
									alert("사용가능");
									$('#email').focus();
								}else{
									alert("중복ID");
									$('#id').val("");
									$('#id').focus();
								}
							}
						}	  
				  );
				*/
				
				//@WebServlet("/MemoId")
				//public class MemoId extends HttpServlet {
				  $.ajax(
							{
								url:"MemoId",
								data:{id:$('#id').val()},
								dataType:"html",
								success:function(responseData){
									console.log(responseData); //"true" 또는 "false"
									let data = responseData.trim();
									if(data == "true"){
										alert("사용가능");
										$('#email').focus();
									}else{
										alert("중복ID");
										$('#id').val("");
										$('#id').focus();
									}
								}
							}	  
					  );
			  }
		  });
	  });

	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div style="margin-top:70px;"></div> 
<!--  html 파일  action="MemoServlet"  -->
<form name="f" action="MemoAdd.memo" method="get"> 
 <div align="center"> 
    <table width="600" border="0" cellpadding="7" > 
        <tr align="center" bgcolor="gold" height="50"> 
            <td colspan="2"> 
                <font size="4" color="#0033CC" face="굴림체"> 
                <b> 
                   Servlet Memo
                </b> 
                </font> 
            </td> 
        </tr> 
        <tr> 
            <td width="25%" align="center" > 
                <b>ID</b> 
            </td> 
            <td> 
                <input type="text" size="40" name="id" maxlength="10"  id="id"> 
                <input type="button" value="ID확인" id="message">
            </td> 
        </tr>     
        <tr> 
            <td width="25%" align="center"> 
                <b>EMAIL</b> 
            </td> 
            <td> 
                <input type="text" size="40" id="email" name="email" maxlength="60" > 
            </td> 
        </tr> 
        <tr> 
            <td width="25%" align="center"> 
                <b>MEMO</b> 
            </td> 
            <td> 
                <textarea name="content" cols="50" class="box2"></textarea> 
            </td> 
        </tr> 
        <tr bgcolor="gold"> 
            <td colspan="2" align="center" class="c2"> 
                <input type="submit" value="전송" >
                <input type="reset" value="취소"> 
            </td> 
        </tr> 
     
    </table> 
  
  
 </div>
</form>
<hr>
<a href="MemoList.memo">목록보기</a> 
 <!--  
 http://localhost:8090/WebServlet_4_Memo_mvc/MemoList.memo
 -->
</body>
</html>