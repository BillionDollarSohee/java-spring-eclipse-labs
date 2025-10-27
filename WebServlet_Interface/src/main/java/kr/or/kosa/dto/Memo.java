package kr.or.kosa.dto;

public class Memo {
    private String id;
    private String email;
    private String content;

    // 빈 생성자
    public Memo() {
    }

    // 모든 필드 생성자
    public Memo(String id, String email, String content) {
        this.id = id;
        this.email = email;
        this.content = content;
    }

    // getters & setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    // toString
    @Override
    public String toString() {
        return "Memo{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
