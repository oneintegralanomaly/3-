package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{

	public static final int portNum = 5001; // 포트 번호
	public static final String hName = "localhost"; // 호스트
//	private static int userNum;
	
	private ExecutorService executorService;

	private ServerController serverController;

	private ServerSocket sSocket;

	Vector<Client> clients = new Vector<>();

	public boolean svStat;

	// FX GUI 위한 생성자 설정
	public Server(ServerController serverController)
	{
		this.serverController = serverController;
	}

	public void runServer()
	{

		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		try
		{
			sSocket = new ServerSocket();
			sSocket.bind(new InetSocketAddress(hName, portNum));
		} catch (IOException e1)
		{
			return;
		}
		
		Runnable runnable = new Runnable()
		{

			@Override
			public void run()
			{
				display("서버 기동");
				while (true)
				{
					try
					{
						display("접속자 기다리는 중");
						Socket socket = sSocket.accept(); // accept connection
						display("접속자 수락 " + socket.getRemoteSocketAddress());

						Client client = new Client(socket);
						clients.add(client);
						display("현재 연결 수 : " + clients.size());
					}

					catch (Exception e)
					{
						if (!sSocket.isClosed())
						{
							pauseServer();
						}
						break;
					}

				}
			}
		};
		executorService.submit(runnable);
	}

	public void pauseServer()
	{
		try
		{
			Iterator<Client> iterator = clients.iterator();
			while(iterator.hasNext())
			{
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			if(sSocket!=null && !sSocket.isClosed())
			{
				sSocket.close();
			}
			if(sSocket!=null && !executorService.isShutdown())
			{
				executorService.shutdown();
			}
			display("서버 종료");
		
		} catch (IOException e)
		{
		}
	}

	// GUI에 표시
	public void display(String msg)
	{
		serverController.appendMsg(msg);
	}

	class Client
	{
		Socket socket;

		ObjectInputStream objectInput;
		ObjectOutputStream objectOutput;
		
		ChatMsg msgM;
		
//		private int myNum;
	
		Client(Socket socket)
		{
			
//			myNum = ++userNum;
			this.socket = socket;
			try
			{
				// output 먼저
				objectOutput = new ObjectOutputStream(socket.getOutputStream());
				objectInput = new ObjectInputStream(socket.getInputStream());

				recieve();
			} catch (IOException e)
			{
				System.out.println("클라이언트 오류");
			}
		}

		public void recieve()
		{

			Runnable runnable = new Runnable()
			{

				@Override
				public void run()
				{
					while (true)
					{
						try
						{
							msgM = (ChatMsg) objectInput.readObject();

							String message = msgM.getMessage();

							for (Client client : clients)
							{
								client.send(message);
							}
						} catch (Exception e)
						{
							clients.remove(Client.this);
							String log = "통신 불가" + socket.getRemoteSocketAddress();
							display(log);
							try
							{
								socket.close();
							} catch (IOException e1)
							{
							}
						}
					}
				}
			};
			executorService.submit(runnable);
		}

		private void send(String msg)
		{
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						objectOutput.writeObject(msg);
					} catch (IOException e)
					{
						System.out.println("전송 오류");
					}
				}
			};
			executorService.submit(runnable);
		}
	}
}
