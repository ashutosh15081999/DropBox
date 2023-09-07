package com.typeface.dropbox.ResponseEntity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileNegativeResponseInfo {
	private String responseMessage;
	private int responseCode;
	
	public FileNegativeResponseInfo() {
		
	}
	
	public FileNegativeResponseInfo(String responseMessage, int responseCode) {
		this.responseMessage = responseMessage;
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	
	
}
