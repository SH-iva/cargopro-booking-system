package com.cargopro.linternship_project.repository;

import com.cargopro.linternship_project.model.Load;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // <-- Add this import
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
// Add JpaSpecificationExecutor to the extends list
public interface LoadRepository extends JpaRepository<Load, UUID>, JpaSpecificationExecutor<Load> {
}