package com.tymchak;

import org.atmosphere.cpr.BroadcasterFactory;

public class Main {

	private String m = "hello";

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public void sendMessage(){
		System.out.println("message send");
		BroadcasterFactory.getDefault().lookup("/chat", true).broadcast("hello world");
	}
	
}