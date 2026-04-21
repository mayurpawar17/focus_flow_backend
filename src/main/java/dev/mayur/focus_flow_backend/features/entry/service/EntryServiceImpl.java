package dev.mayur.focus_flow_backend.features.entry.service;

import dev.mayur.focus_flow_backend.features.entry.dto.EntryRequestDTO;
import dev.mayur.focus_flow_backend.features.entry.dto.EntryResponseDTO;
import dev.mayur.focus_flow_backend.features.entry.entity.Entry;
import dev.mayur.focus_flow_backend.features.entry.repository.EntryRepository;
import dev.mayur.focus_flow_backend.features.summary.dto.SummaryResponseDTO;
import dev.mayur.focus_flow_backend.features.summary.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final AiService aiService;

    @Override
    public EntryResponseDTO createEntry(Long userId, EntryRequestDTO request) {

        Entry entry = Entry.builder().userId(userId).title(request.getTitle()).category(request.getCategory()).timeSpent(request.getTimeSpent()).createdAt(LocalDateTime.now()).build();

        Entry saved = entryRepository.save(entry);

        return mapToDTO(saved);
    }

    @Override
    public List<EntryResponseDTO> getTodayEntries(Long userId) {

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);

        return entryRepository.findByUserIdAndCreatedAtBetween(userId, start, end).stream().map(this::mapToDTO).toList();
    }

    @Override
    public SummaryResponseDTO getTodaySummary(Long userId) {

        List<Entry> entries = getTodayEntriesRaw(userId);

        if (entries.isEmpty()) {
            return SummaryResponseDTO.builder().summary("No activity recorded today.").insight("Start logging tasks to get insights.").totalTasks(0).build();
        }

        StringBuilder input = new StringBuilder("User tasks:\n");

        entries.forEach(e -> input.append("- ").append(e.getTitle()).append(" (").append(e.getTimeSpent() == null ? "?" : e.getTimeSpent()).append(" mins)\n"));

        String aiResponse = aiService.generateSummary(input.toString());
        String[] parts = aiResponse.split("Insight:");

        String summary = parts[0];
        String insight = parts.length > 1 ? parts[1] : "No insight available";

        return SummaryResponseDTO.builder().summary(summary).insight(insight.trim()).totalTasks(entries.size()).build();
    }

    private List<Entry> getTodayEntriesRaw(Long userId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);

        return entryRepository.findByUserIdAndCreatedAtBetween(userId, start, end);
    }

    private String getTopCategory(List<Entry> entries) {
        return entries.stream().filter(e -> e.getCategory() != null).collect(java.util.stream.Collectors.groupingBy(Entry::getCategory, java.util.stream.Collectors.counting())).entrySet().stream().max(java.util.Map.Entry.comparingByValue()).map(java.util.Map.Entry::getKey).orElse("various tasks");
    }

    @Override
    public EntryResponseDTO updateEntry(Long userId, Long entryId, EntryRequestDTO request) {

        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> new RuntimeException("Entry not found"));

        // 🔒 Security check (important)
        if (!entry.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        entry.setTitle(request.getTitle());
        entry.setCategory(request.getCategory());
        entry.setTimeSpent(request.getTimeSpent());

        Entry updated = entryRepository.save(entry);

        return mapToDTO(updated);
    }

    @Override
    public void deleteEntry(Long userId, Long entryId) {

        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> new RuntimeException("Entry not found"));

        // 🔒 Security check
        if (!entry.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        entryRepository.delete(entry);
    }

    private EntryResponseDTO mapToDTO(Entry entry) {
        return EntryResponseDTO.builder().id(entry.getId()).title(entry.getTitle()).category(entry.getCategory()).timeSpent(entry.getTimeSpent()).createdAt(entry.getCreatedAt()).build();
    }
}