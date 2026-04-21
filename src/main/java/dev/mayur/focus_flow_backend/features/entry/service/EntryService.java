package dev.mayur.focus_flow_backend.features.entry.service;


import dev.mayur.focus_flow_backend.features.entry.dto.EntryRequestDTO;
import dev.mayur.focus_flow_backend.features.entry.dto.EntryResponseDTO;
import dev.mayur.focus_flow_backend.features.summary.dto.SummaryResponseDTO;

import java.util.List;

public interface EntryService {

    EntryResponseDTO createEntry(Long userId, EntryRequestDTO request);

    List<EntryResponseDTO> getTodayEntries(Long userId);

    SummaryResponseDTO getTodaySummary(Long userId);
}