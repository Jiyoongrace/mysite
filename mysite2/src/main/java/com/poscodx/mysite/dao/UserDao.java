package com.poscodx.mysite.dao;

import com.poscodx.mysite.vo.UserVo;

import java.sql.*;

public class UserDao {
    private Connection getConnection() throws SQLException {
        Connection conn = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://192.168.0.195:3306/webdb?charset=utf8";
            conn = DriverManager.getConnection(url, "webdb", "webdb");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        }

        return conn;
    }

    public int insert(UserVo vo) {
        int result = 0;


        try (
                Connection conn = getConnection();
                PreparedStatement pstmt1 = conn.prepareStatement("insert into user values(null, ?, ?, password(?), ?, current_date())");
                PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
        ) {

            pstmt1.setString(1, vo.getName());
            pstmt1.setString(2, vo.getEmail());
            pstmt1.setString(3, vo.getPassword());
            pstmt1.setString(4, vo.getGender());
            result = pstmt1.executeUpdate();

            ResultSet rs = pstmt2.executeQuery();
            vo.setNo(rs.next() ? rs.getLong(1) : null);
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error:" + e);
        }

        return result;
    }

    public UserVo findByNoAndPassword(String email, String password) {
        UserVo result = null;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("select no, name from user where email =? and password = password(?)");
        ) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = new UserVo();
                    result.setNo(rs.getLong("no"));
                    result.setName(rs.getString("name"));
                    result.setEmail(rs.getString("email"));
                    result.setGender(rs.getString("gender"));
                    result.setJoinDate(rs.getString("join_date"));
                }
            } catch (SQLException e) {
                System.out.println("error: " + e);
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }


        return result;
    }

    public UserVo findByNo(Long no) {
        UserVo result = null;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("select name, email, password, gender from user where no =?");
        ) {
            pstmt.setLong(1, no);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = new UserVo();
                    result.setName(rs.getString("name"));
                    result.setEmail(rs.getString("email"));
                    result.setPassword(rs.getString("password"));
                    result.setGender(rs.getString("gender"));
                }
            } catch (SQLException e) {
                System.out.println("error: " + e);
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }
        return result;
    }

    public void updateUserNamePassword(String name, String password, String gender, Long no) {
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt1 = conn.prepareStatement("update user set name =?, gender =? where no =?");
                PreparedStatement pstmt2 = conn.prepareStatement("update user set name =?, password = password(?), gender =? where no =?");

        ) {
            if("".equals(password)) {
                pstmt1.setString(1, name);
                pstmt1.setString(2, gender);
                pstmt1.setLong(3, no);
                pstmt1.executeUpdate();
            } else {
                pstmt2.setString(1, name);
                pstmt2.setString(2, password);
                pstmt2.setString(3, gender);
                pstmt2.setLong(4, no);
                pstmt2.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }
    }
}
