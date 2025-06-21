package com.tsa.dev.requestDTO;

import com.tsa.dev.model.enums.RoomType;
import lombok.Builder;
import lombok.Data;

@Data
public class RoomRequestDTO {
    private String roomNumber;
    private int floor;
    private RoomType roomType;
    private Double price;
}
