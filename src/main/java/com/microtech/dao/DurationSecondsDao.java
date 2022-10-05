package com.microtech.dao;

import com.microtech.model.DurationSeconds;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Repository
public interface DurationSecondsDao {
    void insertData(String metric, Set<String> attributes, List<DurationSeconds> durationSecondsList) throws SQLException;

    void createTable(String metric, Set<String> attributes, List<DurationSeconds> durationSecondsList);
}
