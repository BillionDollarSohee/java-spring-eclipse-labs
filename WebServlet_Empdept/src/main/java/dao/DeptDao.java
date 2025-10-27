package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Dept;
import utils.ConnectionPoolHelper;

public class DeptDao {

    // 1. 전체 조회 : select deptno, dname, loc from dept
    public List<Dept> getDeptAllList() {
        List<Dept> deptList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT deptno, dname, loc FROM dept";

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Dept dept = new Dept();
                dept.setDeptno(rs.getInt("deptno"));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));
                deptList.add(dept);
            }
        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(rs);
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return deptList;
    }

    // 2. 조건 조회 : select deptno, dname, loc from dept where deptno=?
    public Dept getDeptByDeptno(int deptno) {
        Dept dept = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT deptno, dname, loc FROM dept WHERE deptno=?";

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deptno);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dept = new Dept();
                dept.setDeptno(rs.getInt("deptno"));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));
            }
        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(rs);
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return dept;
    }

    // 3. 삽입 : insert into dept(deptno, dname, loc) values(?,?,?)
    public int insertDept(Dept dept) {
        int rowCount = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO dept(deptno, dname, loc) VALUES(?,?,?)";

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dept.getDeptno());
            pstmt.setString(2, dept.getDname());
            pstmt.setString(3, dept.getLoc());
            rowCount = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return rowCount;
    }

    // 4. 수정 : update dept set dname=?, loc=? where deptno=?
    public int updateDept(Dept dept) {
        int rowCount = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE dept SET dname=?, loc=? WHERE deptno=?";

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dept.getDname());
            pstmt.setString(2, dept.getLoc());
            pstmt.setInt(3, dept.getDeptno());
            rowCount = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return rowCount;
    }

    // 5. 삭제 : delete from dept where deptno=?
    public int deleteDept(int deptno) {
        int rowCount = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM dept WHERE deptno=?";

        try {
            conn = ConnectionPoolHelper.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deptno);
            rowCount = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        } finally {
            ConnectionPoolHelper.close(pstmt);
            ConnectionPoolHelper.close(conn);
        }

        return rowCount;
    }
}
