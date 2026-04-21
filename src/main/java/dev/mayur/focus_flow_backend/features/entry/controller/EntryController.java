package dev.mayur.focus_flow_backend.features.entry.controller;


import dev.mayur.focus_flow_backend.features.entry.dto.EntryRequestDTO;
import dev.mayur.focus_flow_backend.features.entry.dto.EntryResponseDTO;
import dev.mayur.focus_flow_backend.features.entry.service.EntryService;
import dev.mayur.focus_flow_backend.features.summary.dto.SummaryResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public EntryResponseDTO create(@Valid @RequestBody EntryRequestDTO request) {
        return service.createEntry(userId, request);
    }

    @GetMapping("/today")
    public List<EntryResponseDTO> getToday() {
        return service.getTodayEntries(userId);
    }

    @GetMapping("/summary/today")
    public SummaryResponseDTO getSummary() {
        return service.getTodaySummary(userId);
    }
}