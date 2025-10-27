package service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.NoticeDao;
import vo.Notice;
import javax.servlet.http.HttpServletRequest;

@Service // 서비스 애너테이션은 컴포넌트 스캔으로 빈으로 등록하려는 목적
public class CustomerService {

    // CustomerService는 SQLSession 에 의존하며 sqlSession는 싱글톤 객체라서 클로즈 안해도 됨
    private SqlSession sqlSession;

    @Autowired
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 글목록보기 서비스
    public List<Notice> notices(String pg, String f, String q) {
        int page = 1;
        String field = "TITLE";
        String query = "%%";

        if (pg != null && !pg.equals("")) {
            page = Integer.parseInt(pg);
        }
        if (f != null && !f.equals("")) {
            field = f;
        }
        if (q != null && !q.equals("")) {
            query = q;
        }

        List<Notice> list = null;
        try {
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
            list = noticeDao.getNotices(page, field, query);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 글 상세보기 서비스
    public Notice noticesDetail(String seq) {
        Notice notice = null;
        try {
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
            notice = noticeDao.getNotice(seq);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return notice;
    }

    // 글 쓰기 서비스
    public String noticeReg(Notice n, HttpServletRequest request) {
        String filename = n.getFile().getOriginalFilename();
        String path = request.getServletContext().getRealPath("/customer/upload");
        String fpath = path + "\\" + filename;
        System.out.println(fpath);

        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(fpath);
            fs.write(n.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fs != null) fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        n.setFileSrc(filename);

        try {
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
            noticeDao.insert(n);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:notice.do";
    }

    // 글 수정하기 서비스
    public Notice noticeEdit(String seq) {
        Notice notice = null;
        try {
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
            notice = noticeDao.getNotice(seq);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return notice;
    }

    // 글 수정 처리 서비스
    public String noticeEdit(Notice n, HttpServletRequest request) {
        String filename = n.getFile().getOriginalFilename();
        String path = request.getServletContext().getRealPath("/customer/upload");
        String fpath = path + "\\" + filename;
        System.out.println(fpath);

        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(fpath);
            fs.write(n.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fs != null) fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        n.setFileSrc(filename);

        try {
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
            noticeDao.update(n);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:noticeDetail.do?seq=" + n.getSeq();
    }

    // 글 삭제 서비스
    public String noticeDel(String seq) {
        try {
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
            noticeDao.delete(seq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:notice.do";
    }
}