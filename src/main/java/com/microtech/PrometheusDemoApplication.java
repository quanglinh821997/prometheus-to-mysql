package com.microtech;

import com.microtech.dao.DurationSecondsDao;
import com.microtech.dao.DurationSecondsDaoImpl;
import com.microtech.model.DurationSeconds;
import com.microtech.model.MatrixResponse;
import com.microtech.service.DurationSecondsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class PrometheusDemoApplication {

	public static void main(String[] args) throws IOException {

		DurationSecondsDao durationSecondsDao = null;

		PrometheusApiClient client = new PrometheusApiClient("https://prometheus.micro-tech.com.vn");
		Long now = System.currentTimeMillis() / 1000;
		Long tenMinEarlier = now - 10 * 60;
		MatrixResponse response = null;
		response = client.queryRange("go_gc_duration_seconds", tenMinEarlier.toString(),
				now.toString(), "30s", "");
		List<DurationSeconds> durationSecondsList = parseMatrixResponse(response);
		DurationSecondsServiceImpl durationSecondsService = new DurationSecondsServiceImpl();
		durationSecondsService.insertData(durationSecondsList);

	}

	private static List<DurationSeconds> parseMatrixResponse(MatrixResponse response) {
		List<DurationSeconds> result = new ArrayList<>();
		for (MatrixResponse.MatrixResult matrixResult : response.getData().getResult()) {
			for (List<Float> values : matrixResult.getValues()) {
				result.add(DurationSeconds.builder()
						.instance(matrixResult.getMetric().get("instance"))
						.job(matrixResult.getMetric().get("job"))
						.quantile(Float.parseFloat(matrixResult.getMetric().get("quantile")))
						.timestamp(new Timestamp(values.get(0).longValue() * 1000))
						.value(values.get(1))
						.build());
			}
		}
		return result;
	}

}
