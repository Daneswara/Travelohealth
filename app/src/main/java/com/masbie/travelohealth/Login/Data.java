package com.masbie.travelohealth.Login;

public class Data{
	private Message message;
	private int status;
	private Token token;

	public void setMessage(Message message){
		this.message = message;
	}

	public Message getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setToken(Token token){
		this.token = token;
	}

	public Token getToken(){
		return token;
	}
}
