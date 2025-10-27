package com.model;

public class NewArticleCommand {

    private int parentId;
    private String title;
    private String content;

    // 기본 생성자
    public NewArticleCommand() {}

    // Getter & Setter
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // toString()
    @Override
    public String toString() {
        return "NewArticleCommand{" +
                "parentId=" + parentId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    // equals() & hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewArticleCommand)) return false;

        NewArticleCommand that = (NewArticleCommand) o;

        if (parentId != that.parentId) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = parentId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
