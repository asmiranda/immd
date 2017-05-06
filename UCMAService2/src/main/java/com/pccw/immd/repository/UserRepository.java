package com.pccw.immd.repository;

import com.pccw.immd.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vagrant on 5/3/17.
 */
@Repository
public interface UserRepository extends MongoRepository<User,String>{

    User findByUsername(String username);
}
