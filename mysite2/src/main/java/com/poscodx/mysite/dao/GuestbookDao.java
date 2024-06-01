package com.poscodx.mysite.dao;

import com.poscodx.mysite.vo.GuestbookVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestbookDao {
    private Connection getConnection() throws SQLException {
        Connection conn = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://192.168.35.96:3306/webdb?charset=utf8";
            conn = DriverManager.getConnection(url, "webdb", "webdb");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        }

        return conn;
    }

    public Boolean insert(GuestbookVo vo) {
        boolean result = false;

        String sql = "insert into guestbook(name, password, contents, reg_date) values(?,?,?, now())";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vo.getName());
            pstmt.setString(2, vo.getPassword());
            pstmt.setString(3, vo.getContents());

            int count = pstmt.executeUpdate();
            result = count == 1;

        } catch (SQLException e) {
            System.out.println("error: " + e);
        }
        return result;
    }

    public String getPassword(Long no) {
        String result = "";

        String sql = "select password from guestbook where no=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, no);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString("password");
                }
            } catch (SQLException e) {
                System.out.println("error: " + e);
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }
        return result;
    }

    public int delete(Long no, String password) {
        int result = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where no = ? and password = ?");
        ){
            pstmt.setLong(1, no);
            pstmt.setString(2, password);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error:" + e);
        }

        return result;
    }

    public List<GuestbookVo> findAll() {
        List<GuestbookVo> result = new ArrayList<>();

        String sql = "select no, name, password, contents, reg_date from guestbook";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()) {
                Long no = rs.getLong(1);
                String name = rs.getString(2);
                String password = rs.getString(3);
                String contents =  rs.getString(4);
                String regdate = rs.getString(5);

                GuestbookVo vo = new GuestbookVo();
                vo.setNo(no);
                vo.setName(name);
                vo.setPassword(password);
                vo.setContents(contents);
                vo.setRegDate(regdate);

                result.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }
        return result;
    }
}
