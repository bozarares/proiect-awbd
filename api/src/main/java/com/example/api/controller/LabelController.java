package com.example.api.controller;

import com.example.api.entity.Label;
import com.example.api.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping
    public List<Label> getAllLabels() {
        return labelService.getAllLabels();
    }

    @GetMapping("/{id}")
    public Label getLabelById(@PathVariable Long id) {
        return labelService.getLabelById(id);
    }

    @PostMapping
    public Label createLabel(@RequestBody Label label) {
        return labelService.createLabel(label);
    }

    @PutMapping("/{id}")
    public Label updateLabel(@PathVariable Long id, @RequestBody Label labelDetails) {
        return labelService.updateLabel(id, labelDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }
}
