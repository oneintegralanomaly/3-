package controller;

import java.io.Serializable;

public class ChatMsg implements Serializable
{
	
//	public static final int ALL = 0;
//	public static final int W = 1;
//	public static final int DEAD = 2;

	
	private String message;


	public ChatMsg(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
}
