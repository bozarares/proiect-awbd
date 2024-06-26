package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.api.entity.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

}
