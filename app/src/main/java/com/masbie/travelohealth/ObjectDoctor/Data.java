package com.masbie.travelohealth.ObjectDoctor;

import java.util.List;

//data
public class Data{
	private List<ResultItem> result;
	private int status;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}