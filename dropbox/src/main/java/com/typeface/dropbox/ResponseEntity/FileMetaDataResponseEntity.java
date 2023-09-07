package com.typeface.dropbox.ResponseEntity;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include. NON_NULL)
@XmlRootElement
public class FileMetaDataResponseEntity {
	
	@JsonProperty("UniqueID")
	private String fileID;
	
	@JsonProperty("Name")
	private String fileName;
	
	@JsonProperty("Size")
	private double fileSize;
	
	@JsonProperty("CreatedTime")
	private String createdTime;
	
	@JsonProperty("LastModifiedTime")
	private String lastModifiedTime;
	
	public String getFileID() {
		return fileID;
	}
	
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public double getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
}
