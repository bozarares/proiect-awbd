package com.example.api.controller;

import com.example.api.dto.LabelRequest;
import com.example.api.entity.Label;
import com.example.api.entity.Task;
import com.example.api.service.LabelService;
import com.example.api.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Label> getAllLabels() {
        return labelService.getAllLabels();
    }

    @GetMapping("/{id}")
    public Label getLabelById(@PathVariable Long id) {
        return labelService.getLabelById(id);
    }

    @PostMapping
    public Label createLabel(@RequestBody @Valid LabelRequest labelRequest) {
        Label label = new Label();
        label.setName(labelRequest.getName());
        label.setColor(labelRequest.getColor());

        Label createdLabel = labelService.createLabel(label);

        Long taskId = labelRequest.getTaskId();
        if (taskId != null) {
            Task existingTask = taskService.getTaskById(taskId);
            if (existingTask != null) {
                existingTask.getLabels().add(createdLabel);
                taskService.updateTask(existingTask.getId(), existingTask);
            } else {
                throw new IllegalArgumentException("Task not found for id: " + taskId);
            }
        } else {
            throw new IllegalArgumentException("TaskId must not be null");
        }

        return createdLabel;
    }

    @PutMapping("/{id}")
    public Label updateLabel(@PathVariable Long id, @RequestBody @Valid Label labelDetails) {
        return labelService.updateLabel(id, labelDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }
}
