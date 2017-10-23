package com.masbie.travelohealth.ObjectDoctor;

import java.util.List;

public class ResultItem{
	private List<ServiceItem> service;
	private String id;
	private String username;

	public void setService(List<ServiceItem> service){
		this.service = service;
	}

	public List<ServiceItem> getService(){
		return service;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}