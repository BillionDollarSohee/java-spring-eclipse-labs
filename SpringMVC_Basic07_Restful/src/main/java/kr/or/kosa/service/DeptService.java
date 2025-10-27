package kr.or.kosa.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.kosa.dao.DeptDao;
import kr.or.kosa.dto.Dept;

@Service
public class DeptService {
	
	private SqlSession sqlsession;
	
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	public List<Dept> selectAllDeptList(){
		DeptDao deptdao = sqlsession.getMapper(DeptDao.class);
		List<Dept> list = deptdao.select();
		return list;
	}
	
	public Dept selectDeptByDeptno(int deptno) {
		DeptDao deptdao = sqlsession.getMapper(DeptDao.class);
		return deptdao.selectByDeptno(deptno);
	}	
	
	//작성해 보세요
	
	public int insert(Dept dept) {
		DeptDao deptdao = sqlsession.getMapper(DeptDao.class);
		return  deptdao.insert(dept);
	}
	
	public int update(Dept dept) {
		DeptDao deptdao = sqlsession.getMapper(DeptDao.class);
		return  deptdao.update(dept);
	}
	
	public int delete(int deptno) {
		DeptDao deptdao = sqlsession.getMapper(DeptDao.class);
		return  deptdao.delete(deptno);
	}
	
}
