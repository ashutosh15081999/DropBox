package com.typeface.dropbox;

import com.typeface.dropbox.ResponseEntity.FileMetaDataResponseEntity;

public class FileManagementUtils {
	
	public FileManagementUtils() {
		
	}
	
	public static FileMetaDataResponseEntity getResponseEntityFromMetaDataInfo(FileMetaDataInfo fileMetaDataInfo) {
		FileMetaDataResponseEntity responseEntity = null;
		
		if(fileMetaDataInfo != null) {
			responseEntity = new FileMetaDataResponseEntity();
			responseEntity.setFileID(fileMetaDataInfo.getFileID());
			responseEntity.setFileName(fileMetaDataInfo.getFileName());
			responseEntity.setFileSize(fileMetaDataInfo.getFileSize());
			responseEntity.setCreatedTime(fileMetaDataInfo.getCreatedTime());
			responseEntity.setLastModifiedTime(fileMetaDataInfo.getLastModifiedTime());
		}
		
		return responseEntity;
	}
}
