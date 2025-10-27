package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Emp;
import utils.ConnectionPoolHelper;

public class EmpDao {
	
	
	//삽입
	public int insertEmp(Emp emp) {
		
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    // hiredate 부분을 sysdate로 고정
	    String sql = "insert into Emp(empno, ename, job, mgr, hiredate, comm, deptno, sal) "
	               + "values(?,?,?,?, sysdate, ?,?,?)";
	    int resultRow = 0;
			
	    try {
	        conn = ConnectionPoolHelper.getConnection();
	        pstmt = conn.prepareStatement(sql);
				  
	        pstmt.setInt(1, emp.getEmpno());
	        pstmt.setString(2, emp.getEname());
	        pstmt.setString(3, emp.getJob());
	        pstmt.setInt(4, emp.getMgr());
	        pstmt.setInt(5, emp.getComm());
	        pstmt.setInt(6, emp.getDeptno());
	        pstmt.setInt(7, emp.getSal());
				  
	        resultRow = pstmt.executeUpdate(); 
						  
	    } catch (Exception e) {
	        System.out.println("예외발생 : " + e.getMessage());
	    } finally {
	        ConnectionPoolHelper.close(pstmt);
	        ConnectionPoolHelper.close(conn);
	    }
		
	    return resultRow;
	}
	
	public int updateEmp(Emp emp) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql="update Emp set ename=? , job=?, mgr=?,  sal=?, comm=?, deptno=?  where empno=?";
		int resultRow = 0;
		
		try {
			  conn = ConnectionPoolHelper.getConnection();
			  pstmt = conn.prepareStatement(sql);
			  
			 
			  pstmt.setString(1, emp.getEname());
			  pstmt.setString(2, emp.getJob());
			  pstmt.setInt(3, emp.getMgr());
			  pstmt.setInt(4, emp.getSal());
			  pstmt.setInt(5, emp.getComm());
			  pstmt.setInt(6, emp.getDeptno());
			  pstmt.setInt(7, emp.getEmpno());
			  
			  resultRow = pstmt.executeUpdate();
			  
					  
		} catch (Exception e) {
			 System.out.println("예외발생 : " + e.getMessage());
		}finally {
			ConnectionPoolHelper.close(pstmt);
			ConnectionPoolHelper.close(conn);
		}
	
		return resultRow;
	}
	
public Emp getEmpListById(int id) throws SQLException{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select empno , ename , job, mgr, hiredate, comm, deptno, sal from Emp where empno=?";
		Emp Emp = new Emp();
		
		try {
			conn = ConnectionPoolHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Emp.setEmpno(rs.getInt("empno"));
				Emp.setEname(rs.getString("ename"));
				Emp.setJob(rs.getString("job"));
				Emp.setMgr(rs.getInt("mgr"));
				Emp.setHiredate(rs.getDate("hiredate"));
				Emp.setComm(rs.getInt("comm"));
				Emp.setDeptno(rs.getInt("deptno"));
				Emp.setSal(rs.getInt("sal"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPoolHelper.close(rs);
			ConnectionPoolHelper.close(pstmt);
			ConnectionPoolHelper.close(conn);
		}
		return Emp;
	}
	
public List<Emp> getEmpList()  throws SQLException{
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "select empno , ename , job, mgr, hiredate, comm, deptno, sal from Emp";
	
	conn = ConnectionPoolHelper.getConnection(); //POOL 전환
	pstmt = conn.prepareStatement(sql);
	rs = pstmt.executeQuery();
	
	List<Emp> EmpList = new ArrayList<Emp>();

	while(rs.next()) {
		Emp emp = new Emp();
		emp.setEmpno(rs.getInt("empno"));
		emp.setEname(rs.getString("ename"));
		emp.setJob(rs.getString("job"));
		emp.setMgr(rs.getInt("mgr"));
		emp.setHiredate(rs.getDate("hiredate"));
		emp.setComm(rs.getInt("comm"));
		emp.setDeptno(rs.getInt("deptno"));
		emp.setSal(rs.getInt("sal"));
		
		EmpList.add(emp);
	}
	

	
	ConnectionPoolHelper.close(rs);
	ConnectionPoolHelper.close(pstmt);
	ConnectionPoolHelper.close(conn);
	
	return EmpList;
}

public int deleteEmp(int empno) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql=" delete from Emp where empno=?";
    int resultRow = 0;
    
    try {
         conn = ConnectionPoolHelper.getConnection();
         pstmt = conn.prepareStatement(sql);
         
        
         pstmt.setInt(1, empno);
       
         
         resultRow = pstmt.executeUpdate(); //insert , update , delete
         
               
    } catch (Exception e) {
        System.out.println("예외발생 : " + e.getMessage());
    }finally {
       ConnectionPoolHelper.close(pstmt);
       ConnectionPoolHelper.close(conn);
    }
 
    return resultRow;
 }
	
	
}
