package com.poscodx.mysite.repository;

import java.util.Map;

import com.poscodx.mysite.security.UserDetailsImpl;
import com.poscodx.mysite.vo.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private SqlSession sqlSession;

    public UserRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public UserDetailsImpl findByEmail2(String email) {
        return sqlSession.selectOne("user.findByEmail2", email);
    }

    public int insert(UserVo vo) {
        return sqlSession.insert("user.insert", vo);
    }

    public UserVo findByEmailAndPassword(String email, String password) {
        return sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));
    }

    public UserVo findByNo(Long no) {
        return sqlSession.selectOne("user.findByNo", no);
    }

    public int update(UserVo vo) {
        return sqlSession.update("user.update", vo);
    }

    public UserVo findByEmail(String email) {
        return sqlSession.selectOne("user.findByEmail", email);
    }
}