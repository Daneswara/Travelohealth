package com.masbie.travelohealth.Login;

public class Token{
	private String refresh;
	private String token;

	public void setRefresh(String refresh){
		this.refresh = refresh;
	}

	public String getRefresh(){
		return refresh;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}
