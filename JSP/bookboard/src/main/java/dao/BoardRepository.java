package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.BoardDTO;

public class BoardRepository {
	//싱글턴
    private static BoardRepository repository = new BoardRepository();
    private BoardRepository() {}
    public static BoardRepository getInstance() {
        return repository;
    }
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs=null;
    int limit = 5;
    
    
    // create

    //글의 총갯수 알아오는 함수
    public int getListCount(){
        int result = 0;

        String sql = "select count(*) from board";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) 
                result = rs.getInt(1);

        } catch (Exception ex) {
            System.out.println("getListCount() 에러: " + ex);
        }
        return result;
    }
    
    public void create(BoardDTO board) {
        try {
            conn = DBConnection.getConnection();

            String sql = "insert into board(id,name,subject,content,regist_day,hit,ip) values( ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getId());
            pstmt.setString(2, board.getName());
            pstmt.setString(3, board.getSubject());
            pstmt.setString(4, board.getContent());
            pstmt.setString(5, board.getRegist_day());
            pstmt.setInt(6, board.getHit());
            pstmt.setString(7, board.getIp());

            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("insertBoard() 에러 : " + ex);
        }
    }
    // read
    public ArrayList readall(int pageNum) {

        int start = (pageNum - 1) * limit;
        int index = start + 1;
        int total_record = getListCount();

        ArrayList allboard = new ArrayList();
        String sql = "select * from board ORDER BY num DESC";

        try {
            conn = DBConnection.getConnection();


            pstmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = pstmt.executeQuery();
            //
            while (rs.absolute(index)) {
                BoardDTO board = new BoardDTO();
                board.setNum(rs.getInt("num"));
                board.setId(rs.getString("id"));
                board.setName(rs.getString("name"));
                board.setSubject(rs.getString("subject"));
                board.setContent(rs.getString("content"));
                board.setRegist_day(rs.getString("regist_day"));
                board.setHit(rs.getInt("hit"));
                board.setIp(rs.getString("ip"));
                allboard.add(board);

                if (index < (start + limit) && index < total_record)
                    index++;
                else
                    break;
            }
        } catch (Exception ex) {
            System.out.println("getBoardList() 에러 : " + ex);
        }
        return allboard;
    }
    // update
    
    // delete
}
