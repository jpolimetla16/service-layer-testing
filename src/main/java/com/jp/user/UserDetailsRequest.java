package com.jp.user;

public class UserDetailsRequest {

	private String fullName;
	
	public UserDetailsRequest() {
		
	}

	public UserDetailsRequest(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
