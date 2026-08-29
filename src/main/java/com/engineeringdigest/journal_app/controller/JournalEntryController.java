package com.engineeringdigest.journal_app.controller;

import com.engineeringdigest.journal_app.entity.JournalEntry;
import com.engineeringdigest.journal_app.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAll() {
        List<JournalEntry> all = journalEntryService.getAll();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<List<JournalEntry>>(all, HttpStatus.OK);
        }
        return new ResponseEntity<List<JournalEntry>>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry req) {
        try {
            req.setDate(LocalDateTime.now());
            JournalEntry journalEntry = journalEntryService.saveEntry(req);
            return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>( HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        journalEntryService.deleteById(id);
        return new ResponseEntity<JournalEntry>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry req) {
        JournalEntry old = journalEntryService.getById(id).orElse(null);
        if(old != null) {
            old.setTitle(req.getTitle() != null && !req.getTitle().isEmpty() ? req.getTitle() : old.getTitle());
            old.setContent(req.getContent() != null && !req.getContent().isEmpty() ? req.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<JournalEntry>(old, HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }
}
