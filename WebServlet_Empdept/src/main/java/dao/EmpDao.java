package dao;

/* CRUD
1. select empno, ename, mgr, hiredate, sal, comm, deptno from emp
2. select empno, ename, mgr, hiredate, sal, comm, deptno from emp where empno =?
3. insert into emp(empno, ename, mgr, hiredate, sal, comm, deptno) values(?,?,?,?,?,?,?)
4. update emp set empno=?, ename=?, mgr=?, hiredate=?, sal=?, comm=?, deptno=? where empno=?
5. delete from emp where empno=?
6. Like 검색  >> 이름 검색  >> 부서번호 , 부서이름도 같이 출력
   select e.ename, e.deptno, d.dname
   from emp e
   left join dept d on e.deptno = d.deptno
   where e.ename like ?
 */

import dto.Emp;
import dto.EmpDept;
import utils.ConnectionPoolHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmpDao {

    // 1. 전체 조회
    public List<Emp> getEmpAllList() throws SQLException {
        List<Emp> empList = new ArrayList<>();
        String sql = "SELECT empno, ename, mgr, hiredate, sal, comm, deptno FROM emp";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Emp emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setMgr(rs.getInt("mgr"));
                emp.setHiredate(rs.getObject("hiredate", LocalDateTime.class));
                emp.setSal(rs.getInt("sal"));
                // comm이 null일 수 있으므로 getObject 사용
                emp.setComm(rs.getObject("comm", Integer.class) != null ? rs.getInt("comm") : 0);
                emp.setDeptno(rs.getInt("deptno"));

                empList.add(emp);
            }

        } finally {
            ConnectionPoolHelper.close(rs);
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return empList;
    }

    // 2. empno 기준 단일 조회
    public Emp getEmpByEmpno(int empno) {
        Emp emp = null;
        String sql = "SELECT empno, ename, mgr, hiredate, sal, comm, deptno FROM emp WHERE empno=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, empno);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setMgr(rs.getInt("mgr"));
                emp.setHiredate(rs.getObject("hiredate", LocalDateTime.class));
                emp.setSal(rs.getInt("sal"));
                emp.setComm(rs.getObject("comm", Integer.class) != null ? rs.getInt("comm") : 0);
                emp.setDeptno(rs.getInt("deptno"));
            }

        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(rs);
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return emp;
    }

    // 3. insert
    public int insertEmp(Emp emp) {
        int rowcount = 0;
        // hiredate는 DB sysdate를 사용 (Java에서 값 전달 X)
        String sql = "INSERT INTO emp(empno, ename, mgr, hiredate, sal, comm, deptno) VALUES(?,?,?,sysdate,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, emp.getEmpno());
            pstmt.setString(2, emp.getEname());
            pstmt.setInt(3, emp.getMgr());
            pstmt.setInt(4, emp.getSal());
            pstmt.setInt(5, emp.getComm());
            pstmt.setInt(6, emp.getDeptno());

            rowcount = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return rowcount;
    }

    // 4. update
    public int updateEmp(Emp emp) {
        int rowcount = 0;
        String sql = "UPDATE emp SET empno=?, ename=?, mgr=?, hiredate=?, sal=?, comm=?, deptno=? WHERE empno=?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, emp.getEmpno());
            pstmt.setString(2, emp.getEname());
            pstmt.setInt(3, emp.getMgr());
            pstmt.setTimestamp(4, Timestamp.valueOf(emp.getHiredate()));
            pstmt.setInt(5, emp.getSal());
            pstmt.setInt(6, emp.getComm());
            pstmt.setInt(7, emp.getDeptno());
            pstmt.setInt(8, emp.getEmpno());

            rowcount = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return rowcount;
    }

    // 5. delete
    public int deleteEmp(int empno) {
        int rowcount = 0;
        String sql = "DELETE FROM emp WHERE empno=?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, empno);
            rowcount = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return rowcount;
    }

    // 6. LIKE 검색 (사원 이름으로 Emp+Dept 조인 조회)
    public List<EmpDept> selectEmpWithDeptByName(String name) {
        List<EmpDept> list = new ArrayList<>();
        String sql = "SELECT e.ename, e.deptno, d.dname " +
                     "FROM emp e " +
                     "LEFT JOIN dept d ON e.deptno = d.deptno " +
                     "WHERE e.ename LIKE ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EmpDept dto = new EmpDept();
                dto.setEname(rs.getString("ename"));
                dto.setDeptno(rs.getInt("deptno"));
                dto.setDname(rs.getString("dname"));
                list.add(dto);
            }

        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(rs);
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return list;
    }
}

