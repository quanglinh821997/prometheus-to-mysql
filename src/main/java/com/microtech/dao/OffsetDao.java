package com.microtech.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface OffsetDao {

    void insert(String metric, Long currentOffset) throws SQLException;

    Long getOffset(String key) throws SQLException;


}
