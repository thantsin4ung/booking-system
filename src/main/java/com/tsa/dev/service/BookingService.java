package com.tsa.dev.service;

import com.tsa.dev.model.enums.BookingStatus;
import com.tsa.dev.requestDTO.BookingRequestDTO;
import com.tsa.dev.responseDTO.BookingResponseDTO;
import com.tsa.dev.utils.PaginationResponse;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO request);
    BookingResponseDTO getBooking(Long id);
    PaginationResponse<BookingResponseDTO> getBookingsByUser(Long userId, Pageable pageable);
    PaginationResponse<BookingResponseDTO> getBookings(Pageable pageable);
    void cancelBooking(Long id);
    PaginationResponse<BookingResponseDTO> getBookingsByStatus(BookingStatus status, Pageable pageable);
}
