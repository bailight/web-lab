package com.back.repository;

import com.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String login);

    boolean existsUser(String login);

}