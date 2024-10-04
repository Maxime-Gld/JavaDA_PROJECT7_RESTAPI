package com.nnk.springboot.domain;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotBlank(message = "MoodysRating is mandatory")
    String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    String sandPRating;

    @NotBlank(message = "FicthRating is mandatory")
    String fitchRating;

    @NotBlank(message = "OrderNumber is mandatory")
    @NumberFormat
    Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
