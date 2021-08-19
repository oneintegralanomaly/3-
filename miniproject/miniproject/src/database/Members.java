package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import controller.RegisterController;
public class Members {
	RegisterController mc = new RegisterController();
 public static void menu() {  
	
	  String jdbcUrl = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&serverTimezone=Asia/Seoul";
	  
	  String dbId = "root";
	 
	  String dbPw = "1234";	  
	  Connection conn = null;
	  PreparedStatement pstmt = null;
	  
	  String sql = "";
	  int num = 0;
	  new RegisterController();
	  String name = null;
	  String id = null;
	  String pw = null;
	  
	  try {
	   Class.forName("com.mysql.cj.jdbc.Driver");
	
	   conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);	     
	     sql = "INSERT INTO bingo.members VALUES(?,?,?,?)";
	     pstmt = conn.prepareStatement(sql);
	     pstmt.setInt(1, num);
	     pstmt.setString(2, name);
	     pstmt.setString(3, id);
	     pstmt.setString(4, pw);
	     pstmt.executeUpdate();	     
	  }
 catch (Exception e) {
	   e.printStackTrace();
	  } finally{
	   if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
	   if(conn!=null) try{conn.close();}catch(SQLException ex){}
	  }
 }
}