package com.abhi.ToDoList.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.abhi.ToDoList.model.Task;
import com.abhi.ToDoList.model.User;

@Repository
public interface TaskDao extends JpaRepository<Task, Integer> {
	
	
	List<Task> findByUser(User user);
	
	
	List<Task> findByUserAndDeadlineBeforeAndStatus(User user, LocalDateTime deadline, boolean status);
	
	List<Task> findByDeadlineBeforeAndStatusIsFalse(LocalDateTime now);


	List<Task> findByUserAndStatus(User user, boolean status);
}
