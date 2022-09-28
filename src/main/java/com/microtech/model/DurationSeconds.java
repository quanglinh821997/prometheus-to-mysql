package com.microtech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "go_gc_duration_seconds")
public class DurationSeconds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "instance")
    private String instance;
    @Column(name = "job")
    private String job;
    @Column(name = "quantile")
    private Float quantile;
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Column(name = "value")
    private Timestamp value;


}
