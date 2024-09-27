package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;

@Service
public interface RatingService {

    void createRating(Rating rating);

    void updateRating(int id, Rating rating);

    void deleteRating(Integer id);

    Rating findById(Integer id);

    List<Rating> findAll();
}