package com.engineeringdigest.journal_app.controller;

import com.engineeringdigest.journal_app.entity.JournalEntry;
import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.service.JournalEntryService;
import com.engineeringdigest.journal_app.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesByUser(@PathVariable String userName) {
        UserEntity userEntity = userService.getByUserName(userName);
        List<JournalEntry> all = userEntity.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<List<JournalEntry>>(all, HttpStatus.OK);
        }
        return new ResponseEntity<List<JournalEntry>>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry req, @PathVariable String userName) {
        try {
            req.setDate(LocalDateTime.now());
            JournalEntry journalEntry = journalEntryService.saveEntry(req, userName);
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


    @DeleteMapping("id/{userName}/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id, @PathVariable String userName) {
        journalEntryService.deleteById(id, userName);
        return new ResponseEntity<JournalEntry>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry req,
            @PathVariable String userName
    ) {
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
