package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

//클래스 > 많이 사용 > 편하게 > new > static 
public class ConnectionHelper {
   public static Connection getConnection(DBType dbType) {
	  
	   
	   Connection conn = null;
	   
	   try {
		    Class.forName("oracle.jdbc.OracleDriver");
		   	switch (dbType) {
				case ORACLE:
					    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","KOSA","1004");
					break;
				case MARIADB:
						conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sampledb","KOSA","1004");
					break;		
			}
		} catch (Exception e) {
			System.err.println("connection Factory : "  + e.getMessage());
		}
	   
	   return conn;
   }	
   
   
   public static Connection getConnection(DBType dbType,String id, String pwd) {
	   
	   Connection conn = null;
	   
	   try {
		   	switch (dbType) {
				case ORACLE:
					    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",id,pwd);
					break;
				case MARIADB:
						conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/shopdb",id,pwd);
					break;		
			}
		} catch (Exception e) {
			System.err.println("connection Factory : "  + e.getMessage());
		}
	   
	   return conn;
   }	
   
   
   public static void close(Connection conn) {
	   if(conn != null) {
		   try {
			   conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
	   }
   }
   
   public static void close(ResultSet rs) {
	   if(rs != null) {
		   try {
			   rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
	   }
   }
   public static void close(Statement stmt) {
	   if(stmt != null) {
		   try {
			   stmt.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
	   }
   }
   
   public static void close(PreparedStatement pstmt) {
	   if(pstmt != null) {
		   try {
			   pstmt.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
	   }
   }
}
