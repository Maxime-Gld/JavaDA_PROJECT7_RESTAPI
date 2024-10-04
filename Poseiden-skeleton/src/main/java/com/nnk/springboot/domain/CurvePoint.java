package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NumberFormat
    @NotBlank(message = "CurveId is mandatory")
    Integer curveId;

    Timestamp asOfDate;

    @NumberFormat
    @NotBlank(message = "CurveId is mandatory")
    Double term;

    @NumberFormat
    @NotBlank(message = "CurveId is mandatory")
    Double value;

    @CreationTimestamp
    Timestamp creationDate;

    public CurvePoint(int curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
