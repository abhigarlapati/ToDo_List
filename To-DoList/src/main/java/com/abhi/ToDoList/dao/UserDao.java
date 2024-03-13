package com.abhi.ToDoList.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhi.ToDoList.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	User findByEmail(String email);
	
	
}
