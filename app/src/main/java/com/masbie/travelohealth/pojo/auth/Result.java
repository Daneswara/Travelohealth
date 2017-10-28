package com.masbie.travelohealth.pojo.auth;

public class Result{
	private String coupon;

	public void setCoupon(String coupon){
		this.coupon = coupon;
	}

	public String getCoupon(){
		return coupon;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"coupon = '" + coupon + '\'' + 
			"}";
		}
}
