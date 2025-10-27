package dto;

public class EmpDept {
    private String ename;
    private int deptno;
    private String dname;

    // ======= 생성자 =======

    // 기본 생성자
    public EmpDept() {
    }

    // 모든 필드를 받는 생성자
    public EmpDept(String ename, int deptno, String dname) {
        this.ename = ename;
        this.deptno = deptno;
        this.dname = dname;
    }

    // ======= Getter & Setter =======

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    // ======= toString =======

    @Override
    public String toString() {
        return "EmpDept{" +
                "ename='" + ename + '\'' +
                ", deptno=" + deptno +
                ", dname='" + dname + '\'' +
                '}';
    }
}
