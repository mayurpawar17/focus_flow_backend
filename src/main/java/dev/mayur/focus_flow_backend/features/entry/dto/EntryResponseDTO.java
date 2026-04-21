package dev.mayur.focus_flow_backend.features.entry.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EntryResponseDTO {
    private Long id;
    private String title;
    private String category;
    private Integer timeSpent;
    private LocalDateTime createdAt;
}
