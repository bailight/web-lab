package com.back.repository;

import com.back.entity.Result;
import com.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findResultByUser(User user);

    void deleteByUser(User user);
}