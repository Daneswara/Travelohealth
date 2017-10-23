package com.masbie.travelohealth.ObjectRoom;

import java.util.List;

public class ResultItem{
	private List<ClassesItem> classes;
	private String name;
	private String id;

	public void setClasses(List<ClassesItem> classes){
		this.classes = classes;
	}

	public List<ClassesItem> getClasses(){
		return classes;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}
}