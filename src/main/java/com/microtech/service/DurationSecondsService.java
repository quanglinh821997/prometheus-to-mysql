package com.microtech.service;

import com.microtech.model.DurationSeconds;
import com.microtech.repo.DurationSecondsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class DurationSecondsService {

    @Autowired
    private DurationSecondsRepo durationSecondsRepo;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private void insertData(List<DurationSeconds> durationSeconds) {
        batchInsert(durationSeconds);
    }

    private void batchInsert(List<DurationSeconds> durationSeconds) {
        jdbcTemplate.batchUpdate("insert into prometheus-demo.durationSeconds (instance, job, quantile, timestamp, value)" +
                " values (?,?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DurationSeconds item = durationSeconds.get(i);
                ps.setString(1, item.getInstance());
                ps.setString(2, item.getJob());
                ps.setFloat(3, item.getQuantile());
                ps.setTimestamp(4, item.getTimestamp());
                ps.setTimestamp(5, item.getValue());
            }

            @Override
            public int getBatchSize() {
                return durationSeconds.size();
            }
        });
    }


}
