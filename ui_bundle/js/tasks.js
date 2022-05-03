$(document).ready(function(){
	//$("#saveButton").prop('disabled',true);
	$(".tableData").hide();
	$("#addTasksButton").prop('disabled',true);

    
	$("#taskDescId").blur(function(){
		var taskDescId = $(".taskDesc").val();
		var startTimeId = $(".startTime").val();
		var endTimeId = $(".endTime").val();
		
		if(taskDescId != "" && startTimeId != "" && endTimeId!="")
			$("#addTasksButton").removeAttr('disabled');
		else
			$("#addTasksButton").attr('disabled','disabled');
	});
	
	$("#startTimeId").blur(function(){
		var taskDescId = $(".taskDesc").val();
		var startTimeId = $(".startTime").val();
		var endTimeId = $(".endTime").val();
		
		if(taskDescId != "" && startTimeId != "" && endTimeId!="")
			$("#addTasksButton").removeAttr('disabled');
		else
			$("#addTasksButton").attr('disabled','disabled');
	});
	
	$("#endTimeId").blur(function(){
		var taskDescId = $(".taskDesc").val();
		var startTimeId = $(".startTime").val();
		var endTimeId = $(".endTime").val();
		
		if(taskDescId != "" && startTimeId != "" && endTimeId!="")
			$("#addTasksButton").removeAttr('disabled');
		else
			$("#addTasksButton").attr('disabled','disabled');
	});
	
	
	
	$("#addTasksButton").click(function(){
		
		var task = $(".taskDesc").val();
		var startTime = $(".startTime").val();
		var endTime = $(".endTime").val();
		
		
		saveTasks();
		
		$(".taskDesc").val("");
		$(".startTime").val("");
		$(".endTime").val("");
		$("#addTasksButton").prop('disabled',true);
		
	});
	
	$(document).on("click", ".deleteIcon", function(){
		
		var taskId = this.parentElement.parentElement.firstElementChild.innerText;
		deleteTask(taskId);
		$(this).parent().parent().remove();
		var tableRowLength = $("#tableBody").children().length;
		if(tableRowLength == 0){
			$(".noData").show();
			$(".tableData").hide();
		}
	});
	
	
	
	
});





