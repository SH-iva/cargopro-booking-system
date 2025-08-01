package com.cargopro.linternship_project.controller;

import com.cargopro.linternship_project.model.Load;
import com.cargopro.linternship_project.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/load")
public class LoadController {

    @Autowired
    private LoadService loadService;

    // POST /load
    @PostMapping
    public ResponseEntity<Load> addLoad(@RequestBody Load load) {
        Load newLoad = loadService.createLoad(load);
        return new ResponseEntity<>(newLoad, HttpStatus.CREATED);
    }

    // GET /load (with filtering and pagination)
    @GetMapping
    public ResponseEntity<Page<Load>> getAllLoads(
            @RequestParam(required = false) String shipperId,
            @RequestParam(required = false) String truckType,
            @RequestParam(required = false) Load.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Load> loads = loadService.getAllLoads(shipperId, truckType, status, pageable);
        return ResponseEntity.ok(loads);
    }

    // GET /load/{loadId}
    @GetMapping("/{loadId}")
    public ResponseEntity<Load> getLoad(@PathVariable UUID loadId) {
        Load load = loadService.getLoadById(loadId);
        return ResponseEntity.ok(load);
    }

    // PUT /load/{loadId}
    @PutMapping("/{loadId}")
    public ResponseEntity<Load> updateLoad(@PathVariable UUID loadId, @RequestBody Load loadDetails) {
        Load updatedLoad = loadService.updateLoad(loadId, loadDetails);
        return ResponseEntity.ok(updatedLoad);
    }

    // DELETE /load/{loadId}
    @DeleteMapping("/{loadId}")
    public ResponseEntity<Void> deleteLoad(@PathVariable UUID loadId) {
        loadService.deleteLoad(loadId);
        return ResponseEntity.noContent().build();
    }
}