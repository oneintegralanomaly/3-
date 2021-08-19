package controller;

import com.jfoenix.controls.JFXButton;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class RegisterController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private JFXButton loginButton1;
    
    @FXML
    private TextField name;

    @FXML
    private TextField id;

    @FXML
    private PasswordField pwd;
    
    @FXML
    private JFXButton membersBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	membersBtn.setOnAction(e->membersAction(e));
    }

    @FXML
    private void open_login_form(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = loginButton1.getScene();

        root.translateYProperty().set(scene.getHeight());

        StackPane parentContainer = (StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(container);
        });
        timeline.play();

    }
    public void membersAction(ActionEvent event){
		String uName = name.getText();
	    String uId = id.getText();
	    String uPwd = pwd.getText();  
	 
		  String jdbcUrl = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&serverTimezone=Asia/Seoul";
		
		  String dbId = "root";
		
		  String dbPw = "1234";	  
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  
		  String sql = "";
		  int num = 0;
		  new RegisterController();
		  String name = uName;
		  String id = uId;
		  String pw = uPwd;
		  
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
