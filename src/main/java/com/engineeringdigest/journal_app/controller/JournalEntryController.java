package com.engineeringdigest.journal_app.controller;

import com.engineeringdigest.journal_app.entity.JournalEntry;
import com.engineeringdigest.journal_app.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping()
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping()
    public Boolean createEntry(@RequestBody JournalEntry req) {
        req.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(req);
        return true;
    }

    @GetMapping("id/{id}")
    public JournalEntry getJournalById(@PathVariable ObjectId id) {
        return journalEntryService.getById(id).orElse(null);
    }

    @DeleteMapping("id/{id}")
    public Boolean deleteJournalById(@PathVariable ObjectId id) {
        journalEntryService.deleteById(id);
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry req) {
        JournalEntry old = journalEntryService.getById(id).orElse(null);
        if(old != null) {
            old.setTitle(req.getTitle() != null && !req.getTitle().isEmpty() ? req.getTitle() : old.getTitle());
            old.setContent(req.getContent() != null && !req.getContent().isEmpty() ? req.getContent() : old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
