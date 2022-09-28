package com.microtech.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DurationSeconds {

    private Long id;

    private String instance;

    private String job;

    private Float quantile;

    private Timestamp timestamp;

    private Float value;


}
