package com.example.api.service;

import com.example.api.model.Label;
import com.example.api.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

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
        labelRepository.deleteById(id);
    }
}
