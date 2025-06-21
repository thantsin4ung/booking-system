package com.tsa.dev.serviceImpl;

import com.tsa.dev.model.Room;
import com.tsa.dev.repo.RoomRepository;
import com.tsa.dev.requestDTO.RoomRequestDTO;
import com.tsa.dev.responseDTO.RoomResponseDTO;
import com.tsa.dev.service.RoomService;
import com.tsa.dev.utils.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    @CacheEvict(value = "rooms", allEntries = true)
    public RoomResponseDTO createRoom(RoomRequestDTO request) {
        Room room = new Room();
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPrice(request.getPrice());
        room.setFloor(request.getFloor());
        room.setCreatedAt(Instant.now());
        Room saved = roomRepository.save(room);
        return RoomResponseDTO.builder()
                .id(saved.getId())
                .roomNumber(saved.getRoomNumber())
                .floor(saved.getFloor())
                .roomType(saved.getRoomType())
                .price(saved.getPrice())
                .available(saved.isAvailable())
                .build();
    }

    @Override
    @Cacheable(value = "rooms")
    public PaginationResponse<RoomResponseDTO> getAllRooms(Pageable pageable) {
        Page<Room> page = roomRepository.findAll(pageable);

        List<RoomResponseDTO> content = page.stream()
                .map(room -> RoomResponseDTO.builder()
                        .id(room.getId())
                        .roomNumber(room.getRoomNumber())
                        .floor(room.getFloor())
                        .available(room.isAvailable())
                        .price(room.getPrice())
                        .roomType(room.getRoomType())
                        .build())
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
}
