package bit.dao;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;
import bit.dto.User;

public class UserDao {
    private DataSource ds;

    public UserDao() throws NamingException {
        Context ctx = new InitialContext();
        ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
    }

    // 로그인
    public User login(String email, String pwd) {
        String sql = "SELECT USER_ID, USER_NAME, EMAIL, REGISTER_DATE FROM USERS WHERE EMAIL=? AND PWD=?";
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, pwd);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("USER_ID"));
                    user.setUserName(rs.getString("USER_NAME"));
                    user.setEmail(rs.getString("EMAIL"));
                    user.setRegisterDate(rs.getDate("REGISTER_DATE"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 로그인 실패
    }

    // 아이디(이메일) 중복체크
    public boolean isEmailAvailable(String email) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE EMAIL=?";
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // 이미 존재
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
