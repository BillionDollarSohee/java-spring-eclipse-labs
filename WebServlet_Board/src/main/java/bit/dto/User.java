package bit.dto;

import java.util.Date;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String pwd;
    private Date registerDate;

    // === 기본 생성자 ===
    public User() {}

    // === 모든 필드 생성자 ===
    public User(int userId, String userName, String email, String pwd, Date registerDate) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.pwd = pwd;
        this.registerDate = registerDate;
    }

    // === 로그인 후 세션에 필요한 정보만 받는 생성자 (id, name, email) ===
    public User(int userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    // === Getter & Setter ===
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId +
               ", userName=" + userName +
               ", email=" + email +
               ", registerDate=" + registerDate + "]";
    }
}
