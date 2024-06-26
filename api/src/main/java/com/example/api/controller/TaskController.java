package com.example.api.controller;

import com.example.api.entity.Task;
import com.example.api.service.TaskService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    /**
     * Endpoint pentru a obține toate task-urile.
     * 
     * @return Lista de task-uri.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskService.getAllTasks();
    }

    /**
     * Endpoint pentru a obține un task după ID.
     * 
     * @param id ID-ul task-ului.
     * @return Task-ul găsit.
     */
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        logger.info("Fetching task with ID: {}", id);
        return taskService.getTaskById(id);
    }

    /**
     * Endpoint pentru a crea un nou task.
     * 
     * @param task Task-ul de creat.
     * @return Task-ul creat.
     */
    @PostMapping
    public Task createTask(@RequestBody @Valid Task task) {
        logger.info("Creating new task with title: {}", task.getTitle());
        return taskService.createTask(task);
    }

    /**
     * Endpoint pentru a actualiza un task existent.
     * 
     * @param id          ID-ul task-ului ce trebuie actualizat.
     * @param taskDetails Detaliile actualizate ale task-ului.
     * @return Task-ul actualizat.
     */
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody @Valid Task taskDetails) {
        logger.info("Updating task with ID: {}", id);
        return taskService.updateTask(id, taskDetails);
    }

    /**
     * Endpoint pentru a șterge un task după ID.
     * 
     * @param id ID-ul task-ului ce trebuie șters.
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        logger.info("Deleting task with ID: {}", id);
        taskService.deleteTask(id);
    }
}
