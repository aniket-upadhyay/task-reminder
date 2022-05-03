package com.aniket.taskreminder.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.aniket.taskreminder.config.HibernateConfig;
import com.aniket.taskreminder.entity.TaskEntity;
import com.aniket.taskreminder.json.TaskJson;
import com.aniket.taskreminder.util.AppConstants;

@Component("TaskDao")
@Transactional
public class TaskDaoImpl implements TaskDao {
	

    @Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	


	@Override
	public TaskEntity getTaskById(Integer taskId) {
		TaskEntity task = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(TaskEntity.class);
			criteria.add(Restrictions.eq("id", taskId));
			task = (TaskEntity) criteria.uniqueResult();
				
		}catch(Exception e) {
			e.printStackTrace();
		}
		return task;
	}
    
	@Override
	public JSONObject saveTasks(TaskEntity taskEntity) {
		JSONObject jsonObject = new JSONObject();
		TaskEntity taskEntityObj = new TaskEntity();
		try {
			Session session = sessionFactory.getCurrentSession();
			
			session.saveOrUpdate(taskEntity);
			taskEntityObj = getTaskById(taskEntity.getId());
			
			jsonObject.put(AppConstants.result, AppConstants.success);
			jsonObject.put(AppConstants.taskEntity, taskEntityObj);
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		 return jsonObject;
	}


	@Override
	public TaskEntity getTask(Date currentDate) {
		List<TaskEntity> taskEntities = new ArrayList<TaskEntity>();
		TaskEntity task = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(TaskEntity.class);
			criteria.add(Restrictions.eq("createdDate", currentDate));
			criteria.add(Restrictions.eq("reminderFlag", 0));
			criteria.addOrder(Order.asc("endTime"));
			taskEntities = criteria.list();
			
			if(!taskEntities.isEmpty())
				task = taskEntities.get(0);
				
		}catch(Exception e) {
			e.printStackTrace();
		}
		return task;
	}


	@Override
	public JSONObject updateReminderFlag(TaskEntity taskEntity) {
		JSONObject jsonObject = new JSONObject();
		try {
			Session session = sessionFactory.getCurrentSession();
			taskEntity.setReminderFlag(1);
			session.saveOrUpdate(taskEntity);
			jsonObject.put(AppConstants.result, AppConstants.success);
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		return jsonObject;
	}



	@Override
	public JSONObject deleteTasks(TaskEntity taskEntity) {
		JSONObject jsonObject = new JSONObject();
		try {
			Session session = sessionFactory.getCurrentSession();
			session.delete(taskEntity);
			jsonObject.put(AppConstants.result, AppConstants.success);
		}catch(Exception e) {
			jsonObject.put(AppConstants.result, AppConstants.failure);
		}
		return jsonObject;
	}

}
