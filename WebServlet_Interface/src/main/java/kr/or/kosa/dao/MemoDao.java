package kr.or.kosa.dao;
/*
DB연결
CRUD 함수 생성 > memo
1. 전체조회  : select id , email , content from memo
2. 조건조회  : select id , email , content from memo where id=? //제약 id >pk
3. 삽입     : insert into memo(id,email,content) values(?,?,?)
4. 수정     : update memo set email=? , content=? where id=?
5. 삭제     : delete from memo where id=?
알파... LIKE 검색  where email like '%naver%'

자바로 함수를 생성 .... 처리 ... 거의 똑같아요
ArrayList
HashMap
제너릭 ...
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kr.or.kosa.dto.Memo;
import kr.or.kosa.utils.ConnectionPoolHelper;


public class MemoDao {
	
	/*
	Connection conn = null;
	
	public MemoDao() {
		conn = ConnectionHelper.getConnection(DBType.ORACLE);
	}
	*/
	//전체조회
	public List<Memo> getMemoList()  throws SQLException{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id , email , content from memo";
		
		conn = ConnectionPoolHelper.getConnection(); //POOL 전환
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		List<Memo> memoList = new ArrayList<Memo>();
		//[new Memo()][new Memo()][new Memo()]
		while(rs.next()) {
			Memo memo = new Memo();
			memo.setId(rs.getString("id"));
			memo.setEmail(rs.getString("email"));
			memo.setContent(rs.getString("content"));
			
			memoList.add(memo);
		}
		
		ConnectionPoolHelper.close(rs);
		ConnectionPoolHelper.close(pstmt);
		ConnectionPoolHelper.close(conn);
		
		return memoList;
	}
	
	//조건조회
	public Memo getMemoListById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id , email , content from memo where id=?";
		Memo memo = new Memo();
		
		try {
			conn = ConnectionPoolHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				memo.setId(rs.getString("id"));
				memo.setEmail(rs.getString("email"));
				memo.setContent(rs.getString("content"));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			ConnectionPoolHelper.close(rs);
			ConnectionPoolHelper.close(pstmt);
			ConnectionPoolHelper.close(conn);
		}
	
		
		return memo;
	}
	
	//삽입
	public int insertMemo(Memo memo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql="insert into memo(id,email,content) values(?,?,?)";
		int resultRow = 0;
		
		try {
			  conn = ConnectionPoolHelper.getConnection();
			  pstmt = conn.prepareStatement(sql);
			  
			  pstmt.setString(1, memo.getId());
			  pstmt.setString(2, memo.getEmail());
			  pstmt.setString(3, memo.getContent());
			  
			  resultRow = pstmt.executeUpdate(); //insert , update , delete
			  
					  
		} catch (Exception e) {
			 System.out.println("예외발생 : " + e.getMessage());
		}finally {
			ConnectionPoolHelper.close(pstmt);
			ConnectionPoolHelper.close(conn);
		}
	
		return resultRow;
	}
	
	//수정
	public int updateMemo(Memo memo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql="update memo set email=? , content=? where id=?";
		int resultRow = 0;
		
		try {
			  conn = ConnectionPoolHelper.getConnection();
			  pstmt = conn.prepareStatement(sql);
			  
			
			  pstmt.setString(1, memo.getEmail());
			  pstmt.setString(2, memo.getContent());
			  pstmt.setString(3, memo.getId());
			  
			  resultRow = pstmt.executeUpdate(); //insert , update , delete
			  
					  
		} catch (Exception e) {
			 System.out.println("예외발생 : " + e.getMessage());
		}finally {
			ConnectionPoolHelper.close(pstmt);
			ConnectionPoolHelper.close(conn);
		}
	
		return resultRow;
	}
	
	//삭제
	public int deleteMemo(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql="delete from memo where id=?";
		int resultRow = 0;
		
		try {
			  conn = ConnectionPoolHelper.getConnection();
			  pstmt = conn.prepareStatement(sql);
			  
			  pstmt.setString(1, id);
			  resultRow = pstmt.executeUpdate(); //insert , update , delete
			  
					  
		} catch (Exception e) {
			 System.out.println("예외발생 : " + e.getMessage());
		}finally {
			ConnectionPoolHelper.close(pstmt);
			ConnectionPoolHelper.close(conn);
		}
	
		return resultRow;
	}
	
	//필요에 따라서
	//LIKE 등등
	public Memo idSearchByEmail(String email) {
		return null;
	}
	
	//ID유무
	public String isCheckById(String id) {
		
		String ismemoid=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="select id from memo where id=?";

		Connection conn = null;
		try {
			  conn = ConnectionPoolHelper.getConnection();
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setString(1, id);
			  		
			  rs = pstmt.executeQuery();
			  if(rs.next()) { //동일한 ID 있다
				  //같은 ID 존재
				  ismemoid = "false";
			  }else {
				  //사용가능한 ID
				  ismemoid = "true";
			  }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			ConnectionPoolHelper.close(rs);
			ConnectionPoolHelper.close(pstmt);
			try {
				ConnectionPoolHelper.close(conn); //반환하기
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return ismemoid;
	}
}
