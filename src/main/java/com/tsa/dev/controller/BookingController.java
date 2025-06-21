package com.tsa.dev.controller;

import com.tsa.dev.model.enums.BookingStatus;
import com.tsa.dev.requestDTO.BookingRequestDTO;
import com.tsa.dev.responseDTO.BookingResponseDTO;
import com.tsa.dev.service.BookingService;
import com.tsa.dev.utils.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PaginationResponse<BookingResponseDTO>> getBookingsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        PaginationResponse<BookingResponseDTO> bookings = bookingService.getBookingsByUser(userId, pageable);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<PaginationResponse<BookingResponseDTO>> getBookingsByUser(
            @PathVariable BookingStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        PaginationResponse<BookingResponseDTO> bookings = bookingService.getBookingsByStatus(status, pageable);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/get-all")
    public ResponseEntity<PaginationResponse<BookingResponseDTO>> getBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        PaginationResponse<BookingResponseDTO> bookings = bookingService.getBookings(pageable);
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}

