package com.microtech;

import com.microtech.model.MatrixResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class PrometheusApiClient {

    public String baseUrl;
    private Retrofit retrofit;
    private PrometheusRest service;

    public PrometheusApiClient(String baseUrl) {
        this.baseUrl = baseUrl;

        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(PrometheusRest.class);
    }

    public MatrixResponse queryRange(String query, String start, String end, String step, String timeout) throws IOException {
        return service.queryRange(query, start, end, step, timeout).execute().body();
    }
}
