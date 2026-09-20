package com.engineeringdigest.journal_app.repository;

import com.engineeringdigest.journal_app.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

    UserEntity findByUserName(String username);

}
