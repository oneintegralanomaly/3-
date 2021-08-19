package controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ServerController
{

	@FXML
	TextArea serverView;
	@FXML
	Button btnStart;
	@FXML
	Button btnStop;

	private Server server = new Server(this);
	
	public void startAction()
	{
		server.runServer();
	}
	
	public void stopAction()
	{
		server.pauseServer();
	}
	
	public void appendMsg(String msg)
	{
		serverView.appendText(msg + "\n");
	}
	
}
