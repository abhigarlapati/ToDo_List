package com.abhi.ToDoList.notification;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.abhi.ToDoList.model.Task;
import com.abhi.ToDoList.model.User;
import com.abhi.ToDoList.service.TaskService;
import com.abhi.ToDoList.service.UserService;

@Component
public class TaskDeadLineNotification {
    
    private final UserService userService;
    private final TaskService taskService;
    private final JavaMailSender javaMailSender;

    @Autowired
    public TaskDeadLineNotification(UserService userService, TaskService taskService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.taskService = taskService;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "0 0 * * * *") // Run every hour
    public void sendScheduledEmails() {
        Iterable<User> users = userService.getAllUsers();
        for (User user : users) {
            LocalTime notificationTime = user.getNotificationTime();
            if (notificationTime != null && isCurrentTime(notificationTime)) {
                List<Task> tasks = taskService.getNotCompletedTasksByUser(user); // Fetch not completed tasks for the user
                if (!tasks.isEmpty()) {
                    sendNotificationEmail(user.getEmail(), tasks);
                }
            }
        }
    }

    private boolean isCurrentTime(LocalTime time) {
        LocalTime currentTime = LocalTime.now();
        return currentTime.equals(time);
    }

    void sendNotificationEmail(String email, List<Task> tasks) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Task Notification Deadline");
        
        StringBuilder message = new StringBuilder("Your tasks are overdue:\n");
        for (Task task : tasks) {
            message.append("Task: ").append(task.getTaskdata())
                   .append("\nDeadline: ").append(task.getDeadline())
                   .append("\n\n");
        }
        
        msg.setText(message.toString());
        javaMailSender.send(msg);
    }
}
