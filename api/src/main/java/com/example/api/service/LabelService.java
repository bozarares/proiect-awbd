package com.example.api.service;

import com.example.api.entity.Label;
import com.example.api.entity.Task;
import com.example.api.repository.LabelRepository;
import com.example.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    public Label getLabelById(Long id) {
        return labelRepository.findById(id).orElse(null);
    }

    public Label createLabel(Label label) {
        return labelRepository.save(label);
    }

    public Label updateLabel(Long id, Label labelDetails) {
        Label label = labelRepository.findById(id).orElse(null);
        if (label != null) {
            label.setName(labelDetails.getName());
            label.setColor(labelDetails.getColor());
            return labelRepository.save(label);
        }
        return null;
    }

    public void deleteLabel(Long id) {
        Optional<Label> labelOptional = labelRepository.findById(id);
        if (labelOptional.isPresent()) {
            Label label = labelOptional.get();
            List<Task> tasks = label.getTasks();
            for (Task task : tasks) {
                task.getLabels().remove(label);
                taskRepository.save(task);
            }
            labelRepository.deleteById(id);
        }
    }
}
