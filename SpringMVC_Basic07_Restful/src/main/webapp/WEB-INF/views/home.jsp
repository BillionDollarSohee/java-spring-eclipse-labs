<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
	
	<script type="text/javascript">
	
		$(document).ready(function(){
			
			// === EMP 관련 기존 함수들 ===
			function formview(){
				$("#formempno").val("");
				$("#formename").val("");
				$("#formsal").val("");
				
				if(document.getElementById("writeform").classList.item(2) == null){
		            document.getElementById("writeform").className += " visually-hidden";
		        }else{
		            document.getElementById("writeform").classList.remove("visually-hidden");
		        }
			}
			
			function formupdateview(){
				
				if(document.getElementById("updateform").classList.item(2) == null){
		            document.getElementById("updateform").className += " visually-hidden";
		        }else{
		            document.getElementById("updateform").classList.remove("visually-hidden");
		        }
			}
			
			//사원 전체 조회 함수
			function empList(){
				$.ajax({
					type: "get",
					url: "emp",
					contentType: "application/json; charset=utf-8",
					success: function(data){
						
						$("#empList").empty();
						let html = "";
						
						$.each(data, function(){
							html += "<tr>";
							html += '<td>' + this.empno + '</td>';
							html += '<td>' + this.ename + '</td>';
							html += '<td>' + this.sal + '</td>';
							html += '<td><a class="btn btn-outline-primary empupdate" value2="' + this.empno + '">수정</a></td>';
							html +=	'<td><a class="btn btn-outline-primary empdelete" value2="' + this.empno + '">삭제</a></td>';
							html += "</tr>";
						});
						
						$("#empList").append(html);
						
					}
				});
			}
		
			//전체 조회
			empList();
			
			//특정 사원 검색
			function searchByEmpno(requestdata1){
				$.ajax({
					type: "get",
					url: "emp/"+requestdata1,
					success: function(data){
						
						console.log(data);
						
						$("#empList").empty();
						let html = "";
						
						html += "<tr>";
						html += '<td>' + data.empno + '</td>';
						html += '<td>' + data.ename + '</td>';
						html += '<td>' + data.sal + '</td>';
						html += '<td><a class="btn btn-outline-primary empupdate" value2="' + data.empno + '">수정</a></td>';
						html +=	'<td><a class="btn btn-outline-primary empdelete" value2="' + data.empno + '">삭제</a></td>';
						html += "</tr>";
						
						$("#empList").append(html);
						
					}
				});
			}
			
			//특정 사원 검색
			$("#empnosearchbtn").click(function(){
				if($("#empnosearch").val() == "" || $("#empnosearch").val() == null){
					empList();
				}else{
					searchByEmpno($("#empnosearch").val());
				}
			});
			
			//사원등록 form 보이게 하거나 지우기
			$('#empwritebtn').click(function(){
				formview();
			});
			
			//사원 등록 비동기 처리
			$("#empwritesubmit").click(function(){
				
				let requestdata = {
							"empno": $("#formempno").val(), 
							"ename": $("#formename").val(),
							"sal": $("#formsal").val(),
						}
				let data = JSON.stringify(requestdata);
				console.log(data);
				$.ajax({
					type: "post",
					url: "emp",
					data: data,
					dataType: "text",
					contentType: "application/json; charset=utf-8",
					success: function(data){
						formview();
						empList();
						alert(data);
					}
				});
			});
			
			//등록 취소
			$("#empwritereset").on("click", function(){
				formview();
			});
			
			//해당 emp 컬럼 삭제
			$(document).on("click",".empdelete", function(){
				
				console.log($(this).attr("value2"));
				
				$.ajax({
					type: "DELETE",
					url: "emp/"+$(this).attr("value2"),
					success: function(data){
						empList();
					}
				});
			});
			
			//수정 기능 함수
			function updateEmp(){
				let requestdata = {
						"empno": $("#formempno1").val(), 
						"ename": $("#formename1").val(),
						"sal": $("#formsal1").val(),
					}
				let data = JSON.stringify(requestdata);
				$.ajax({
					type: "put",
					url: "emp",
					data: data,
					dataType: "text",
					contentType: "application/json; charset=utf-8",
					success: function(data){
						formupdateview();
						empList();
						alert(data);
					}
				});
			}
			
			//해당 emp 컬럼 수정 버튼 클릭
			$(document).on("click", ".empupdate", function(){
				formupdateview();
				
				var tr1 = $(this).closest('tr');
				
				$("#formempno1").val(tr1.find("td:eq(0)").text());
				$("#formename1").val(tr1.find("td:eq(1)").text());
				$("#formsal1").val(tr1.find("td:eq(2)").text());
				
			});
			
			//수정 기능 실행
			$(document).on("click", "#empwritesubmit1", function(){
				updateEmp();
			});
			
			// === DEPT 관련 새로 추가된 함수들 (Fetch API 사용) ===
			
			// 부서 전체 조회 함수
			async function deptList(){
				try {
					const response = await fetch('dept', {
						method: 'GET',
						headers: {
							'Content-Type': 'application/json; charset=utf-8'
						}
					});
					
					if (response.ok) {
						const data = await response.json();
						
						$("#deptList").empty();
						let html = "";
						
						data.forEach(function(dept){
							html += "<tr>";
							html += '<td>' + dept.deptno + '</td>';
							html += '<td>' + dept.dname + '</td>';
							html += '<td>' + dept.loc + '</td>';
							html += '<td><button class="btn btn-outline-primary deptupdate" data-deptno="' + dept.deptno + '" data-dname="' + dept.dname + '" data-loc="' + dept.loc + '">수정</button></td>';
							html +=	'<td><button class="btn btn-outline-danger deptdelete" data-deptno="' + dept.deptno + '">삭제</button></td>';
							html += "</tr>";
						});
						
						$("#deptList").append(html);
					}
				} catch (error) {
					console.error('Error:', error);
				}
			}
			
			// 부서 검색 함수 - 수정된 부분
			// 부서 검색 함수 - 개선된 버전
			/* async function searchByDeptno(deptno){
				try {
					console.log('부서 검색 요청:', deptno);
					const response = await fetch(`dept/${deptno}`, {
						method: 'GET',
						headers: {
							'Content-Type': 'application/json; charset=utf-8'
						}
					});
					
					console.log('검색 응답 상태:', response.status);
					
					$("#deptList").empty();
					let html = "";
					
					if (response.ok) {
						const data = await response.json();
						console.log('검색 결과 데이터:', data);
						
						// 데이터가 존재하는지 확인
						if (data && data.deptno) {
							html += "<tr>";
							html += '<td>' + data.deptno + '</td>';
							html += '<td>' + (data.dname || '') + '</td>';
							html += '<td>' + (data.loc || '') + '</td>';
							html += '<td><button class="btn btn-outline-primary deptupdate" data-deptno="' + data.deptno + '" data-dname="' + data.dname + '" data-loc="' + data.loc + '">수정</button></td>';
							html +=	'<td><button class="btn btn-outline-danger deptdelete" data-deptno="' + data.deptno + '">삭제</button></td>';
							html += "</tr>";
						} else {
							html += "<tr><td colspan='5' class='text-center'>검색 결과가 없습니다.</td></tr>";
						}
					} else if (response.status === 404) {
						console.log('부서를 찾을 수 없음');
						html += "<tr><td colspan='5' class='text-center'>해당 부서번호를 찾을 수 없습니다.</td></tr>";
					} else {
						console.error('부서 검색 실패:', response.status);
						const errorText = await response.text();
						console.error('에러 내용:', errorText);
						html += "<tr><td colspan='5' class='text-center'>검색 중 오류가 발생했습니다.</td></tr>";
					}
					
					$("#deptList").append(html);
					
				} catch (error) {
					console.error('검색 중 예외 발생:', error);
					$("#deptList").empty();
					$("#deptList").append("<tr><td colspan='5' class='text-center'>검색 중 오류가 발생했습니다.</td></tr>");
				}
			} */
			// 부서 검색 함수 (jQuery 버전으로 변경)
			function searchByDeptno(deptno){
				$.ajax({
					type: "GET",
					url: "dept/" + deptno,
					success: function(data){
						console.log('검색 결과:', data);
						
						$("#deptList").empty();
						let html = "";
						
						// 데이터가 존재하는지 확인
						if (data && data.deptno) {
							html += "<tr>";
							html += '<td>' + data.deptno + '</td>';
							html += '<td>' + data.dname + '</td>';
							html += '<td>' + data.loc + '</td>';
							html += '<td><button class="btn btn-outline-primary deptupdate" data-deptno="' + data.deptno + '" data-dname="' + data.dname + '" data-loc="' + data.loc + '">수정</button></td>';
							html +=	'<td><button class="btn btn-outline-danger deptdelete" data-deptno="' + data.deptno + '">삭제</button></td>';
							html += "</tr>";
						} else {
							html += "<tr><td colspan='5' class='text-center'>검색 결과가 없습니다.</td></tr>";
						}
						
						$("#deptList").append(html);
					},
					error: function(xhr, status, error) {
						console.error('부서 검색 실패:', xhr.responseText);
						$("#deptList").empty();
						$("#deptList").append("<tr><td colspan='5' class='text-center'>해당 부서번호를 찾을 수 없습니다.</td></tr>");
					}
				});
			}

			
			// 부서 등록 함수
			async function insertDept(deptData) {
				try {
					const response = await fetch('dept', {
						method: 'POST',
						headers: {
							'Content-Type': 'application/json; charset=utf-8'
						},
						body: JSON.stringify(deptData)
					});
					
					const result = await response.text();
					
					if (response.ok) {
						alert(result);
						deptList(); // 목록 새로고침
						// 모달 닫기
						const modal = bootstrap.Modal.getInstance(document.getElementById('deptInsertModal'));
						modal.hide();
						// 폼 리셋
						document.getElementById('deptInsertForm').reset();
					} else {
						alert('등록 실패: ' + result);
					}
				} catch (error) {
					console.error('Error:', error);
					alert('등록 중 오류가 발생했습니다.');
				}
			}
			
			// 부서 수정 함수
			async function updateDept(deptData) {
				try {
					const response = await fetch('dept', {
						method: 'PUT',
						headers: {
							'Content-Type': 'application/json; charset=utf-8'
						},
						body: JSON.stringify(deptData)
					});
					
					const result = await response.text();
					
					if (response.ok) {
						alert(result);
						deptList(); // 목록 새로고침
						// 모달 닫기
						const modal = bootstrap.Modal.getInstance(document.getElementById('deptUpdateModal'));
						modal.hide();
					} else {
						alert('수정 실패: ' + result);
					}
				} catch (error) {
					console.error('Error:', error);
					alert('수정 중 오류가 발생했습니다.');
				}
			}
			
			/* // 부서 삭제 함수
			async function deleteDept(deptno) {
				if (confirm('정말 삭제하시겠습니까?')) {
					try {
						const response = await fetch(`dept/${deptno}`, {
							method: 'DELETE'
						});
						
						const result = await response.text();
						
						if (response.ok) {
							alert(result);
							deptList(); // 목록 새로고침
						} else {
							alert('삭제 실패: ' + result);
						}
					} catch (error) {
						console.error('Error:', error);
						alert('삭제 중 오류가 발생했습니다.');
					}
				}
			} */
			
			// 부서 삭제 함수 (jQuery 버전)
			function deleteDept(deptno) {
			    if (confirm('정말 삭제하시겠습니까?')) {
			        $.ajax({
			            type: "DELETE",
			            url: "dept/" + deptno,
			            success: function(data) {
			                alert(data);
			                deptList();
			            },
			            error: function(xhr, status, error) {
			                console.error('삭제 실패:', xhr.responseText);
			                alert('삭제 실패: ' + xhr.status);
			            }
			        });
			    }
			}
			
			// 부서 목록 로드
			deptList();
			
			// === 이벤트 핸들러들 ===
			
			// 부서 검색
			$("#deptnosearchbtn").click(function(){
				if($("#deptnosearch").val() == "" || $("#deptnosearch").val() == null){
					deptList();
				}else{
					searchByDeptno($("#deptnosearch").val());
				}
			});
			
			// 부서 등록 폼 제출
			$("#deptInsertForm").submit(function(e){
				e.preventDefault();
				
				const deptData = {
					deptno: parseInt($("#insertDeptno").val()),
					dname: $("#insertDname").val(),
					loc: $("#insertLoc").val()
				};
				
				insertDept(deptData);
			});
			
			// 부서 수정 폼 제출
			$("#deptUpdateForm").submit(function(e){
				e.preventDefault();
				
				const deptData = {
					deptno: parseInt($("#updateDeptno").val()),
					dname: $("#updateDname").val(),
					loc: $("#updateLoc").val()
				};
				
				updateDept(deptData);
			});
			
			// 부서 수정 버튼 클릭
			$(document).on("click", ".deptupdate", function(){
				const deptno = $(this).data("deptno");
				const dname = $(this).data("dname");
				const loc = $(this).data("loc");
				
				$("#updateDeptno").val(deptno);
				$("#updateDname").val(dname);
				$("#updateLoc").val(loc);
				
				// 모달 열기
				const modal = new bootstrap.Modal(document.getElementById('deptUpdateModal'));
				modal.show();
			});
			
			// 부서 삭제 버튼 클릭
			$(document).on("click", ".deptdelete", function(){
				const deptno = $(this).data("deptno");
				deleteDept(deptno);
			});
			
		});
	
	</script>
	
