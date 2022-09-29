package com.microtech.dao;

import com.microtech.model.DurationSeconds;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface DurationSecondsDao {
    void insertData(String metric, List<DurationSeconds> durationSecondsList) throws SQLException;


}
