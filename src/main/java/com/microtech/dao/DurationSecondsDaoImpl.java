package com.microtech.dao;

import com.microtech.model.DurationSeconds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DurationSecondsDaoImpl implements DurationSecondsDao {



    @Override
    public void insertData(String metric, List<DurationSeconds> durationSecondsList) {

        String sql = "insert into prometheus." + metric + " (instance,job,quantile,timestamp,value) values (?,?,?,?,?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            int count = 1;
            int batchSize = 500;
            long startTime = System.currentTimeMillis();
            for (DurationSeconds durationSeconds : durationSecondsList) {

                preparedStatement.setString(1, durationSeconds.getInstance());
                preparedStatement.setString(2, durationSeconds.getJob());
                preparedStatement.setFloat(3, durationSeconds.getQuantile() == null ? 0.0f : durationSeconds.getQuantile());
                preparedStatement.setTimestamp(4, durationSeconds.getTimestamp());
                preparedStatement.setFloat(5, durationSeconds.getValue());
                preparedStatement.addBatch();
                if (count % batchSize == 0) {

                    preparedStatement.executeBatch();
                    conn.commit();
                    preparedStatement.clearBatch();
                }
                count++;
            }

            preparedStatement.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            System.out.println(" success !");
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            System.out.println(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


