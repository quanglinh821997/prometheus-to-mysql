package com.microtech.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
public class MatrixResponse {
    String status;
    MatrixData data;

    @Getter
    @ToString
    public static class MatrixData {
        String resultType;
        List<MatrixResult> result;
    }

    @Getter
    @ToString
    public static class MatrixResult {
        Map<String, String> metric;
        List<List<Float>> values;

        @Override
        public String toString() {
            return String.format(
                    "metric: %s\nvalues: %s",
                    metric.toString(),
                    values == null ? "" : values.toString()
            );
        }
    }
}
