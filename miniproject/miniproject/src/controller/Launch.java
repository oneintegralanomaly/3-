package controller;

import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launch extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setTitle("Team G3");
        stage.show();
        
        stage.setOnCloseRequest(e -> { 
			e.consume(); 
			Alert closeConfiguration = new Alert(Alert.AlertType.CONFIRMATION); 
			closeConfiguration.setTitle("Bingo Game"); 
			closeConfiguration.setHeaderText("종료버튼입니다.");
			closeConfiguration.setContentText("게임을 정말 종료하시겠습까?");
			Optional<ButtonType> result = closeConfiguration.showAndWait(); 
			if (result.get().equals(ButtonType.OK)) { 
				
				System.exit(1); } 
			});
        
        
    }

    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
