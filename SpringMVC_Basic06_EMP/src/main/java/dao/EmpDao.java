package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import vo.Emp;

public interface EmpDao {
	// 사원추가
	public int insert(Emp e) throws ClassNotFoundException, SQLException;
	// 사원수
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException;
	// 전체 사원 목록
	List<Emp> getEmplist(Map<String, Integer> map) throws SQLException;
    // 사원 상세보기
    public Emp getEmp(int empno) throws ClassNotFoundException, SQLException;
    // 사원 수정
    public int update(Emp e) throws ClassNotFoundException, SQLException;
    // 사원 삭제
    public int delete(int empno) throws ClassNotFoundException, SQLException; 
	
}
