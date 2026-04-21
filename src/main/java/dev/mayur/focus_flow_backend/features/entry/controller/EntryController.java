package dev.mayur.focus_flow_backend.features.entry.controller;


import dev.mayur.focus_flow_backend.core.dto.ApiResponse;
import dev.mayur.focus_flow_backend.features.entry.dto.EntryRequestDTO;
import dev.mayur.focus_flow_backend.features.entry.dto.EntryResponseDTO;
import dev.mayur.focus_flow_backend.features.entry.service.EntryService;
import dev.mayur.focus_flow_backend.features.summary.dto.SummaryResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entries")
@RequiredArgsConstructor
public class EntryController {

    private final EntryService service;

    // TEMP: hardcoded userId (replace with auth later)
    private final Long userId = 1L;

    @PostMapping
    public ResponseEntity<ApiResponse<EntryResponseDTO>> create(@Valid @RequestBody EntryRequestDTO request) {
        EntryResponseDTO data = service.createEntry(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Entry created successfully", data));
    }

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<EntryResponseDTO>>> getToday() {
        List<EntryResponseDTO> data = service.getTodayEntries(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Entry fetched successfully", data));

    }

    @GetMapping("/summary/today")
    public SummaryResponseDTO getSummary() {
        return service.getTodaySummary(userId);
    }

    @PutMapping("/{id}")
    public EntryResponseDTO update(@PathVariable Long id, @Valid @RequestBody EntryRequestDTO request) {
        return service.updateEntry(userId, id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteEntry(userId, id);
        return "Entry deleted successfully";
    }
}