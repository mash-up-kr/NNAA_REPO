package com.na.backend.repository;

import com.na.backend.entity.Heart;
import com.na.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart,Long> {
    List<String> findQuestionIdsById(String userId);
}
