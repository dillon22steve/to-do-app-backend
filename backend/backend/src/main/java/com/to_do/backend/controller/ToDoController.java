package com.to_do.backend.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.to_do.backend.service.ToDoService;
import com.to_do.backend.model.ToDo;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class ToDoController {
    
    @Autowired
    private ToDoService service;

    @GetMapping
    public List<ToDo> getTasks(Principal principal) {
        // principal.getName() usually returns the username/email
        String username = principal.getName(); 
        return service.getTasksByUsername(username);
    }

    @PostMapping
    public ResponseEntity<ToDo> addTask(@RequestBody ToDo task, Principal principal) {
        System.out.println("Creating task for user: " + principal.getName() + " " + task.getTitle());
        ToDo createdTask = service.createTask(principal.getName(), task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<ToDo> updateTask(@PathVariable Long id, @RequestBody ToDo task) {
    //     return ResponseEntity.ok(service.updateTask(id, task));
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    //     service.deleteTask(id);
    //     return ResponseEntity.noContent().build();
    // }

}
