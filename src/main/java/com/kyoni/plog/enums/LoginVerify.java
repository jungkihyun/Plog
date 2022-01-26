package com.kyoni.plog.enums;

public enum LoginVerify {
	
	OK("OK"),
	NICNAME("NICNAME"),
	EMAIL("EMAIL"),
	PW("PW");
	
	private String name;
	
	private LoginVerify() {
		
	}
	
	private LoginVerify(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
