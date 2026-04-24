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

    private final EntryService entryService;

    // TEMP: hardcoded userId (replace with auth later)
    private final Long userId = 1L;

    //create new entry
    @PostMapping
    public ResponseEntity<ApiResponse<EntryResponseDTO>> create(@Valid @RequestBody EntryRequestDTO request) {
        EntryResponseDTO data = entryService.createEntry(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Entry created successfully", data));
    }

    //get today entries
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<EntryResponseDTO>>> getToday() {
        List<EntryResponseDTO> data = entryService.getTodayEntries(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Entry fetched successfully", data));

    }

    //get summary of today entries
    @GetMapping("/summary/today")
    public ResponseEntity<ApiResponse<SummaryResponseDTO>> getSummary() {
        SummaryResponseDTO data = entryService.getTodaySummary(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Summary fetched successfully", data));

    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EntryResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody EntryRequestDTO request) {
        EntryResponseDTO data = entryService.updateEntry(userId, id, request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Entry fetched successfully", data));

    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        entryService.deleteEntry(userId, id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Entry deleted successfully", null));

    }
}