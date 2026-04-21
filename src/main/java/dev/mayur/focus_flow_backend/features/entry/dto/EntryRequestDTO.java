package dev.mayur.focus_flow_backend.features.entry.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntryRequestDTO {
    @NotBlank
    private String title;

    private String category;

    private Integer timeSpent;
}
