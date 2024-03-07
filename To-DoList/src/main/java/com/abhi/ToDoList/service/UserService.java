package com.abhi.ToDoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.ToDoList.dao.UserDao;
import com.abhi.ToDoList.model.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userdao;
	
	
	
	public void register(User user)
	{
		userdao.save(user);
	}
	
	
	public boolean login(String email, String password) {
        User user = userdao.findByEmail(email);
        // Check if the user exists and if the provided password matches the encoded password in the database
        return user != null && password.equals(user.getPassword());
    }
	
	
	
	public User getUserByEmail(String email) {
        return userdao.findByEmail(email);
    }

}
