package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private JFXButton Regist;
    @FXML
    private JFXButton loginButton;
  
    @FXML
    private TextField id;

    @FXML
    private PasswordField pwd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	  loginButton.setOnAction(e->loginAction(e));
    }

    @FXML
    private void open_registration_form(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));

        Scene scene = Regist.getScene();

        root.translateXProperty().set(scene.getWidth());

        StackPane parentContainer = (StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(container);
        });
        timeline.play();

    }
    public void loginAction(ActionEvent event){
		String uId = id.getText();
		String uPwd = pwd.getText();
		 
		  String jdbcUrl = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&serverTimezone=Asia/Seoul";
		  
		  String dbId = "root";
		  
		  String dbPw = "1234";	  
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  String sql = ""; 
		  
		  try {
			   Class.forName("com.mysql.cj.jdbc.Driver");
			   
			   conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);	     
			  
			     sql = "SELECT id, pw FROM bingo.members WHERE id = ? and pw = ?";
			     pstmt = conn.prepareStatement(sql);
			     pstmt.setString(1, uId);
			     pstmt.setString(2, uPwd);
			     rs = pstmt.executeQuery(); 
			     if(rs.next()){			     
			     if(rs.getString("id").equals(uId) && rs.getString("pw").equals(uPwd)){
			    	 Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("Client_Look.fxml"));
						Scene scene = new Scene(root);    	  
						primaryStage.setScene(scene); 
						primaryStage.show();
						
					Stage primaryStage1 = new Stage();
					Parent root1 = FXMLLoader.load(getClass().getResource("Server_Lock.fxml"));
					Scene scene1 = new Scene(root1);    	  
					primaryStage1.setScene(scene1); 
					primaryStage1.show();
			     } 
		     }
		  }
		 catch (Exception e) {
			   e.printStackTrace();
			  } finally{
			   if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
			   if(conn!=null) try{conn.close();}catch(SQLException ex){}
			  }
		  
	}
}
