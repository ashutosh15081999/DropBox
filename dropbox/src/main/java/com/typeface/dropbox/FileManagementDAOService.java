package com.typeface.dropbox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileManagementDAOService {
	
	private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/dropbox";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	private static final String FILEMETADATA = "FileMetaData";
	
	public FileManagementDAOService() {
		
	}
	
	public void persistFileMetaDataInfo(FileMetaDataInfo info) {
		Connection conn = null;
		try {
			conn = connect();
			
			StringBuilder queryString = new StringBuilder();
			queryString.append("insert into FileMetaData(fileID, fileName, fileSize) values (\"")
										.append(info.getFileID()).append("\", \"")
										.append(info.getFileName()).append("\", \"")
										.append(info.getFileSize()).append("\")");
			
			System.out.println(queryString);
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate(queryString.toString());
			
			if(result == 0) {
				throw new Exception();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disconnect(conn);
		}
	}
	
	public FileMetaDataInfo getFileMetaDataInfo(String id) {
		return getFileMetaDataInfo(id, null);
	}
	
	private FileMetaDataInfo getFileMetaDataInfo(String id, Connection conn) {
		FileMetaDataInfo retVal = null;
		boolean closeConnection = false;
		
		try {
			if(conn == null) {
				conn = connect();
				closeConnection = true;
			}
			
			String queryString = "select * from " + FILEMETADATA + " where fileID in (\"" + id + "\") and isDeleted = \"F\"";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);
			if (rs.next()) {
				retVal = new FileMetaDataInfo();
				System.out.println("Result Found");
                int colIndex = 1;
                retVal.setId(rs.getInt(colIndex++));
                retVal.setFileID(rs.getString(colIndex++));
                retVal.setFileName(rs.getString(colIndex++));
                retVal.setFileSize(rs.getDouble(colIndex++));
                retVal.setCreatedTime(rs.getTimestamp(colIndex++).toString());
                retVal.setLastModifiedTime(rs.getTimestamp(colIndex++).toString());
            }
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(closeConnection) {
				disconnect(conn);
			}
		}
		
		return retVal;
	}
	
	
	public List<FileMetaDataInfo> getFilesMetaDataInfo(){
		List<FileMetaDataInfo> fileInfos = new ArrayList<FileMetaDataInfo>();
		Connection conn = null;
		
		try {
			conn = connect();
			
			String queryString = "select * from " + FILEMETADATA + " where isDeleted = \"F\"";
			System.out.println(queryString);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);
			System.out.println("Query Executed");
			while (rs.next()) {
				FileMetaDataInfo fileInfo = new FileMetaDataInfo();
				System.out.println("Result Found");
                int colIndex = 1;
                fileInfo.setId(rs.getInt(colIndex++));
                fileInfo.setFileID(rs.getString(colIndex++));
                fileInfo.setFileName(rs.getString(colIndex++));
                fileInfo.setFileSize(rs.getDouble(colIndex++));
                fileInfo.setCreatedTime(rs.getTimestamp(colIndex++).toString());
                System.out.println(fileInfo.getCreatedTime());
                fileInfo.setLastModifiedTime(rs.getTimestamp(colIndex++).toString());
                System.out.println(fileInfo.getLastModifiedTime());
                
                fileInfos.add(fileInfo);
            }
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disconnect(conn);
		}
		
		return fileInfos;
	}
	
	public boolean deleteFile(String id) {
		boolean isFileDeleted = true;
		Connection conn = null;
		try {
			conn = connect();
			
			String queryString = "update " + FILEMETADATA + " set isDeleted = \"T\" where fileID in (\"" + id + "\")";
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate(queryString);
			
			if(result == 0) {
				isFileDeleted = false;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disconnect(conn);
		}
		
		return isFileDeleted;
	}
	
	public FileMetaDataInfo updateFileMetaData(FileMetaDataInfo fileMetaDataInfo) {
		FileMetaDataInfo fileInfo = null;
		Connection conn = null;
		try {
			conn = connect();
			
			String queryString = "update " + FILEMETADATA + " set fileName=?, fileSize=?, isDeleted=? where fileID=?";
			PreparedStatement stmt = conn.prepareStatement(queryString);
			
			int index = 1;
			stmt.setString(index++, fileMetaDataInfo.getFileName());
			stmt.setDouble(index++, fileMetaDataInfo.getFileSize());
			stmt.setString(index++, "F");
			stmt.setString(index++, fileMetaDataInfo.getFileID());
			
			int rs = stmt.executeUpdate();
			
			if(rs == 0) {
				throw new Exception();
			}
			
			fileInfo = getFileMetaDataInfo(fileMetaDataInfo.getFileID(), conn);
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disconnect(conn);
		}
		
		return fileInfo;
	}
	
	private Connection connect() {
		Connection conn = null;
		
		try {
            Class.forName(DATABASE_DRIVER);
            conn = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println(e);
        }
		
        return conn;
    }
	
	public void disconnect(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
}
