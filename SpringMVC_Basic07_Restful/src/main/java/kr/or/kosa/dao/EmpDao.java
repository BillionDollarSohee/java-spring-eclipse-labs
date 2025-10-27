package kr.or.kosa.dao;

import java.util.List;

import kr.or.kosa.dto.Emp;

public interface EmpDao {
	// Mapper와 연동할 추상함수(CRUD)
	
	// 전체조회
	List<Emp> select();
	
	// 조건조회 
	Emp selectByEmpno(int empno);
	
	// 삽입
	int insert(Emp emp);
	
	// 수정
	int update(Emp emp);
	
	// 삭제
	int delete(int empno);
}
