package com.engineeringdigest.journal_app.service;

import com.engineeringdigest.journal_app.entity.JournalEntry;
import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {
        try {
            UserEntity userEntity = userService.getByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
            userEntity.getJournalEntries().add(savedJournalEntry);
            // userEntity.setUserName(null);
            userService.saveEntry(userEntity);

            return savedJournalEntry;
        } catch(Exception e) {
            System.out.println(e);
            throw new RuntimeException("Exception occured: ", e);
        }

    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName) {
        UserEntity userEntity = userService.getByUserName(userName);
        userEntity.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(userEntity);
        journalEntryRepository.deleteById(id);
    }

}
