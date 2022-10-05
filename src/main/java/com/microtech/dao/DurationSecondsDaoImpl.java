package com.microtech.dao;

import com.microtech.PrometheusApiClient;
import com.microtech.model.DurationSeconds;
import com.microtech.model.MatrixResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DurationSecondsDaoImpl implements DurationSecondsDao {


    @Override
    public void insertData(String metric, Set<String> attributes, List<DurationSeconds> durationSecondsList) {

        try {
            PrometheusApiClient client = new PrometheusApiClient("https://prometheus.micro-tech.com.vn");
            Long now = System.currentTimeMillis() / 1000;
            OffsetDaoImpl offsetDao = new OffsetDaoImpl();
            Long offset = offsetDao.getOffset(metric);
            Long tenMinEarlier = offset == null ? now - 5 * 60 : offset;
            MatrixResponse response = client.queryRange(metric, tenMinEarlier.toString(),
                    now.toString(), "15s", "");

            StringBuilder sql = new StringBuilder("insert into prometheus." + metric + "(");
            for (var attribute : attributes) {
                if (!attribute.equals("__name__")) {
                    sql.append(attribute).append(" , ");
                }
            }
            sql.append("timestamp").append(" ,");
            sql.append("value").append(" ) values (");
            for (var attribute : attributes) {
                if (!attribute.equals("__name__")) {
                    sql.append("?,");
                }
            }
            sql.append("?,?)");

            Connection conn = DataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql.toString());

            var resultList = response.getData().getResult();
            for (int result = 0; result < resultList.size(); result++) {
                ArrayList<String> values = new ArrayList<>(response.getData().getResult().get(result).getMetric().values());

                    for (int i = 1; i < values.size(); i++) {
                        String value = values.get(i);
                        preparedStatement.setString(i, value.toString());
                    }

                    ArrayList<List<Float>> items = new ArrayList<>(response.getData().getResult().get(result).getValues());
                    for (int n = 0; n < items.size(); n++) {
                        var index = items.get(n);
                        for (int j = 0; j < index.size(); j++) {
                            var item = index.get(j);
                            System.out.println( j+":" +item);
                            preparedStatement.setString(j + 5, item.toString());
                        }
                    }
                    preparedStatement.addBatch();
                    preparedStatement.executeUpdate();
                    conn.setAutoCommit(false);
                    preparedStatement.clearBatch();
            }
            preparedStatement.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable(String metric, Set<String> attributes, List<DurationSeconds> durationSecondsList) {
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS prometheus." + metric + "(");
        for (String attribute : attributes) {
            if (!attribute.equals("__name__")) {
                query.append(attribute).append(" VARCHAR(200),");
            }
        }
        query.append("timestamp").append(" VARCHAR(200),");
        query.append("value").append(" VARCHAR(200),");
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        try (Connection conn = DataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query.toString())) {
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


