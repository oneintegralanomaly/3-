package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Client_Controller implements Initializable
{

	public static final String hName = "localhost"; // 호스트명
	public static final int portNum = 5001; // 포트 번호

	@FXML
	Button start;
	@FXML
	Button stop;
	@FXML
	Button sendButton;
	@FXML
	Button btnExit;
	@FXML
	Button cr1;
	@FXML
	Button cr2;
	@FXML
	Button cr3;
	@FXML
	TextField inputField;
	@FXML
	TextArea viewArea;
	
	@FXML
	ListView userList;
	
	// 입출력
	private ObjectInputStream objectInput; // to read from the socket
	private ObjectOutputStream objectOutput; // to write on the socket
	private Socket socket;

	@Override
	public void initialize(URL var1, ResourceBundle var2)
	{
		viewArea.setEditable(false);
		sendButton.setDisable(true);
	}

	public void runClient()
	{
		sendButton.setDisable(false);

		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					display("접속 시작");
					socket = new Socket();
					socket.connect(new InetSocketAddress(hName, portNum));

					String cSuccess = (socket.getInetAddress() + " : 접속 성공");
					display(cSuccess);

					objectInput = new ObjectInputStream(socket.getInputStream());
					objectOutput = new ObjectOutputStream(socket.getOutputStream());

				} catch (IOException e)
				{
					display("서버 통신 불가");
					if(!socket.isClosed())
					{
						pauseClient();
					}
					return;
				}
				recieveMsg();
			}
		};
		thread.start();
	}

	public void pauseClient()
	{
		try
		{ 
			display("클라이언트 종료");
			sendButton.setDisable(true);
			if(socket!=null && !socket.isClosed())
			{
				socket.close();
			}
		} catch (IOException e)
		{
		}
	}

	private void display(String msg)
	{
		viewArea.appendText(msg + "\n");
	}

	public void recieveMsg()
	{
		while (true)
		{
			try
			{
				String msg = (String) objectInput.readObject();
				viewArea.appendText(msg + "\n");

			} catch (Exception e)
			{
				pauseClient();
			}
		}
	}

	public void sendBtnAction()
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{

				try
				{
					ChatMsg msg = new ChatMsg(inputField.getText());
					objectOutput.writeObject(msg);
					inputField.setText("");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

	// 스타트 버튼
	public void startBtnAction()
	{
		runClient();
	}

	// 스탑 버튼
	public void stopBtnAction()
	{
		pauseClient();
	}

	// exit 버튼
	public void exitBtnAction()
	{
		System.exit(0);
	}

	public void cr1Action() throws Exception
	{
		URL url = new URL("https://news.naver.com");

		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
		String line;
		int check_line = 0;

		while ((line = reader.readLine()) != null)
		{
			if (line.contains("\"title\" : \""))
			{
				check_line = 1;
				if (check_line == 1)
				{
					String temp = line.split(":")[1];
					String temp2 = temp.substring(0,temp.length()-1);
					viewArea.appendText("Headline :" + temp2 + "\n");
					break;
				}
			}
		}
		reader.close();
	}
	
	public void cr2Action() throws Exception
	{

		URL url = new URL("https://weather.naver.com/today");

		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String line;

		while ((line = reader.readLine()) != null)
		{
			if (line.contains("<strong class=\"current\">"))
			{
				String temp = line.split("</span>")[1].split("<span class=\"degree\">")[0];

				String s = String.format("Current Temperature : 섭시 %s도 \n", temp);
				viewArea.appendText(s);
				break;
			}
		}
		reader.close();
	}
	
	public void cr3Action() throws Exception
	{
		URL url = new URL("http://ncov.mohw.go.kr/bdBoardList_Real.do");

		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String line;

		
		while ((line = reader.readLine()) != null)
		{
			if (line.contains("<p class=\"inner_value\">"))
			{
				String temp = line.split("<p class=\"inner_value\">")[1];
				String temp2 = temp.substring(0,7);
				
				viewArea.appendText("코로나19 금일 확진 소계 : " + temp2 + "\n");
				break;
			}
		}
		reader.close();
		
	}
	
	
	public void inputAction()
	{

	}

}