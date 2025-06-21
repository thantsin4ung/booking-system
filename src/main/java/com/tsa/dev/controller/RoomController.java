package com.tsa.dev.controller;

import com.tsa.dev.requestDTO.RoomRequestDTO;
import com.tsa.dev.responseDTO.RoomResponseDTO;
import com.tsa.dev.service.RoomService;
import com.tsa.dev.utils.PaginationRequest;
import com.tsa.dev.utils.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/api/admin/create-rooms")
    public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody RoomRequestDTO dto) {
        return ResponseEntity.ok(roomService.createRoom(dto));
    }

    @PostMapping("/api/user/rooms")
    public ResponseEntity<PaginationResponse<RoomResponseDTO>> getRooms(@RequestBody PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), Sort.by(paginationRequest.getSortBy()));
        PaginationResponse<RoomResponseDTO> response = roomService.getAllRooms(pageable);
        return ResponseEntity.ok(response);
    }
}
