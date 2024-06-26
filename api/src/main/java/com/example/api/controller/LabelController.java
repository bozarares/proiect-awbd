package com.example.api.controller;

import com.example.api.dto.LabelRequest;
import com.example.api.entity.Label;
import com.example.api.entity.Task;
import com.example.api.service.LabelService;
import com.example.api.service.TaskService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(LabelController.class);

    /**
     * Endpoint pentru a obține toate etichetele.
     * 
     * @return Lista de etichete.
     */
    @GetMapping
    public List<Label> getAllLabels() {
        logger.info("Fetching all labels");
        return labelService.getAllLabels();
    }

    /**
     * Endpoint pentru a obține o etichetă după ID.
     * 
     * @param id ID-ul etichetei.
     * @return Eticheta găsită.
     */
    @GetMapping("/{id}")
    public Label getLabelById(@PathVariable Long id) {
        logger.info("Fetching label with ID: {}", id);
        return labelService.getLabelById(id);
    }

    /**
     * Endpoint pentru a crea o nouă etichetă și a o asocia unui task existent.
     * 
     * @param labelRequest Cererea pentru a crea eticheta, incluzând și ID-ul
     *                     task-ului.
     * @return Eticheta creată.
     */
    @PostMapping
    public Label createLabel(@RequestBody @Valid LabelRequest labelRequest) {
        logger.info("Creating new label with name: {}", labelRequest.getName());
        Label label = new Label();
        label.setName(labelRequest.getName());
        label.setColor(labelRequest.getColor());

        Label createdLabel = labelService.createLabel(label);

        Long taskId = labelRequest.getTaskId();
        if (taskId != null) {
            Task existingTask = taskService.getTaskById(taskId);
            if (existingTask != null) {
                logger.info("Associating label with task ID: {}", taskId);
                existingTask.getLabels().add(createdLabel);
                taskService.updateTask(existingTask.getId(), existingTask);
            } else {
                logger.error("Task not found for ID: {}", taskId);
                throw new IllegalArgumentException("Task not found for id: " + taskId);
            }
        } else {
            logger.error("TaskId must not be null");
            throw new IllegalArgumentException("TaskId must not be null");
        }

        return createdLabel;
    }

    /**
     * Endpoint pentru a actualiza o etichetă existentă.
     * 
     * @param id           ID-ul etichetei ce trebuie actualizată.
     * @param labelDetails Detaliile actualizate ale etichetei.
     * @return Eticheta actualizată.
     */
    @PutMapping("/{id}")
    public Label updateLabel(@PathVariable Long id, @RequestBody @Valid Label labelDetails) {
        logger.info("Updating label with ID: {}", id);
        return labelService.updateLabel(id, labelDetails);
    }

    /**
     * Endpoint pentru a șterge o etichetă după ID.
     * 
     * @param id ID-ul etichetei ce trebuie șters.
     */
    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable Long id) {
        logger.info("Deleting label with ID: {}", id);
        labelService.deleteLabel(id);
    }
}
