package com.tsa.dev.quartzJob;

import com.tsa.dev.model.Booking;
import com.tsa.dev.model.enums.BookingStatus;
import com.tsa.dev.repo.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AutoCancelBookingJob implements Job {

    private final BookingRepository bookingRepository;

    @Override
    public void execute(JobExecutionContext context) {
        Instant cutoff = Instant.now()
                .minus(Duration.ofMinutes(3))
                .truncatedTo(ChronoUnit.MILLIS); // ensure match with DB precision

        System.out.println("Cutoff (UTC): " + cutoff);

        List<Booking> outdatedBookings = bookingRepository.findAllByStatusAndCreatedAtBefore(
                BookingStatus.PENDING, cutoff
        );

        System.out.println(cutoff + " Minutes ");

        for (Booking booking : outdatedBookings) {
            booking.setStatus(BookingStatus.CANCELLED);
            booking.setUpdatedAt(Instant.now());
        }

        bookingRepository.saveAll(outdatedBookings);
        System.out.println("Auto-cancelled " + outdatedBookings.size() + " bookings.");
    }
}
