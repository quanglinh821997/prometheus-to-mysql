package com.microtech.service;

import com.microtech.dao.DurationSecondsDaoImpl;
import com.microtech.model.DurationSeconds;

import java.sql.SQLException;
import java.util.List;

public class DurationSecondsServiceImpl implements DurationSecondsService{
    @Override
    public void insertData(String metric, List<DurationSeconds> durationSecondsList) throws SQLException {
        DurationSecondsDaoImpl durationSecondsSecondsDao = new DurationSecondsDaoImpl();
        durationSecondsSecondsDao.insertData(metric, durationSecondsList);
    }



}
