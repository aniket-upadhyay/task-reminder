package com.aniket.taskreminder.entity;


import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

import com.aniket.taskreminder.json.TaskJson;
import com.aniket.taskreminder.util.AppUtils;

@Entity
@Table(name = "tasktable")
public class TaskEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "task")
	private String task;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "end_time")
	private Date endTime;
	
	@Column(name = "reminder_flag")
	private Integer reminderFlag;
	
	@Column(name = "fcm_token")
	private String fcmToken;
	
	@Column(name = "created_date")
	private Date createdDate;

	public TaskEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TaskEntity(TaskJson taskJson) {
		this.id = taskJson.getId();
		this.task = taskJson.getTask();
		this.startTime = AppUtils.convertStringToDate(taskJson.getStartTime(), "yyyy-MM-dd HH:mm");
		this.endTime = AppUtils.convertStringToDate(taskJson.getEndTime(), "yyyy-MM-dd HH:mm");
		this.reminderFlag = taskJson.getReminderFlag();
		this.fcmToken = taskJson.getFcmToken();
		this.createdDate = AppUtils.convertStringToDate(taskJson.getStartTime(), "yyyy-MM-dd");
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
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "TaskEntity [id=" + id + ", task=" + task + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", reminderFlag=" + reminderFlag + ", fcmToken=" + fcmToken + ", createdDate=" + createdDate + "]";
	}

	
}
