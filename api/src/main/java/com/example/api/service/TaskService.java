package com.example.api.service;

import com.example.api.entity.Task;
import com.example.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Obține toate taskurile
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Obține un task după ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // Creează un nou task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Actualizează un task existent
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCreatedDate(taskDetails.getCreatedDate());
            task.setDueDate(taskDetails.getDueDate());
            task.setPriority(taskDetails.getPriority());
            task.setStatus(taskDetails.getStatus());
            task.setLabels(taskDetails.getLabels());
            return taskRepository.save(task);
        }
        return null;
    }

    // Șterge un task după ID
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
