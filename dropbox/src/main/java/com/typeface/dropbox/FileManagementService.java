package com.typeface.dropbox;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sun.jersey.core.header.FormDataContentDisposition;


public class FileManagementService {
	
	private static final String FILE_STORAGE_DIR = "/Users/ashu/Documents/Dropbox";
	private static Path uploadPath = Paths.get(FILE_STORAGE_DIR);
	
	public FileManagementService() {
		setupFileDataBase();
	}

	
	private void setupFileDataBase() {
		try {
			if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 
	 * @param fileInputStream
	 * @param fileMetaData
	 * @return
	 */
    public static FileMetaDataInfo uploadFile(InputStream fileInputStream, FormDataContentDisposition fileMetaData) {
    	FileMetaDataInfo fileInfo = null;
        try {
            
        	String fileUniqueID = UUID.randomUUID().toString();
            fileInfo = createFileInFileDB(fileInputStream, fileMetaData, fileUniqueID);
            
            persistFileMetaData(fileInfo);
        }catch(Exception ex) {
            System.out.println("Exception occured while uploading file.");
        }
        return fileInfo;
    }
    
        
    public static File getFile(String id) {
    	File fileObject = null;
    	try {
    		FileManagementDAOService fileManagementDAOService = new FileManagementDAOService();
    		FileMetaDataInfo fileInfo = fileManagementDAOService.getFileMetaDataInfo(id);
    		
    		if(fileInfo != null) {
    			System.out.println("File found");
    			fileObject = uploadPath.resolve(fileInfo.getFileID() + "_" + fileInfo.getFileName()).toFile();
    			if(!fileObject.exists()) {
    				throw new Exception();
    			}
    		}
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return fileObject;
    }
    
    public static List<FileMetaDataInfo> getAllFiles(){
    	List<FileMetaDataInfo> fileInfos = new ArrayList<FileMetaDataInfo>();
    	
    	try {
    		FileManagementDAOService fileManagementDAOService = new FileManagementDAOService();
    		fileInfos = fileManagementDAOService.getFilesMetaDataInfo();
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return fileInfos;
    }
    
    public static boolean deleteFile(String fileID) {
    	boolean isFileDeleted = false;
    	try {
    		FileManagementDAOService fileManagementDAOService = new FileManagementDAOService();
    		FileMetaDataInfo fileInfo = fileManagementDAOService.getFileMetaDataInfo(fileID);
    		
    		if(fileInfo != null) {
    			// Update the fileStatus in Database and Delete the file content from the file system storage
    			isFileDeleted =  fileManagementDAOService.deleteFile(fileID);
    			if(isFileDeleted) {
    				isFileDeleted = deleteFileFromFileDB(fileInfo);
    			}
    		}
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return isFileDeleted;
    }
    
    private static boolean deleteFileFromFileDB(FileMetaDataInfo fileInfo){
    	
    	boolean isFileDeleted = false;
    	
    	try {
    		Path filePath = uploadPath.resolve(fileInfo.getFileID() + "_" + fileInfo.getFileName());
    		isFileDeleted = Files.deleteIfExists(filePath);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return isFileDeleted;
    }
    
    
    public static FileMetaDataInfo updateFile(InputStream fileInputStream, FormDataContentDisposition fileMetaData, String fileID) {
    	FileMetaDataInfo fileInfo = null;
    	
    	try {
    		/** Verify if the file exists in the Database **/
    		FileManagementDAOService fileManagementDAOService = new FileManagementDAOService();
    		fileInfo = fileManagementDAOService.getFileMetaDataInfo(fileID);
    		
    		if(fileInfo != null) {
    			/** 
					1 - Remove the old file from the fileDatabase
					2 - Add new file into the fileDatabase
					3 - Update the file Meta Data in the Database  
				**/
    			boolean isFileDeleted = deleteFileFromFileDB(fileInfo);
    			
    			if(isFileDeleted) {
    				System.out.println("File deleted");
    				String fileUniqueID = fileInfo.getFileID();
    				fileInfo = createFileInFileDB(fileInputStream, fileMetaData, fileUniqueID);
    				
    				System.out.println("File Created");

    				if(fileInfo != null) {
    	    			fileInfo = fileManagementDAOService.updateFileMetaData(fileInfo);
    				}
    			}
    			
    		}
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return fileInfo;
    }
    
    private static FileMetaDataInfo createFileInFileDB(InputStream fileInputStream, FormDataContentDisposition fileMetaData, String fileUniqueID){
    	FileMetaDataInfo fileInfo = null;
    	
    	try {
    		String fileName = fileMetaData.getFileName();

            Path filePath = uploadPath.resolve(fileUniqueID + "_" + fileName);
            Files.copy(fileInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            
            fileInfo = getFileMetaDataInfo(fileName, fileUniqueID, filePath);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return fileInfo;
    }
    
    private static FileMetaDataInfo getFileMetaDataInfo(String fileName, String fileUniqueID, Path filePath){
 
    	FileMetaDataInfo fileMetaDataInfo = new FileMetaDataInfo();
        fileMetaDataInfo.setFileName(fileName);
        fileMetaDataInfo.setFileID(fileUniqueID);
        fileMetaDataInfo.setFileSize(getFileSize(filePath));
    	
    	return fileMetaDataInfo;
    }
    
    /**
     * 
     * @param file
     * @return
     */
    private static double getFileSize(Path file) {
    	long size = file.toFile().length();
    	double mb = 1024*1024;
    	double fileSize = (double)size / mb;
    	DecimalFormat df_obj = new DecimalFormat("#.#####");
    	fileSize = Double.parseDouble(df_obj.format(fileSize));
    	
    	return fileSize;
    }
    
    /**
     * 
     * @param fileName
     * @param fileUniqueID
     * @param filePath
     */
    private static void persistFileMetaData(FileMetaDataInfo fileMetaDataInfo) {
    	if(fileMetaDataInfo != null) {
    		FileManagementDAOService fileManagementDAOService = new FileManagementDAOService();
            fileManagementDAOService.persistFileMetaDataInfo(fileMetaDataInfo);
    	}
    }
}
