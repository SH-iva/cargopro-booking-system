package com.cargopro.linternship_project.service;

import com.cargopro.linternship_project.exception.ResourceNotFoundException; // <-- Import the new exception
import com.cargopro.linternship_project.model.Booking;
import com.cargopro.linternship_project.model.Load;
import com.cargopro.linternship_project.repository.BookingRepository;
import com.cargopro.linternship_project.repository.LoadRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LoadRepository loadRepository;

    @Transactional
    public Booking createBooking(UUID loadId, Booking booking) {
        Load load = loadRepository.findById(loadId)
                // Change this line
                .orElseThrow(() -> new ResourceNotFoundException("Load not found with ID: " + loadId));

        if (load.getStatus() == Load.Status.CANCELLED) {
            throw new IllegalStateException("Cannot create booking for a cancelled load.");
        }

        booking.setLoad(load);
        booking.setStatus(Booking.Status.PENDING);
        load.setStatus(Load.Status.BOOKED);
        loadRepository.save(load);

        return bookingRepository.save(booking);
    }

    @Transactional
    public void deleteBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                // Change this line
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        Load load = booking.getLoad();
        bookingRepository.delete(booking);

        long remainingBookings = bookingRepository.countByLoadAndStatusNot(load, Booking.Status.REJECTED);
        if (remainingBookings == 0) {
            load.setStatus(Load.Status.POSTED);
            loadRepository.save(load);
        }
    }

    public Booking getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                // Change this line
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
    }

    public Booking updateBooking(UUID bookingId, Booking bookingDetails) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                // Change this line
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        existingBooking.setStatus(bookingDetails.getStatus());
        existingBooking.setComment(bookingDetails.getComment());

        return bookingRepository.save(existingBooking);
    }

    public List<Booking> getAllBookings(UUID loadId, String transporterId, Booking.Status status) {
        Specification<Booking> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (loadId != null) {
                predicates.add(criteriaBuilder.equal(root.get("load").get("id"), loadId));
            }
            if (transporterId != null && !transporterId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("transporterId"), transporterId));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return bookingRepository.findAll(spec);
    }
}