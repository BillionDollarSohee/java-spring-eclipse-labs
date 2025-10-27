package bit.service;

import java.util.List;
import javax.naming.NamingException;
import jakarta.servlet.http.HttpServletRequest;

import bit.dao.BoardDao;
import bit.dto.Board;
import bit.dto.Reply;

public class BoardService {
    private static final BoardService instance = new BoardService();
    private final BoardDao dao; // DAO를 한번만 생성

    private BoardService() {
        try {
            dao = new BoardDao();
        } catch (Exception e) {
            throw new RuntimeException("BoardDao 생성 실패", e);
        }
    }

    public static BoardService getInBoardService() {
        return instance;
    }

    // --- 게시글 ---
    public int writeOk(Board boarddata) {
        return dao.writeok(boarddata);
    }

    public List<Board> list(int cpage, int pagesize) {
        return dao.list(cpage, pagesize);
    }

    public int totalBoardCount() {
        return dao.totalBoardCount();
    }

    public Board content(int idx) {
        return dao.getContent(idx);
    }

    public boolean addReadNum(String idx) {
        return dao.getReadNum(idx);
    }

    public int board_Delete(String idx, String pwd) {
        return dao.deleteOk(idx, pwd);
    }

    public int rewriteok(Board boarddata) {
        return dao.reWriteOk(boarddata);
    }

    public Board board_EditContent(String idx) {
        return dao.getEditContent(idx);
    }

    public int board_Edit(jakarta.servlet.http.HttpServletRequest req) {
        return dao.boardEdit(req);
    }

    // --- 댓글 ---
    public int replyWrite(int idx_fk, String writer, String userid, String content, String pwd) {
        return dao.replywrite(idx_fk, writer, userid, content, pwd);
    }

    public List<Reply> replyList(String idx_fk) {
        return dao.replylist(idx_fk);
    }

    public int replyDelete(String no, String pwd) {
        return dao.replyDelete(no, pwd);
    }

    // --- 작성자 중복 확인 ---
    public boolean isWriterAvailable(String writer) {
        return dao.isWriterAvailable(writer);
    }
}
