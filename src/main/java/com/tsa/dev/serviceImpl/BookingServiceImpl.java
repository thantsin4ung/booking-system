package com.tsa.dev.serviceImpl;

import com.tsa.dev.model.Booking;
import com.tsa.dev.model.Room;
import com.tsa.dev.model.User;
import com.tsa.dev.model.enums.BookingStatus;
import com.tsa.dev.repo.BookingRepository;
import com.tsa.dev.repo.RoomRepository;
import com.tsa.dev.repo.UserRepository;
import com.tsa.dev.requestDTO.BookingRequestDTO;
import com.tsa.dev.responseDTO.BookingResponseDTO;
import com.tsa.dev.service.BookingService;
import com.tsa.dev.utils.PaginationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    @Override
    @Transactional
    @CacheEvict(value = "bookingsByUser", key = "#request.userId")
    public BookingResponseDTO createBooking(BookingRequestDTO request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = new Booking();
        booking.setCustomerName(request.getCustomerName());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setPrice(request.getPrice());
        booking.setStatus(request.getStatus());
        booking.setPaymentStatus(request.getPaymentStatus());
        booking.setNotes(request.getNotes());
        booking.setReferenceCode(request.getReferenceCode());
        booking.setGuests(request.getGuests());
        booking.setRoom(room);
        booking.setUser(user);
        booking.setCreatedAt(Instant.now());

        Booking saved = bookingRepository.save(booking);

        return mapToDTO(saved);
    }

    @Override
    @Cacheable("booking")
    public BookingResponseDTO getBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return mapToDTO(booking);
    }

    @Override
    @Cacheable(value = "bookingsByUser", key = "#userId")
    public PaginationResponse<BookingResponseDTO> getBookingsByUser(Long userId, Pageable pageable) {
        Page<Booking> page = bookingRepository.findAllByUserId(userId, pageable);

        List<BookingResponseDTO> content = page.stream()
                .map(this::mapToDTO)
                .toList();

        return new PaginationResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    @Cacheable(value = "bookings")
    public PaginationResponse<BookingResponseDTO> getBookings(Pageable pageable) {
        Page<Booking> page = bookingRepository.findAll(pageable);

        List<BookingResponseDTO> content = page.stream()
                .map(this::mapToDTO)
                .toList();

        return new PaginationResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = {"booking", "bookingsByUser"}, allEntries = true)
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Override
    @Cacheable(value = "bookingsByStatus", key = "#status")
    public PaginationResponse<BookingResponseDTO> getBookingsByStatus(BookingStatus status, Pageable pageable) {
        Page<Booking> page = bookingRepository.findBookingByStatus(status, pageable);

        List<BookingResponseDTO> content = page.stream()
                .map(this::mapToDTO)
                .toList();

        return new PaginationResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    private BookingResponseDTO mapToDTO(Booking booking) {
        return BookingResponseDTO.builder()
                .id(booking.getId())
                .customerName(booking.getCustomerName())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .price(booking.getPrice())
                .status(booking.getStatus())
                .paymentStatus(booking.getPaymentStatus())
                .notes(booking.getNotes())
                .referenceCode(booking.getReferenceCode())
                .guests(booking.getGuests())
                .roomId(booking.getRoom().getId())
                .userId(booking.getUser().getId())
                .build();
    }
}