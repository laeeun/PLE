package dao;

import java.sql.*;
import java.util.ArrayList;
import dto.member;

public class member_repository {

    // 싱글턴: DAO 하나만 쓰도록
    private static member_repository repository = new member_repository();
    private member_repository() {}
    public static member_repository getInstance() {
        return repository;
    }

    // DB 연결 함수
    public Connection dbconn() throws Exception {
        String url = "jdbc:mysql://localhost:3306/member";
        String id = "root";
        String pw = "1234";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, id, pw);
    }

    // C - 회원 저장
    public void create(member mb) {
        try {
            Connection conn = dbconn();
            String sql = "insert into member values(?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mb.getId());
            pstmt.setString(2, mb.getPw());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // R - 전체 회원 불러오기
    public ArrayList<member> readall() {
        ArrayList<member> list = new ArrayList<>();
        try {
            Connection conn = dbconn();
            String sql = "select * from member";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                member mb = new member();
                mb.setId(rs.getString("id"));
                mb.setPw(rs.getString("pw"));
                list.add(mb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
 // UPDATE - 비밀번호 수정
    public void update(member mb) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "update member set pw=? where id=?";

        try {
            conn = dbconn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mb.getPw());
            pstmt.setString(2, mb.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ ONE - id로 회원 한 명 조회
    public member readone(String id) {
        member mb = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from member where id=?";

        try {
            conn = dbconn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                mb = new member();
                mb.setId(rs.getString("id"));
                mb.setPw(rs.getString("pw"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mb;
    }

}
