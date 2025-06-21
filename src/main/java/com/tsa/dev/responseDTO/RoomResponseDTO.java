package com.tsa.dev.responseDTO;

import com.tsa.dev.model.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponseDTO {
    private Long id;
    private String roomNumber;
    private int floor;
    private RoomType roomType;
    private Double price;
    private boolean available;
}
