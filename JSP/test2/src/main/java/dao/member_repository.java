package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.member;

public class member_repository {
	//싱글턴 패턴 : member_repository의 객체를 1개만 유지할수있음. 남이 new못함
	private static member_repository repository = new member_repository();
	private member_repository() {}
	public static member_repository getInstance() {
		return repository;
	}
	
	//create
	public void save(member mb) {
		System.out.println(mb.toString());
		//Connection 객체 생성 : 데이터베이스연결 
		System.out.println("저장");
		Connection conn = null;
		String url="jdbc:mysql://localhost:3306/test_crud";
		String id="root";
		String pw="1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection(url,id,pw);
			//커넥션을 찍어서 연결되었는지 안되었는지 확인한다 null이면 연결안됨
			System.out.println("데이터 베이스 연결됨");
		}catch(Exception e) {}
		
		//PreparedStatement 객체생성 : 쿼리 전송 
		PreparedStatement pstmt =null;
		String sql = "insert into member values(?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mb.getId());
			pstmt.setString(2, mb.getPw());
			pstmt.executeUpdate();
		}catch(Exception e) {}
	}
	
	//read_all
	public ArrayList<member> readall(){
		ArrayList<member> allmember=new ArrayList<member>();
		ResultSet rs=null;
		//Connection 객체 생성 : 데이터베이스연결 
		Connection conn = null;
		String url="jdbc:mysql://localhost:3306/test_crud";
		String id="root";
		String pw="1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection(url,id,pw);
		}catch(Exception e) {}
		
		//PreparedStatement 객체생성 : 쿼리 전송 
		PreparedStatement pstmt =null;
		String sql = "select * from member";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String rsid = rs.getString("id"); //id컬럼
				String rspw = rs.getString("pw"); //pw컬럼
				//가져온 데이터를 묶음
				member mb = new member();
				mb.setId(rsid);
				mb.setPw(rspw);
				//묶은 데이터를 ArrayList에 저장
				allmember.add(mb);
			}
		}catch(Exception e) {}
		
		return allmember;
	}
	
	//read_one
	public member readone(String rid) {
		member mb = new member();
		ResultSet rs=null;
		//Connection 객체 생성 : 데이터베이스연결 
		Connection conn = null;
		String url="jdbc:mysql://localhost:3306/test_crud";
		String id="root";
		String pw="1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection(url,id,pw);
		}catch(Exception e) {}
		
		//PreparedStatement 객체생성 : 쿼리 전송 
		PreparedStatement pstmt =null;
		String sql = "select * from member where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String rsid = rs.getString("id"); //id컬럼
				String rspw = rs.getString("pw"); //pw컬럼
				//가져온 데이터를 묶음
				mb.setId(rsid);
				mb.setPw(rspw);
			}
		}catch(Exception e) {}
		
		return mb;
	}

	public void update(String rid, String rpw) {
		Connection conn = null;
		String url="jdbc:mysql://localhost:3306/test_crud";
		String id="root";
		String pw="1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection(url,id,pw);
			System.out.println("데이터 베이스 연결됨");
		}catch(Exception e) {}
		
		//PreparedStatement 객체생성 : 쿼리 전송 
		PreparedStatement pstmt =null;
		String sql = "update member set pw=?  where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rpw);
			pstmt.setString(2, rid);
			pstmt.executeUpdate();
		}catch(Exception e) {}
	}
	
	public void delmember(String rid) {
		Connection conn = null;
		String url="jdbc:mysql://localhost:3306/test_crud";
		String id="root";
		String pw="1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection(url,id,pw);
			System.out.println("데이터 베이스 연결됨");
		}catch(Exception e) {}
		
		//PreparedStatement 객체생성 : 쿼리 전송 
		PreparedStatement pstmt =null;
		String sql = "delete from member where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rid);
			pstmt.executeUpdate();
		}catch(Exception e) {}
	}

}
