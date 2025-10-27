package kr.or.kosa.dao;

import java.util.List;

import kr.or.kosa.dto.Dept;


public interface DeptDao {
		// 전체조회
		List<Dept> select();
		
		// 조건조회 
		Dept selectByDeptno(int deptno);
		
		// 삽입
		int insert(Dept dept);
		
		// 수정
		int update(Dept dept);
		
		// 삭제
		int delete(int deptno);
}
