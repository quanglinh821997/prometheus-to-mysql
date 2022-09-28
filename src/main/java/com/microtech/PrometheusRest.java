package com.microtech;

import com.microtech.model.MatrixResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrometheusRest {
    @GET("api/v1/query_range")
    Call<MatrixResponse> queryRange(
            @Query("query") String query,
            @Query("start") String start,
            @Query("end") String end,
            @Query("step") String step,
            @Query("timeout") String timeout
    );


}
