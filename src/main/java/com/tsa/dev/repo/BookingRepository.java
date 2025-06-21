package com.tsa.dev.repo;

import com.tsa.dev.model.Booking;
import com.tsa.dev.model.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByUserId(Long userId, Pageable pageable);

    List<Booking> findAllByStatusAndCreatedAtBefore(BookingStatus status, Instant createdAt);

    Page<Booking> findBookingByStatus(BookingStatus status, Pageable pageable);
}
