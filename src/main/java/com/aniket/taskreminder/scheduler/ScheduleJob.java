package com.aniket.taskreminder.scheduler;

import java.awt.AWTException;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aniket.taskreminder.entity.TaskEntity;
import com.aniket.taskreminder.service.TaskService;
import com.aniket.taskreminder.util.AppUtils;

@Component
public class ScheduleJob {
	
	@Autowired
	TaskService taskService;
	
	@Scheduled(cron = "${cron.expression}")
    public void taskReminderSchedulerUpdate() throws Exception
    {
		JSONObject jsonObject = new JSONObject();
		TaskEntity taskEntity = taskService.getTask(AppUtils.getDateWithZeroTime(AppUtils.currentDatetime));
		jsonObject = taskService.pushNotification(taskEntity);
		if((boolean) jsonObject.get("result"))
			jsonObject = taskService.updateReminderFlag(taskEntity);
        	if((boolean) jsonObject.get("result"))
        		taskService.updateCronExpression();
    }
	
	@Scheduled(cron = "00 15 01 * * *")
    public void taskReminderCheckAndUpdate() throws AWTException, ConfigurationException
    {
		taskService.updateCronExpression();
    }
	
}
