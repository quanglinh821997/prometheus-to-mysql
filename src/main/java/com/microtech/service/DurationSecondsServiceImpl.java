package com.microtech.service;

import com.microtech.dao.DurationSecondsDaoImpl;
import com.microtech.model.DurationSeconds;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DurationSecondsServiceImpl implements DurationSecondsService{
    public void insertData(String metric, Set<String> attributes, List<DurationSeconds> durationSecondsList) throws SQLException {
        DurationSecondsDaoImpl durationSecondsSecondsDao = new DurationSecondsDaoImpl();
        durationSecondsSecondsDao.insertData(metric,attributes, durationSecondsList);
    }

    @Override
    public void createTable(String metric, Set<String> attributes, List<DurationSeconds> durationSecondsList) throws SQLException {
        DurationSecondsDaoImpl durationSecondsSecondsDao = new DurationSecondsDaoImpl();
        durationSecondsSecondsDao.createTable(metric,attributes,durationSecondsList);
    }


}
