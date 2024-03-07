package com.abhi.ToDoList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.ToDoList.dao.TaskDao;
import com.abhi.ToDoList.model.Task;
import com.abhi.ToDoList.model.User;
import com.abhi.ToDoList.service.TaskService;
import com.abhi.ToDoList.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller

public class TodolistController {
	
	@Autowired
	TaskService taskservice;
	
	@Autowired
	UserService userservice;
	
	@Autowired
	HttpSession httpsession;
	
	@GetMapping("/")
	public String loginpage(Model model)
	{
		
		return "login";
	}
	
	@GetMapping("/home")
	public String home(Model model)
	{
		if(isLoggedIn())
		{
			return "todo";
		}
		else {
			return "redirect:/login";
		}
	}
	
	private boolean isLoggedIn() {
		
		return httpsession.getAttribute("loggedInUser")!=null;
	}
	
	private String getLoggedInUserEmail() {
        return (String) httpsession.getAttribute("loggedInUser");
    }

	
	
	
	@PostMapping("/save")
	public String addTask(@ModelAttribute Task task) {
		if(isLoggedIn())
		{
	      taskservice.addTask(task,getLoggedInUserEmail());
	      return "redirect:/getTasks";
		}
	      return "redirect:/";
	}
	
	@GetMapping("/getTasks")
	public String getAllTasks(Model model)
	{
		if(isLoggedIn())
		{
			List<Task> tasks=taskservice.listall(getLoggedInUserEmail());
			model.addAttribute("tasks",tasks);
			return "task-list";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/deleteTask")
	public String deleteTask(@RequestParam("taskId") int taskId)
	{
		if(isLoggedIn())
		{
			taskservice.delet(taskId);
			return "redirect:/getTasks";
		}
		return "redirect:/login";
	}
	
	
	@GetMapping("/updateTask")
    public String showUpdateForm(@RequestParam("taskId") int taskId, Model model) {
		if(isLoggedIn())
		{
	        Task task = taskservice.getTaskById(taskId);
	        model.addAttribute("task", task);
	        return "updateform";
		}
		return "redirect:/login";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        taskservice.updateTask(task,getLoggedInUserEmail());
        return "redirect:/getTasks";
    }
	
    @GetMapping("/login")
    public String login(Model model)
    {
    	return "login";
    }
    
    @PostMapping("/loginuser")
    public String loginUser(@RequestParam("email") String email,@RequestParam("password") String password) {
        // Check if the provided credentials are valid
        if (userservice.login(email, password)) {
            // If login successful, set a session attribute to mark the user as logged in
            httpsession.setAttribute("loggedInUser", email);
        	return "redirect:/home"; // Redirect the user to the home page or any other page you desire
        } else {
            // If login fails, return an error message or redirect to the login page with a message
            return "redirect:/login?error=invalidCredentials";
        }
    }
    
    
    @GetMapping("/logout")
    public String logout() {
        // Invalidate the session
        httpsession.invalidate();
        return "redirect:/login"; // Redirect the user to the login page after logout
    }
    
    
	@GetMapping("/register")
	public String register(Model model)
	{
		return "register";
	}
	
	@PostMapping("/registeruser")
    public String registerUser(@ModelAttribute User user) {
        
        userservice.register(user);
        return "redirect:/login";
    }
	
	


}
