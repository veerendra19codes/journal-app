package com.engineeringdigest.journal_app.service;

import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.repository.UserRepository;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveEntry(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public void saveNewUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(List.of("USER"));
        userRepository.save(userEntity);
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
