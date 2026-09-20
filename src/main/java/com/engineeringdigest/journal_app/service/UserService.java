package com.engineeringdigest.journal_app.service;

import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(UserEntity userEntity) {
        userRepository.save(userEntity );
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public UserEntity getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
