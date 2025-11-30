package com.back.repository;

import com.back.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByUser_userid(Long id);

    void deleteByUser_userid(Long id);
}