package com.typeface.dropbox.ResponseEntity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.typeface.dropbox.FileMetaDataInfo;

@XmlRootElement
public class FilesResponseInfo {
	
	public FilesResponseInfo() {
		
	}
	
	@JsonProperty("fileInfos")
	private List<FileMetaDataInfo> fileInfos;

	public List<FileMetaDataInfo> getFileInfos() {
		return fileInfos;
	}

	public void setFileInfos(List<FileMetaDataInfo> fileInfos) {
		this.fileInfos = fileInfos;
	}
	
	
}
