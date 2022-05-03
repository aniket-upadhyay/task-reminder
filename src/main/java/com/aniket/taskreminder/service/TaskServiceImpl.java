package com.aniket.taskreminder.service;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;

import com.aniket.taskreminder.dao.TaskDao;
import com.aniket.taskreminder.entity.TaskEntity;
import com.aniket.taskreminder.json.TaskJson;
import com.aniket.taskreminder.util.AppConstants;
import com.aniket.taskreminder.util.AppUtils;

@Component("TaskService")
public class TaskServiceImpl implements TaskService {

	
	@Autowired
	TaskDao taskDao;
	
	@Override
	public TaskEntity getTaskbyId(Integer taskId) {
		TaskEntity taskEntity = new TaskEntity();
		try {
			taskEntity = taskDao.getTaskById(taskId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return taskEntity;
	}
	
	@Override
	public JSONObject saveTasks(TaskJson taskJson) {
		JSONObject jsonObject = new JSONObject();
		
		TaskEntity taskEntityObj = new TaskEntity();
		try {
			
			TaskEntity taskEntity = new TaskEntity(taskJson);
		
			jsonObject = taskDao.saveTasks(taskEntity);
			taskEntityObj = (TaskEntity) jsonObject.get("taskEntity");
			TaskJson taskJsonObj = new TaskJson(taskEntityObj);
			
			if((boolean) jsonObject.get("result")) {
				updateCronExpression();
			}
			jsonObject.put(AppConstants.result, AppConstants.success);
			jsonObject.put(AppConstants.taskJson, taskJsonObj);
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		return jsonObject;
	}
	
	public TaskEntity getTask(Date currentDate) {
		TaskEntity taskEntity = null;
		try {
			taskEntity = taskDao.getTask(currentDate);
		}catch(Exception e) {
			e.printStackTrace();
		}
			return taskEntity;
	}

	@Override
	public JSONObject triggerReminder(String reminderMessage) throws AWTException {
		JSONObject jsonObject = new JSONObject();
		try {
			SystemTray tray = SystemTray.getSystemTray();
	        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
	        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
	        trayIcon.setImageAutoSize(true);
	        trayIcon.setToolTip("System tray icon demo");
	        tray.add(trayIcon);
	
	        trayIcon.displayMessage("Task Reminder", reminderMessage, MessageType.NONE);
	        jsonObject.put(AppConstants.result, AppConstants.success);
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		return jsonObject;
	}

	@Override
	public JSONObject updateReminderFlag(TaskEntity taskEntity) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = taskDao.updateReminderFlag(taskEntity);
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		return jsonObject;
		
	}

	@Override
	public void updateCronExpression() throws ConfigurationException {
		Properties props = new Properties();
		try {
			TaskEntity taskEntity = getTask(AppUtils.getDateWithZeroTime(AppUtils.currentDatetime));
			if(taskEntity != null) {
				String cronExpression = taskEntity.getEndTime().getSeconds() + " " + taskEntity.getEndTime().getMinutes() + " " + taskEntity.getEndTime().getHours() +  " * * *";
				
				ClassPathResource c = new ClassPathResource("application.properties");
				File f = c.getFile();
				
				final FileInputStream fileInputStream = new FileInputStream(f.getAbsolutePath());
				props.load((FileInputStream) new FileInputStream(f.getAbsolutePath()));
		        fileInputStream.close();
				
				props.setProperty("cron.expression", cronExpression);
	
		        final FileOutputStream fileOutputStream = new FileOutputStream(f.getAbsolutePath());
		        props.store(fileOutputStream, null);
			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject deleteTasks(Integer taskId) {
		JSONObject jsonObject = new JSONObject();
		TaskEntity taskEntity = null;
		try {
			taskEntity = getTaskbyId(taskId);
			if(taskEntity != null) {
				taskDao.deleteTasks(taskEntity);
				jsonObject.put(AppConstants.result, AppConstants.success);
			}
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		return jsonObject;
	}

	@Override
	public JSONObject pushNotification(TaskEntity taskEntity) throws Exception {
		JSONObject jsonObject = new JSONObject();
		URL url = new URL(AppConstants.API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		try {
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + AppConstants.SERVER_KEY_FCM);
			conn.setRequestProperty("Content-Type", "application/json");
			JSONObject data = new JSONObject();
			data.put("to", taskEntity.getFcmToken().trim());
			JSONObject info = new JSONObject();
			info.put("title", AppConstants.REMINDER); // Notification title
			info.put("body", taskEntity.getTask()); // Notification body
			data.put("notification", info);
			System.out.println(data.toJSONString());
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data.toString());
			wr.flush();
			wr.close();
			int responseCode = conn.getResponseCode();
			System.out.println("Response Code : " + responseCode);
	
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			jsonObject.put(AppConstants.result, AppConstants.success);
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.success);
		}
		return jsonObject;
	}

}
