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


public interface DurationSecondsService {

    void insertData(List<DurationSeconds> durationSecondsList);
}
