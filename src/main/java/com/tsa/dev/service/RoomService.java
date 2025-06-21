package com.tsa.dev.service;

import com.tsa.dev.requestDTO.RoomRequestDTO;
import com.tsa.dev.responseDTO.RoomResponseDTO;
import com.tsa.dev.utils.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    RoomResponseDTO createRoom(RoomRequestDTO request);
    PaginationResponse<RoomResponseDTO> getAllRooms(Pageable pageable);

}
