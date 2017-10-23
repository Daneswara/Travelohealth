package com.masbie.travelohealth.ObjectPelayanan;

import java.util.List;

public class ResultItem{
	private List<DoctorsItem> doctors;
	private String name;
	private String start;
	private String end;
	private String id;

	public void setDoctors(List<DoctorsItem> doctors){
		this.doctors = doctors;
	}

	public List<DoctorsItem> getDoctors(){
		return doctors;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setStart(String start){
		this.start = start;
	}

	public String getStart(){
		return start;
	}

	public void setEnd(String end){
		this.end = end;
	}

	public String getEnd(){
		return end;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}
}