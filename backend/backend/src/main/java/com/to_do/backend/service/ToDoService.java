package com.to_do.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.to_do.backend.model.ToDo;
import com.to_do.backend.model.User;
import com.to_do.backend.repository.ToDoRepository;
import com.to_do.backend.repository.UserRepository;

import java.util.List;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository taskRepository;

    @Autowired 
    private UserRepository userRepository;

    public List<ToDo> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<ToDo> getTasksByUsername(String username) {
        return taskRepository.findByUserUsername(username);
    }

    public ToDo createTask(String username, ToDo task) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        task.setUser(user); // Link the task to the found user
        return taskRepository.save(task);
    }

    public ToDo markTaskComplete(String username, Long id) {
        ToDo task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to update this task");
        }
        
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    public ToDo markTaskIncomplete(String username, Long id) {
        ToDo task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to update this task");
        }
        
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    // public ToDo updateTask(Long id, ToDo taskDetails) {
    //     ToDo task = taskRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
    //     task.setTitle(taskDetails.getTitle());
    //     task.setCompleted(taskDetails.isCompleted());
        
    //     return taskRepository.save(task);
    // }

    // public void deleteTask(Long id) {
    //     taskRepository.deleteById(id);
    // }
    
}
