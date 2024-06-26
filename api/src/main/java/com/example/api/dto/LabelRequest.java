package com.example.api.dto;

/**
 * DTO pentru cererea de creare sau actualizare a unei etichete (label).
 */
public class LabelRequest {
    private String name;
    private String color;
    private Long taskId;

    // Constructor implicit
    public LabelRequest() {
    }

    // Getters și setters

    /**
     * Returnează numele etichetei.
     *
     * @return Numele etichetei.
     */
    public String getName() {
        return name;
    }

    /**
     * Setează numele etichetei.
     *
     * @param name Numele etichetei.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returnează culoarea etichetei.
     *
     * @return Culoarea etichetei.
     */
    public String getColor() {
        return color;
    }

    /**
     * Setează culoarea etichetei.
     *
     * @param color Culoarea etichetei.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Returnează ID-ul task-ului asociat.
     *
     * @return ID-ul task-ului asociat.
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * Setează ID-ul task-ului asociat.
     *
     * @param taskId ID-ul task-ului asociat.
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
