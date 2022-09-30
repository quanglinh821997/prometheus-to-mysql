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
            MatrixResponse response = null;
            response = client.queryRange(metric, tenMinEarlier.toString(),
                    now.toString(), "15s", "");
            Long time2 = System.currentTimeMillis();
            List<DurationSeconds> durationSecondsList = parseMatrixResponse(response);
            DurationSecondsServiceImpl durationSecondsService = new DurationSecondsServiceImpl();
            durationSecondsService.insertData(metric, durationSecondsList);
            offsetDao.insert(metric, now);
            Long time3 = System.currentTimeMillis();
            System.out.println("t3 - t2 = " + (time3 - time2));
            System.out.println("t2 - t1 = " + (time2 - time1));
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
