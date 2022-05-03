function deleteTask(taskId){
	var result;
	var taskDeleteJson = {
		"taskId" : Number(taskId)
	};
	
	$.ajax({

	    url : '/taskReminder/deleteTasks',
	    type : 'POST',
	    data : taskId,
	    dataType:'json',
	    contentType: "application/json",
	    success : function(data) {  
	    	result = data.result;
	    },
	    error : function(request,error)
	    {
	        
	    }
	});

}

