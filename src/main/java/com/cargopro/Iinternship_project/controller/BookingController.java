package com.cargopro.linternship_project.controller;

import com.cargopro.linternship_project.model.Booking;
import com.cargopro.linternship_project.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // POST /booking?loadId=...
    @PostMapping
    public ResponseEntity<Booking> addBooking(@RequestParam UUID loadId, @RequestBody Booking booking) {
        Booking newBooking = bookingService.createBooking(loadId, booking);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    // GET /booking (with filters)
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings(
            @RequestParam(required = false) UUID loadId,
            @RequestParam(required = false) String transporterId,
            @RequestParam(required = false) Booking.Status status) {

        List<Booking> bookings = bookingService.getAllBookings(loadId, transporterId, status);
        return ResponseEntity.ok(bookings);
    }

    // GET /booking/{bookingId}
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBooking(@PathVariable UUID bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    // PUT /booking/{bookingId}
    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable UUID bookingId, @RequestBody Booking bookingDetails) {
        Booking updatedBooking = bookingService.updateBooking(bookingId, bookingDetails);
        return ResponseEntity.ok(updatedBooking);
    }

    // DELETE /booking/{bookingId}
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable UUID bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}