package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;

@Service
public interface RatingService {

    void createRating(Rating rating);

    void updateRating(Integer id, Rating rating);

    void deleteRating(Integer id);

    Rating getRatingById(Integer id);

    List<Rating> getAllRatings();
}