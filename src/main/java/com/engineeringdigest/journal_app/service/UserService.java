package com.engineeringdigest.journal_app.service;

import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveEntry(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public void saveNewUser(UserEntity userEntity) {
        try{
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(List.of("USER"));
            userRepository.save(userEntity);
        } catch(Exception e) {
            System.out.println("Exception: " + e);
//            without @Slf4j
//            logger.info("Error saving user entry for user: {}", userEntity.getUserName(), e);

//            with @Slf4j
            log.info("Error saving user entry for user: {}", userEntity.getUserName(), e);
            log.trace("TRACE logging");
            log.debug("DEBUG logging");
            log.info("INFO logging");
            log.warn("WARN logging");
            log.error("ERROR logging");
        }
    }

    public Optional<UserEntity> getById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void deleteByUsername(String userName) {
        userRepository.deleteByUserName(userName);
    }

    public UserEntity getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
