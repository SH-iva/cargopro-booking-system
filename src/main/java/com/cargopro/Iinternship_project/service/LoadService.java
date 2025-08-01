package com.cargopro.linternship_project.service;

import com.cargopro.linternship_project.exception.ResourceNotFoundException; // <-- Import the new exception
import com.cargopro.linternship_project.model.Load;
import com.cargopro.linternship_project.repository.LoadRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoadService {

    @Autowired
    private LoadRepository loadRepository;

    public Load createLoad(Load load) {
        load.setStatus(Load.Status.POSTED);
        return loadRepository.save(load);
    }

    public Load getLoadById(UUID loadId) {
        return loadRepository.findById(loadId)
                // Change this line
                .orElseThrow(() -> new ResourceNotFoundException("Load not found with ID: " + loadId));
    }

    public Load updateLoad(UUID loadId, Load loadDetails) {
        Load existingLoad = loadRepository.findById(loadId)
                // Change this line
                .orElseThrow(() -> new ResourceNotFoundException("Load not found with ID: " + loadId));

        existingLoad.setLoadingPoint(loadDetails.getLoadingPoint());
        existingLoad.setUnloadingPoint(loadDetails.getUnloadingPoint());
        existingLoad.setProductType(loadDetails.getProductType());
        existingLoad.setTruckType(loadDetails.getTruckType());
        existingLoad.setNoOfTrucks(loadDetails.getNoOfTrucks());
        existingLoad.setWeight(loadDetails.getWeight());
        existingLoad.setComment(loadDetails.getComment());

        return loadRepository.save(existingLoad);
    }

    public void deleteLoad(UUID loadId) {
        Load load = loadRepository.findById(loadId)
                // Change this line
                .orElseThrow(() -> new ResourceNotFoundException("Load not found with ID: " + loadId));
        loadRepository.delete(load);
    }

    public Page<Load> getAllLoads(String shipperId, String truckType, Load.Status status, Pageable pageable) {
        Specification<Load> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (shipperId != null && !shipperId.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("shipperId"), "%" + shipperId + "%"));
            }
            if (truckType != null && !truckType.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("truckType"), truckType));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return loadRepository.findAll(spec, pageable);
    }
}