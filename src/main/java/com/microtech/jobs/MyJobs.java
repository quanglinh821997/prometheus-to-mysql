package com.microtech.jobs;

import com.microtech.PrometheusApiClient;
import com.microtech.dao.OffsetDaoImpl;
import com.microtech.model.DurationSeconds;
import com.microtech.model.MatrixResponse;
import com.microtech.service.DurationSecondsServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MyJobs implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        insertData(jobExecutionContext.getJobDetail().getJobDataMap().get("metric").toString());
    }

    private static void insertData (String metric){
        try {
            Long time1 = System.currentTimeMillis();
            PrometheusApiClient client = new PrometheusApiClient("https://prometheus.micro-tech.com.vn");
            Long now = System.currentTimeMillis() / 1000;
            OffsetDaoImpl offsetDao = new OffsetDaoImpl();
            Long offset = offsetDao.getOffset(metric);
            Long tenMinEarlier = offset == null ? now - 5 * 60 : offset;
            MatrixResponse response = client.queryRange(metric, tenMinEarlier.toString(),
                    now.toString(), "60s", "");

//            response.getData().getResult().get(0).getMetric().values().forEach(key -> {
//                if (!metric.equals(key)) {
//                    System.out.println(key);
//                }
//            });

//            ArrayList<String> values = new ArrayList<>(response.getData().getResult().get(0).getMetric().values());
//            for (int i = 1 ; i < values.size(); i++) {
//                String value = values.get(i);
//                System.out.println(value);
//            }

            ArrayList<List<Float>> values = new ArrayList<>(response.getData().getResult().get(0).getValues());
            for (int i = 1 ; i < values.size(); i++) {
                var value = values.get(i);
                for (int j=0;j<value.size();j++){
                    var item = value.get(j);
                    System.out.println(j +":"+item);
                }

            }


            List<DurationSeconds> durationSecondsList = parseMatrixResponse(response);
            DurationSecondsServiceImpl durationSecondsService = new DurationSecondsServiceImpl();
            durationSecondsService.createTable(metric, response.getData().getResult().get(0).getMetric().keySet(),durationSecondsList);
            durationSecondsService.insertData(metric, response.getData().getResult().get(0).getMetric().keySet(),durationSecondsList);
            offsetDao.insert(metric, now);

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<DurationSeconds> parseMatrixResponse(MatrixResponse response) {
        List<DurationSeconds> result = new ArrayList<>();
        for (MatrixResponse.MatrixResult matrixResult : response.getData().getResult()) {
            for (List<Float> values : matrixResult.getValues()) {
                result.add(DurationSeconds.builder()
                        .instance(matrixResult.getMetric().get("instance"))
                        .job(matrixResult.getMetric().get("job"))
                        .quantile(matrixResult.getMetric().get("quantile") == null ? null : Float.parseFloat(matrixResult.getMetric().get("quantile")))
                        .timestamp(new Timestamp(values.get(0).longValue() * 1000))
                        .value(values.get(1))
                        .build());
            }
        }
        return result;
    }
}
