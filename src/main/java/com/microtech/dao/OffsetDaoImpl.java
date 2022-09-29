package com.microtech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OffsetDaoImpl implements OffsetDao {
    @Override
    public void insert(String metric, Long currentOffset) throws SQLException {

        String sql = "replace into prometheus.offset(id, `offset`) values (?,?)";
        try (Connection conn = com.microtech.dao.DataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, metric);
            preparedStatement.setLong(2, currentOffset);
            preparedStatement.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long getOffset(String id) throws SQLException {

        String sql = "SELECT * FROM prometheus.`offset` WHERE id = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long offset = resultSet.getLong("offset");
                return offset;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

}


