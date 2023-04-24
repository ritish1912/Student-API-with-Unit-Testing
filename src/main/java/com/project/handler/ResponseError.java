package com.project.handler;

import java.util.List;

public class ResponseError {
	private String info;
	private List<String> messages;

	

	public ResponseError(String info, List<String> messages) {
		super();
		this.info = info;
		this.messages = messages;
	}

	public String getInfo() {
		return info;
	}



	public List<String> getMessages() {
		return messages;
	}

}

