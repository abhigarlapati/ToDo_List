package com.abhi.ToDoList.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.ToDoList.dao.UserDao;
import com.abhi.ToDoList.model.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userdao;
	
	@Autowired
	EmailService emailService;
	
	
	
	
	
	public void register(User user)
	{
		
		String verificationToken = generateVerificationToken();
		user.setVerificationToken(verificationToken);
        user.setVerified(false);
		
		userdao.save(user);
		
		String verificationLink = "http://localhost:8081/verify?email=" + user.getEmail() + "&token=" + verificationToken;
        emailService.sendVerificationEmail(user.getEmail(), verificationLink);
        
	}
	
	
	public boolean login(String email, String password) {
		User user = userdao.findByEmail(email);
	    if (user != null && password.equals(user.getPassword()) && user.isVerified()) {
	        return true;
	    }
	    return false;
    }
	
	public List<User> getAllUsers() {
        return userdao.findAll();
    }
	
	
	public User getUserByEmail(String email) {
        return userdao.findByEmail(email);
    }
	
	private String generateVerificationToken() {
        // Generate a unique verification token (you can use UUID.randomUUID() or any other method)
        return UUID.randomUUID().toString();
    }
	
	public void updateUser(User user) {
        userdao.save(user);
    }

}
