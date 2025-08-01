package com.cargopro.linternship_project.repository;

import com.cargopro.linternship_project.model.Booking;
import com.cargopro.linternship_project.model.Load;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // <-- Add this
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
// Add JpaSpecificationExecutor to the extends list
public interface BookingRepository extends JpaRepository<Booking, UUID>, JpaSpecificationExecutor<Booking> {

    long countByLoadAndStatusNot(Load load, Booking.Status status);
}