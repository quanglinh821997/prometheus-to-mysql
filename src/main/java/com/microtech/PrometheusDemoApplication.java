package com.microtech;

import com.microtech.model.MatrixResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class PrometheusDemoApplication {

	public static void main(String[] args) throws IOException {
		PrometheusApiClient client = new PrometheusApiClient("https://prometheus.micro-tech.com.vn");
		Long now = System.currentTimeMillis() / 1000;
		Long tenMinEarlier = now - 10 * 60;
		MatrixResponse response = client.queryRange("go_gc_duration_seconds", tenMinEarlier.toString(),
				now.toString(), "1m", "");
		System.out.println(response.getData());
	}

}
