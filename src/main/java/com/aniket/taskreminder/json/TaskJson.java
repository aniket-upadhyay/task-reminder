package com.aniket.taskreminder.json;

import com.aniket.taskreminder.entity.TaskEntity;
import com.aniket.taskreminder.util.AppUtils;
import com.fasterxml.jackson.annotation.JsonInclude;

public class TaskJson {
	
	private Integer id;
	private String task;
	private String startTime;
	private String endTime;
	private Integer reminderFlag;
	private String fcmToken;
	private String createdDate;
	 
	
	public TaskJson() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public TaskJson(TaskEntity taskEntity) {
		this.id = taskEntity.getId();
		this.task = taskEntity.getTask();
		this.startTime = AppUtils.convertDateToString(taskEntity.getStartTime(), "yyyy-MM-dd HH:mm:ss");
		this.endTime = AppUtils.convertDateToString(taskEntity.getEndTime(), "yyyy-MM-dd HH:mm:ss");;
		this.reminderFlag = taskEntity.getReminderFlag();
		this.fcmToken = taskEntity.getFcmToken();
		this.createdDate = AppUtils.convertDateToString(taskEntity.getCreatedDate(), "yyyy-MM-dd");
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public Integer getReminderFlag() {
		return reminderFlag;
	}


	public void setReminderFlag(Integer reminderFlag) {
		this.reminderFlag = reminderFlag;
	}

	
	public String getFcmToken() {
		return fcmToken;
	}


	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	


}