</head>
<body>

	<!-- 탭 네비게이션 추가 -->
	<div class="container mt-3">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item" role="presentation">
				<button class="nav-link active" id="emp-tab" data-bs-toggle="tab" data-bs-target="#emp" type="button" role="tab">사원 관리</button>
			</li>
			<li class="nav-item" role="presentation">
				<button class="nav-link" id="dept-tab" data-bs-toggle="tab" data-bs-target="#dept" type="button" role="tab">부서 관리</button>
			</li>
		</ul>
		
		<div class="tab-content" id="myTabContent">
			
			<!-- 사원 관리 탭 (기존 코드) -->
			<div class="tab-pane fade show active" id="emp" role="tabpanel">
				<div class="m-5">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>사원번호 :
									<input type="text" id="empnosearch">
									<input type="submit" id="empnosearchbtn" value="검색">
								</th>
								<th><a class="btn btn-primary" id="empwritebtn">사원 등록</a></th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div id="writeform" class="m-5 row visually-hidden">
					<div class="col-2"></div>
					<div class="col-8 text-center">
						<h3><strong>**사원**</strong></h3>
						<table class="table table-striped">
							<tbody>
								<tr>
									<td>EMPNO</td>
									<td><input type="text" id="formempno" value="" required /></td>
								</tr>
								<tr>
									<td>ENAME</td>
									<td><input type="text" id="formename" value="" /></td>
								</tr>
								<tr>
									<td>SAL</td>
									<td><input type="text" id="formsal" value="" /></td>
								</tr>
							</tbody>
						</table>
						<a class="btn btn-outline-primary" id="empwritesubmit">확인</a>
						<a class="btn btn-outline-danger" id="empwritereset">취소</a>
					</div>
					<div class="col-2"></div>
				</div>
				
				<div id="updateform" class="m-5 row visually-hidden">
					<div class="col-2"></div>
					<div class="col-8 text-center">
						<h3><strong>**사원**</strong></h3>
						<table class="table table-striped">
							<tbody id="updatef">
								<tr>
									<td>EMPNO</td>
									<td><input type="text" id="formempno1" value="" required /></td>
								</tr>
								<tr>
									<td>ENAME</td>
									<td><input type="text" id="formename1" value="" /></td>
								</tr>
								<tr>
									<td>SAL</td>
									<td><input type="text" id="formsal1" value="" /></td>
								</tr>
							</tbody>
						</table>
						<a class="btn btn-outline-primary" id="empwritesubmit1">확인</a>
						<a class="btn btn-outline-danger" id="empwritereset1">취소</a>
					</div>
					<div class="col-2"></div>
				</div>
				
				<div class="m-3">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>EMPNO</th>
								<th>ENAME</th>
								<th>SAL</th>
								<th>수정</th>
								<th>삭제</th>
							</tr>
						</thead>
						<tbody id="empList">
						</tbody>
					</table>
				</div>
			</div>
			
			<!-- 부서 관리 탭 (새로 추가) -->
			<div class="tab-pane fade" id="dept" role="tabpanel">
				<div class="m-5">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>부서번호 :
									<input type="text" id="deptnosearch">
									<input type="submit" id="deptnosearchbtn" value="검색">
								</th>
								<th><button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#deptInsertModal">부서 등록</button></th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div class="m-3">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>DEPTNO</th>
								<th>DNAME</th>
								<th>LOC</th>
								<th>수정</th>
								<th>삭제</th>
							</tr>
						</thead>
						<tbody id="deptList">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 부서 등록 모달 -->
	<div class="modal fade" id="deptInsertModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">부서 등록</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<form id="deptInsertForm">
					<div class="modal-body">
						<div class="mb-3">
							<label for="insertDeptno" class="form-label">부서번호</label>
							<input type="number" class="form-control" id="insertDeptno" required>
						</div>
						<div class="mb-3">
							<label for="insertDname" class="form-label">부서명</label>
							<input type="text" class="form-control" id="insertDname" required>
						</div>
						<div class="mb-3">
							<label for="insertLoc" class="form-label">위치</label>
							<input type="text" class="form-control" id="insertLoc" required>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-primary">등록</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 부서 수정 모달 -->
	<div class="modal fade" id="deptUpdateModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">부서 수정</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<form id="deptUpdateForm">
					<div class="modal-body">
						<div class="mb-3">
							<label for="updateDeptno" class="form-label">부서번호</label>
							<input type="number" class="form-control" id="updateDeptno" readonly>
						</div>
						<div class="mb-3">
							<label for="updateDname" class="form-label">부서명</label>
							<input type="text" class="form-control" id="updateDname" required>
						</div>
						<div class="mb-3">
							<label for="updateLoc" class="form-label">위치</label>
							<input type="text" class="form-control" id="updateLoc" required>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-primary">수정</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	
</body>
</html>