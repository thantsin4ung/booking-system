package com.tsa.dev.requestDTO;

import com.tsa.dev.model.enums.BookingStatus;
import com.tsa.dev.model.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequestDTO {
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
