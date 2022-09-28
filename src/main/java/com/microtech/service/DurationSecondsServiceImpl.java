package com.microtech.service;

import com.microtech.dao.DurationSecondsDaoImpl;
import com.microtech.model.DurationSeconds;

import java.util.List;

public class DurationSecondsServiceImpl implements DurationSecondsService{
    @Override
    public void insertData(List<DurationSeconds> durationSecondsList) {
        DurationSecondsDaoImpl durationSecondsSecondsDao = new DurationSecondsDaoImpl();
        durationSecondsSecondsDao.insertData(durationSecondsList);
    }
}
