<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DropBox</title>
    <link rel="stylesheet" href="typeface/css/delete.css">
    <script type="text/javascript">
    	function makeDeleteCall(){
    		const fileID = document.getElementById("fileID").value;
   			const apiEndpoint = "http://localhost:8080/dropbox/api/files/" + fileID;
   			
    		fetch(apiEndpoint, {
        	    method: 'DELETE', 
       		})
       		.then(response => {
       			
       		   	if (!response.ok) {
       		      	throw new Error('Network response was not ok');
       		   	}
       		   	
       		    return response.json();
       		})
       		.then(data => {
       			populateDeleteResponse(data);
       		})
        	.catch(error => {
       		    	console.error('Error:', error);
       		});	
   			
   		}
    	
    	function populateDeleteResponse(data){
    		let existingDiv = document.getElementById("primaryDiv");
    		existingDiv.style.display = "none";
    		
    		let newDiv = document.getElementById("secondaryDiv");
    		newDiv.style.display = "block";
    		
      		let secDivH2 = document.getElementById("deleteResponse");
      		secDivH2.innerHTML=data.responseMessage + "(Code:" + data.responseCode + ")";
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
        <h2>Welcome to Typeface Dropbox!</h2>
        <p>Typeface Dropbox's is designed to be seamless, user-friendly, and highly accessible, 
        making it a popular choice for individuals, teams, and businesses 
        looking for efficient cloud-based file storage and sharing solutions.</p>
        
  		<input type="text" id="fileID" placeholder="Enter Unique fileId" class="text-box"><br>
  		<button type="submit" class="btn submit-btn" onclick="makeDeleteCall()">Delete</button>
 
    </div>
    <div id="secondaryDiv" style="display:none" class="container">
        <h2 id="deleteResponse"></h2>
    </div>
</body>
</html>