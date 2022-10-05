package com.microtech.service;

import com.microtech.model.DurationSeconds;
import com.microtech.dao.DurationSecondsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;


public interface DurationSecondsService {

    void insertData(String metric, Set<String> attributes, List<DurationSeconds> durationSecondsList) throws SQLException;

    void createTable(String metric, Set<String> attributes, List<DurationSeconds> durationSecondsList) throws SQLException;


}
