package com.aniket.taskreminder.service;

import java.awt.AWTException;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.aniket.taskreminder.entity.TaskEntity;
import com.aniket.taskreminder.json.TaskJson;
import com.aniket.taskreminder.util.AppUtils;

@Service("TaskService")
public interface TaskService {
	
	public TaskEntity getTaskbyId(Integer taskId);
	
	public JSONObject saveTasks(TaskJson taskJson);

	public TaskEntity getTask(Date currentDate);
	
	public JSONObject triggerReminder(String reminderMessage) throws AWTException;

	public JSONObject updateReminderFlag(TaskEntity taskEntity);

	public void updateCronExpression() throws ConfigurationException;
	
	public JSONObject deleteTasks(Integer taskId);
	
	//public JSONObject extractExcelAndSave(File file);
	
	public JSONObject pushNotification(TaskEntity taskEntity) throws Exception;
	
}
