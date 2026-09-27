package com.engineeringdigest.journal_app.controller;

import com.engineeringdigest.journal_app.entity.JournalEntry;
import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.service.JournalEntryService;
import com.engineeringdigest.journal_app.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.getByUserName(userName);
        List<JournalEntry> all = userEntity.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<List<JournalEntry>>(all, HttpStatus.OK);
        }
        return new ResponseEntity<List<JournalEntry>>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.getByUserName(userName);
        List<JournalEntry> journalEntries = userEntity.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
        if(!journalEntries.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
            if(journalEntry.isPresent()) {
                return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<JournalEntry>( HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.getByUserName(userName);
        List<JournalEntry> journalEntries = userEntity.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
        if(!journalEntries.isEmpty()) {
            journalEntryService.deleteById(id, userName);
            return new ResponseEntity<JournalEntry>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry req
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userEntity = userService.getByUserName(userName);
        List<JournalEntry> journalEntries = userEntity.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
        if(!journalEntries.isEmpty()) {
            JournalEntry old = journalEntries.get(0);
            if (old != null) {
                old.setTitle(req.getTitle() != null && !req.getTitle().isEmpty() ? req.getTitle() : old.getTitle());
                old.setContent(req.getContent() != null && !req.getContent().isEmpty() ? req.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<JournalEntry>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }
}
