package com.cargopro.linternship_project.service;

// Imports for the classes we are testing or using
import com.cargopro.linternship_project.exception.ResourceNotFoundException;
import com.cargopro.linternship_project.model.Load;
import com.cargopro.linternship_project.repository.LoadRepository;

// Imports for JUnit and Mockito
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

// Static imports for assertion methods and Mockito functions
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoadServiceTest {

    @Mock
    private LoadRepository loadRepository;

    @InjectMocks
    private LoadService loadService;

    @Test
    void whenCreateLoad_shouldSetStatusToPostedAndSave() {
        // Arrange
        Load inputLoad = new Load();
        inputLoad.setShipperId("test-shipper");
        when(loadRepository.save(any(Load.class))).thenReturn(inputLoad);

        // Act
        Load result = loadService.createLoad(inputLoad);

        // Assert
        assertNotNull(result);
        assertEquals(Load.Status.POSTED, result.getStatus());
        verify(loadRepository, times(1)).save(inputLoad);
    }

    @Test
    void whenGetLoadByNonExistentId_shouldThrowResourceNotFoundException() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(loadRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            loadService.getLoadById(nonExistentId);
        });
        verify(loadRepository, times(1)).findById(nonExistentId);
    }
}