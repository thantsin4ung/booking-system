package com.tsa.dev.responseDTO;

import com.tsa.dev.model.enums.BookingStatus;
import com.tsa.dev.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private String customerName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double price;
    private BookingStatus status;
    private PaymentStatus paymentStatus;
    private String notes;
    private String referenceCode;
    private int guests;
    private Long roomId;
    private Long userId;
}

