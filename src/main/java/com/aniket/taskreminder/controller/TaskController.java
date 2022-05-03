package com.aniket.taskreminder.controller;

import java.io.InputStream;
import java.net.Authenticator.RequestorType;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aniket.taskreminder.dao.TaskDao;
import com.aniket.taskreminder.entity.TaskEntity;
import com.aniket.taskreminder.json.TaskJson;
import com.aniket.taskreminder.service.TaskService;
import com.aniket.taskreminder.util.AppConstants;

@RestController
@CrossOrigin
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
		
	@PostMapping(value = "/saveTasks")
	public JSONObject saveTasks(@RequestBody TaskJson taskJson, Model model) throws Exception{
		JSONObject jsonObject = new JSONObject();
		TaskJson taskJsonObj = new TaskJson();
		try {
			
			jsonObject = taskService.saveTasks(taskJson);
			taskJsonObj = (TaskJson) jsonObject.get(AppConstants.taskJson);
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		return jsonObject;
	}
	
	@PostMapping(value = "/deleteTasks")
	public JSONObject deleteeTasks(@RequestBody String taskIdStr, Model model) throws Exception{
		JSONObject jsonObject = new JSONObject();
		try {
			Integer taskId = Integer.valueOf(taskIdStr);
			jsonObject = taskService.deleteTasks(taskId);
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		return jsonObject;
	}
	
	/*@PostMapping(value = "/uploadFileTasks")
	public JSONObject uploadFileTasks(@RequestParam("file") MultipartFile file, Model model) throws Exception{
		JSONObject jsonObject = new JSONObject();
		TaskJson taskJsonObj = new TaskJson();
		try {
			
			
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		return jsonObject;
	}*/

	
}
