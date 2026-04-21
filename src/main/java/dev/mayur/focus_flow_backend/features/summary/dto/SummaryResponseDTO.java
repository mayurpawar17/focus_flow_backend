package dev.mayur.focus_flow_backend.features.summary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SummaryResponseDTO {

    private String summary;
    private String insight;
    private Integer totalTasks;
}