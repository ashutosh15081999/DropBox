<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dropbox</title>
    <link rel="stylesheet" href="typeface/css/update.css">
    
    <script type="text/javascript">
      	function sendPutRequest(){
        	const fileID = document.getElementById("fileID").value;
        	const apiEndpoint = "http://localhost:8080/dropbox/api/files/" + fileID;
       		
       		const fileInput = document.getElementById('file');
       		const formData = new FormData();
       		formData.append('file', fileInput.files[0]);
        		
        	fetch(apiEndpoint, {
        	    method: 'PUT',
       		    body: formData,
       		})
       		.then(response => {
       			console.log(response);
       		   	if (!response.ok) {
        		      throw new Error('Response was not ok');
        		}
        	    return response.json();
        	})
       		.then(responseData => {
       		  	console.log('PUT request successful:', responseData);
       		 	addRowsToTable(responseData)
       		})
       		.catch(error => {
       		    	console.error('Error:', error);
       		});
        	
        	
        }
      	
      	function addRowsToTable(data) {
      		let existingDiv = document.getElementById("primaryDiv");
    		existingDiv.style.display = "none";
    		
    		let newDiv = document.getElementById("secondaryDiv");
    		newDiv.style.display = "block";
    		
      		let table = document.getElementById("tableBody");
      		table.rows[1].cells[1].innerHTML = data.fileID;
      		table.rows[2].cells[1].innerHTML = data.fileName;
      		table.rows[3].cells[1].innerHTML = data.fileSize;
      		table.rows[4].cells[1].innerHTML = data.createdTime;
      		table.rows[5].cells[1].innerHTML = data.lastModifiedTime;	
    	}
  	</script>
</head>
<body>
    <header>
        <h1>TypeFace DropBox</h1>
        <nav>
            <ul>
                <li><a href="index.jsp">Home</a></li>
            </ul>
        </nav>
    </header>
    <div id="primaryDiv" class="container">
        <h1>Update a File</h1>
        <p>Choose a file from your device to update.</p>
        
        <input type="text" id="fileID" placeholder="Enter Unique fileId" class="text-box"><br>
        <input type="file" name="file" id="file" class="input-file">
        <button type="submit" class="btn submit-btn" onclick="sendPutRequest()">Upload</button>
    </div>
    
    <div id="secondaryDiv" style="display:none" class="container">
    	<h1>Updated File Info</h1>
    	<table id="tableBody">
        	<tr>
            	<th>Attribute</th>
            	<th>Value</th>
        	</tr>
        	<tr>
            	<td class="key">fileID</td>
            	<td></td>
        	</tr>
        	<tr>
            	<td class="key">fileName</td>
            	<td></td>
        	</tr>
        	<tr>
            	<td class="key">fileSize</td>
            	<td></td>
        	</tr>
        	<tr>
            	<td class="key">createdTime</td>
            	<td></td>
        	</tr>
        	<tr>
            	<td class="key">lastModifiedTime</td>
            	<td></td>
        	</tr>
    	</table>
    	
    </div>
</body>
</html>