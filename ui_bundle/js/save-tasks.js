function saveTasks(){
		
	
	var startDateTime = new Date($('.startTime').val());
	var endDateTime = new Date($('.endTime').val());
	
	var selectedStartMonth = startDateTime.getMonth() + 1;
	var selectedEndMonth = startDateTime.getMonth() + 1;
	
	var teskDescJson = $(".taskDesc").val();
	var startDateTimeJson = startDateTime.getFullYear() + "-" + selectedStartMonth + "-" + startDateTime.getDate() + " " + startDateTime.getHours() + ":" + startDateTime.getMinutes() + ":" + startDateTime.getSeconds();
	var endDateTimeJson = endDateTime.getFullYear() + "-" + selectedEndMonth + "-" + endDateTime.getDate() + " " + endDateTime.getHours() + ":" + endDateTime.getMinutes() + ":" + endDateTime.getSeconds();
	var createdDateJson = startDateTime.getFullYear() + "-" + selectedStartMonth + "-" + startDateTime.getDate();
	
	var fcmToken = localStorage.getItem('fcmToken');

	var taskRequestJson = {
		"task" : teskDescJson,
		"startTime" : startDateTimeJson,
		"endTime" : endDateTimeJson,
		"reminderFlag" : 0,
		"fcmToken" : fcmToken,
		"createdDate" : createdDateJson
	};
	
	$.ajax({

	    url : 'http://localhost:8080/taskReminder/saveTasks',
	    type : 'POST',
	    data : JSON.stringify(taskRequestJson),
	    dataType:'json',
	    contentType: "application/json",
	    success : function(data) {  
	    	var taskJson = data.taskJson;
	    	addTaskDetails(taskJson.id, taskJson.task, taskJson.startTime, taskJson.endTime);
	    	$(".tableData").show();
	    	$(".noData").hide();
	    },
	    error : function(request,error)
	    {
	        
	    }
	});
	
}

function addTaskDetails(taskId, task, startTime, endTime) {
	$("#tableBody").append(
		'<tr role="row" class="odd">'+
		'<td class="taskId" value="'+ taskId +'" style="display:none;">'+ taskId +'</td>'+
		'<td class="taskRow" value="'+ task +'">'+ task +'</td>'+
		'<td class="startTimeRow" value="'+ startTime +'">'+ startTime +'</td>'+
		'<td class="endTimeRow" value="'+ endTime +'">'+ endTime +'</td>'+
		'<td><i class="fa fa-trash deleteIcon"></i></td>'+
		'</tr>'
	);
	
}

function uploadTaskFile() {
	
	var formData = new FormData($('.fileUpload')[0]);
  
    $.ajax({
        url: "/taskReminder/uploadFileTasks",
        type: "POST",
        data: formData,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function (res) {
            console.log(res);
        },
        error: function (err) {
            console.error(err);
        }
    });
}

