package com.poscodx.mysite.repository;

import com.poscodx.mysite.vo.GuestbookVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GuestbookRepository {
    private SqlSession sqlSession;

    public GuestbookRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<GuestbookVo> findAll() {

        List<GuestbookVo> list = sqlSession.selectList("guestbook.findAll");
        return list;
    }

    public GuestbookVo findByNo(Long no) {
        return sqlSession.selectOne("guestbook.findByNo", no);
    }

    public int deleteByNoAndPassword(Long no, String password) {
        return sqlSession.delete("guestbook.deleteByNoAndPassword", Map.of("no", no, "password", password));
    }

    public int insert(GuestbookVo vo) {
        return sqlSession.insert("guestbook.insert", vo);
    }
}