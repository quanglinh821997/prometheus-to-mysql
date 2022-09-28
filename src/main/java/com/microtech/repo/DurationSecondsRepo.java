package com.microtech.repo;

import com.microtech.model.DurationSeconds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DurationSecondsRepo extends JpaRepository<DurationSeconds, Long> {
}
