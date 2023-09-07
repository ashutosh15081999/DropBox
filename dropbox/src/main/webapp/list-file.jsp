<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DropBox</title>
    <link rel="stylesheet" href="typeface/css/list.css">
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
    <div id="dataContainer"></div>
    <div id="primaryDiv"  class="container">
        <h2>Welcome to Typeface Dropbox!</h2>
        <p>Typeface Dropbox's is designed to be seamless, user-friendly, and highly accessible, 
        making it a popular choice for individuals, teams, and businesses 
        looking for efficient cloud-based file storage and sharing solutions.</p>
     	<button type="submit" class="btn submit-btn" onClick="getApi()">List Valid Files</button>  	
    </div>
    
    <div id="secondaryDiv" style="display:none" class="container">
        <table id="myTable" class="styled-table">
  			<thead>
    			<tr>
      				<th>FileID</th>
      				<th>FileName</th>
      				<th>FileSize</th>
      				<th>DownloadLink</th>
    			</tr>
  			</thead>
  			<tbody id="tableBody">
    			<!-- Rows will be added here dynamically -->
  			</tbody>
		</table>
    </div>
    
    <script type="text/javascript">
        	function getApi(){
        		let existingDiv = document.getElementById("primaryDiv");
        		existingDiv.style.display = "none";
        		
        		let newDiv = document.getElementById("secondaryDiv");
        		newDiv.style.display = "block";
        		
        		fetch('http://localhost:8080/dropbox/api/files')
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP Error! Status: ${response.status}`);
                    }
                    
                    return response.json(); // Parse JSON response
                })
                .then(data => {
                	addRowsToTable(data.fileInfos);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        	}
        	
        	function addRowsToTable(data) {
        		console.log(data);
        		const tableBody = document.getElementById('tableBody');

        		  	// Loop through the data and create a row for each item
        			data.forEach(item => {
        				console.log(item);
        		    	const row = tableBody.insertRow();
        		    	const cell1 = row.insertCell(0);
        		    	const cell2 = row.insertCell(1);
        		    	const cell3 = row.insertCell(2);
        		    	const cell4 = row.insertCell(3);

        		    	// Populate the cells with data
        		    	cell1.textContent = item.fileID;
        		    	cell2.textContent = item.fileName;
        		    	cell3.textContent = item.fileSize;
        		    	let button = document.createElement('button');
        		    	button.innerText = "Download";
        		    	button.addEventListener("click", function(){
        		    		const apiEndpoint = "http://localhost:8080/dropbox/api/files/" + item.fileID;
        	    			console.log(apiEndpoint);
        	    			window.open(
        	    					apiEndpoint,
        	    					'_blank' // <- This is what makes it open in a new window.
        	    			);
        		    	});
        		    	cell4.append(button);
        		});
        	}
        	
        	function downloadFile(fileID){
    			const apiEndpoint = "http://localhost:8080/dropbox/api/files/" + fileID;
    			console.log(apiEndpoint);
    			window.open(
    					apiEndpoint,
    					'_blank' // <- This is what makes it open in a new window.
    			);
    		}

        	
    	</script>
</body>
</html>