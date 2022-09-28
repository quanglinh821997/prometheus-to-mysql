package com.microtech.dao;

import com.microtech.model.DurationSeconds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DurationSecondsDao {
    void insertData(List<DurationSeconds> durationSecondsList);
}
