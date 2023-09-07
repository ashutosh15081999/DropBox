<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DropBox</title>
    <link rel="stylesheet" href="typeface/css/download.css">
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
    <div class="container">
        <h2>Welcome to Typeface Dropbox!</h2>
        <p>Typeface Dropbox's is designed to be seamless, user-friendly, and highly accessible, 
        making it a popular choice for individuals, teams, and businesses 
        looking for efficient cloud-based file storage and sharing solutions.</p>
        <form method="get">
  			<input type="text" id="fileID" placeholder="Enter Unique fileId" class="text-box"><br>
  			<button type="submit" class="btn submit-btn" onclick="createUrl()">Download</button>
  			
		</form> 
		
		<script type="text/javascript">
    		function createUrl(){
    			const fileID = document.getElementById("fileID").value;
    			const apiEndpoint = "http://localhost:8080/dropbox/api/files/" + fileID;
    			console.log(apiEndpoint);
	    	 	window.open(
    					apiEndpoint,
    					'_blank' // <- This is what makes it open in a new window.
    			); 
    			
    		}
    		
    		
    </script>
    </div>
</body>
</html>