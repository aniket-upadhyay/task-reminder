package com.aniket.taskreminder.dao;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.aniket.taskreminder.entity.TaskEntity;

@Repository("TaskDao")
public interface TaskDao {
	
	public TaskEntity getTaskById(Integer taskId);
	
	public JSONObject saveTasks(TaskEntity taskEntity);

	public TaskEntity getTask(Date currentDate);

	public JSONObject updateReminderFlag(TaskEntity taskEntity);

	public JSONObject deleteTasks(TaskEntity taskEntity);

}
