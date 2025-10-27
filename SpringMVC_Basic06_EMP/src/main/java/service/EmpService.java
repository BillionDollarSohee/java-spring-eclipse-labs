package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.EmpDao;
import vo.Emp;

@Service
public class EmpService {

    private SqlSession sqlSession;
    private static final int PAGE_SIZE = 10;

    @Autowired
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 사원 목록보기
    public List<Emp> emplist(int page) {
        int start = 1 + (page - 1) * PAGE_SIZE;
        int end   = PAGE_SIZE + (page - 1) * PAGE_SIZE;

        List<Emp> list = null;
        try {
            EmpDao empDao = sqlSession.getMapper(EmpDao.class);
            Map<String, Integer> param = new HashMap<>();
            param.put("start", start);
            param.put("end", end);
            list = empDao.getEmplist(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 사원 상세보기
    public Emp empDetail(String empno) {
        Emp emp = null;
        try {
            EmpDao empDao = sqlSession.getMapper(EmpDao.class);
            emp = empDao.getEmp(Integer.parseInt(empno));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }

    // 사원추가
    public String empReg(Emp e) {
        try {
            EmpDao empDao = sqlSession.getMapper(EmpDao.class);
            empDao.insert(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "redirect:emp.do";
    }

    // 사원 수정 처리
    public String empEdit(Emp e) {
        try {
            EmpDao empDao = sqlSession.getMapper(EmpDao.class);
            empDao.update(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "redirect:empDetail.do?empno=" + e.getEmpno();
    }

    // 사원 삭제
    public String empDel(String empno) {
        try {
            EmpDao empDao = sqlSession.getMapper(EmpDao.class);
            empDao.delete(Integer.parseInt(empno));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "redirect:emp.do";
    }
    
    // 사원 수정 화면용 (조회)
    public Emp empEdit(String empno) {
        Emp emp = null;
        try {
            EmpDao empDao = sqlSession.getMapper(EmpDao.class);
            emp = empDao.getEmp(Integer.parseInt(empno));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }
    
}

