package kr.or.kosa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.dto.Dept;
import kr.or.kosa.dto.Emp;
import kr.or.kosa.service.DeptService;

@RestController
@RequestMapping("/dept")
public class DeptController {

	private DeptService deptService;
	
	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	// @RestController = @Controller + @ResponseBody -> 모든 함수는 비동기처리 됨 
	// 전체조회
	// @RequestMapping("/emp") + GET(행위)
	@GetMapping
	public ResponseEntity<List<Dept>> deptList(){
		List<Dept> list = new ArrayList<Dept>();
		try {
			System.out.println("정상 실행");
			list = deptService.selectAllDeptList();
			return new ResponseEntity<List<Dept>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("비정상 실행");
			return new ResponseEntity<List<Dept>>(list, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// 조건조회 
	// http://192.168.2.8/emp/7902
	// @RequestMapping("/emp") 이건 이미 있으니까 7902 이걸 받아야 함 
	// @RequestMapping(value="{empno}", method=RequestMethod.GET)
	/* 값이 여러개라면 
	   @GetMapping("/{userId}/{itemId}") >> /api/users/7902/1004
	   public Emp empListByEmpno(@PathVariable("userid") int userid,
	   							 @PathVariable("itemid") int itemid) 
	 */
//	@GetMapping("{deptno}")
//	public ResponseEntity<String> deptListByDeptno(@PathVariable("deptno") int deptno) {
//		
//		try {
//			System.out.println("deptListByDeptno 정상실행");
//			deptService.selectDeptByDeptno(deptno);
//			return new ResponseEntity<String>("deptListByDeptno success", HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<String>("deptListByDeptno fail", HttpStatus.BAD_REQUEST);
//		}
//	}
	@GetMapping("{deptno}")
	public Dept deptListByDeptno(@PathVariable("deptno") int deptno) {
		return deptService.selectDeptByDeptno(deptno);
	}
	
	// 삽입
	// /emp + 데이터(JSON	객체 형태의 문자열) + POST 
	// 동기방식 (DTO 타입 parameter) -> 비동기(@RequestBody Emp emp) 이런식으로 받음 
	// 이거를 POSTMAN에서 테스트 후 문제 없으면 구현에 들어간다 -> 그러면 문제를 줄일 수 있음 
	@PostMapping
	public ResponseEntity<String> insert(@RequestBody Dept dept){
		try {
			System.out.println("insert");
			System.out.println(dept.toString());
			deptService.insert(dept);
			return new ResponseEntity<String>("insert success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("insert fail", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 수정
	// @PutMapping @RequestBody Emp emp
	@PutMapping
	public ResponseEntity<String> update(@RequestBody Dept dept){
		try {
			System.out.println("update");
			System.out.println(dept.toString());
			deptService.update(dept);
			return new ResponseEntity<String>("udpate success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("update fail", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 삭제 
	// @DeleteMapping @PathVariable("empno") int empno 
	@DeleteMapping("{deptno}")
	public ResponseEntity<String> delete(@PathVariable("deptno") int deptno) {
		try {
			System.out.println("delete");
			deptService.delete(deptno);
			return new ResponseEntity<String>("delete success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("delete fail", HttpStatus.BAD_REQUEST);
		}
		
	}
	// POSTMAN TEST
	
	
}
