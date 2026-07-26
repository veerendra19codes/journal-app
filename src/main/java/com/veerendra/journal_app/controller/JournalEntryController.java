package com.veerendra.journal_app.controller;

import com.veerendra.journal_app.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping()
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping()
    public Boolean createEntry(@RequestBody JournalEntry req) {
        journalEntries.put(req.getId(), req);
        return true;
    }

    @GetMapping("id/{id}")
    public JournalEntry getJournalById(@PathVariable Long id) {
        return journalEntries.get(id);
    }

    @DeleteMapping("id/{id}")
    public JournalEntry deleteJournalById(@PathVariable Long id) {
        return journalEntries.remove(id);
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalById(@PathVariable Long id, @RequestBody JournalEntry req) {
        return journalEntries.put(id, req);
    }
}
