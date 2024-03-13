package com.abhi.ToDoList.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.ToDoList.dao.TaskDao;
import com.abhi.ToDoList.model.Task;
import com.abhi.ToDoList.model.User;

@Service
public class TaskService {
	
	@Autowired
    TaskDao taskDao;
	
	@Autowired
	UserService userservice;


    public String addTask(Task t,String userEmail) {
    	User user=userservice.getUserByEmail(userEmail);
    	t.setUser(user);
        taskDao.save(t);
        return "success";
    }
    
    public List<Task> listall(String userEmail)
    {
    	User user = userservice.getUserByEmail(userEmail);
    	return taskDao.findByUser(user);
    }
    
    public void delet(int id) {
    	taskDao.deleteById(id);
    }

    public void updateTask(Task task, String userEmail) {
        // Retrieve the task from the database
        Task existingTask = taskDao.findById(task.getId()).orElseThrow(() -> new RuntimeException("Task not found"));

        if (existingTask != null && existingTask.getUser().getEmail().equals(userEmail)) {
            // Update the task data
            existingTask.setTaskdata(task.getTaskdata());
            existingTask.setDeadline(task.getDeadline());
            existingTask.setStatus(task.isStatus());

            // Save the updated task
            taskDao.save(existingTask);
        } else {
            throw new RuntimeException("Task not found or unauthorized to update this task");
        }
    }
    

    public Task getTaskById(int taskId) {
        return taskDao.findById(taskId).orElse(null);
    }

   
    public void updateTask(Task task) {
        taskDao.save(task);
    }

	
    public List<Task> getOverdueTasks(LocalDateTime now) {
        // Query the database to retrieve all tasks with a deadline before the current date and time
        return taskDao.findByDeadlineBeforeAndStatusIsFalse(now);
    }

    public List<Task> getNotCompletedTasksByUser(User user) {
        return taskDao.findByUserAndStatus(user, false); // Assuming status 'false' means task is not completed
    }

}
