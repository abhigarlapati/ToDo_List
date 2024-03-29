package com.abhi.ToDoList.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Task {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String taskdata;
	boolean status=false;
	LocalDateTime deadline;
	private boolean notificationSent;
	
	@ManyToOne
	private User user;
	
	
	
	
	
	
	public boolean isNotificationSent() {
		return notificationSent;
	}
	public void setNotificationSent(boolean notificationSent) {
		this.notificationSent = notificationSent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskdata() {
		return taskdata;
	}
	public void setTaskdata(String taskdata) {
		this.taskdata = taskdata;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	
	
	public LocalDateTime getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
	
	
	

}
