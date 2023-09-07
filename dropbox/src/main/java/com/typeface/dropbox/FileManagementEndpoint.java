
package com.typeface.dropbox;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;
import com.typeface.dropbox.ResponseEntity.FileMetaDataResponseEntity;
import com.typeface.dropbox.ResponseEntity.FileNegativeResponseInfo;
import com.typeface.dropbox.ResponseEntity.FilesResponseInfo;
import com.sun.jersey.core.header.FormDataContentDisposition;

/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/files")
public class FileManagementEndpoint {
    
    
	@POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON })
    public Response uploadFileEndpoint(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) {
		FileMetaDataResponseEntity responseEntity = null;
		
		try {
			if(!fileMetaData.getFileName().isEmpty()) {
				FileMetaDataInfo fileInfo = FileManagementService.uploadFile(fileInputStream, fileMetaData);
				
				if(fileInfo != null) {
					responseEntity = FileManagementUtils.getResponseEntityFromMetaDataInfo(fileInfo);
				}else {
					throw new RuntimeException();
				}
			}else {
				throw new RuntimeException();
			}
		}catch(Exception ex) {
			FileNegativeResponseInfo responseInfo = new FileNegativeResponseInfo("Exception occured while uplading file.", 500);
			return Response.status(500).entity(responseInfo).build();
		}
		
        return Response.status(200).entity(responseEntity).build();
    }
	
	
	@PUT
    @Path("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateFileEndpoint(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData, @PathParam("id") String fileID) {
		
		FileMetaDataResponseEntity responseEntity = null;

		try {
			if(!fileMetaData.getFileName().isEmpty()) {
				FileMetaDataInfo fileMetaDataInfo = FileManagementService.updateFile(fileInputStream, fileMetaData, fileID);
				
				if(fileMetaDataInfo == null) {
					throw new RuntimeException();
				}else {
					responseEntity = FileManagementUtils.getResponseEntityFromMetaDataInfo(fileMetaDataInfo);
				}
			}else {
				throw new RuntimeException();
			}
		}catch(Exception ex) {
			FileNegativeResponseInfo responseInfo = new FileNegativeResponseInfo("Exception occured while updaing file.", 500);
			return Response.status(500).entity(responseInfo).build();
		}
		
		return Response.status(200).entity(responseEntity).build();
		
	}
	
	@GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@PathParam("id") String fileID) {
		File file = null;
        try {
        	file = FileManagementService.getFile(fileID);
        	
        	if(file == null) {
        		throw new RuntimeException();
        	}
        }catch(Exception ex) {
        	return Response.status(204).build();
        }
        
        return Response.status(200).entity(file).header("Content-Disposition", "attachment; filename=" + file.getName()).build();
    }
	
	@GET
    @Path("")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFiles() {
		FilesResponseInfo responseEntity = null;
		
		try {
			List<FileMetaDataInfo> listOfAvailableFiles = new ArrayList<FileMetaDataInfo>();
			listOfAvailableFiles = FileManagementService.getAllFiles();
			
			responseEntity = new FilesResponseInfo();
			responseEntity.setFileInfos(listOfAvailableFiles);
		}catch(Exception ex) {
			FileNegativeResponseInfo responseInfo = new FileNegativeResponseInfo("Exception occured while listing files.", 500);
			return Response.status(500).entity(responseInfo).build();
		}
		
		return Response.ok(responseEntity).build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteFiles(@PathParam("id") String fileID) {
		FileNegativeResponseInfo response = new FileNegativeResponseInfo();
		try {
			boolean isFileDeleted = FileManagementService.deleteFile(fileID);
			if(isFileDeleted) {
				response.setResponseCode(200);
				response.setResponseMessage("File Deleted SuccessFully.");
			}else {
				response.setResponseCode(500);
				response.setResponseMessage("Unable to Delete File.");
			}
		}catch(Exception ex) {
			FileNegativeResponseInfo responseInfo = new FileNegativeResponseInfo("Exception occured while deleting file.", 500);
			return Response.status(500).entity(responseInfo).build();
		}
		
		return Response.status(200).entity(response).build();
	}
	
}
