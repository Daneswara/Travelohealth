package com.masbie.travelohealth.pojo.auth;

import java.util.List;

public class Message{
	private List<String> message;

	public void setMessage(List<String> message){
		this.message = message;
	}

	public List<String> getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Message{" + 
			"message = '" + message + '\'' + 
			"}";
		}
}