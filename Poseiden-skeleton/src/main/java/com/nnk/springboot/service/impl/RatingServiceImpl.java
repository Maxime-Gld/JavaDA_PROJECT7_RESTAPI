package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void createRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void updateRating(int id, Rating ratingUpdated) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            Rating ratingFound = rating.get();
            ratingFound.setMoodysRating(ratingUpdated.getMoodysRating());
            ratingFound.setSandPRating(ratingUpdated.getSandPRating());
            ratingFound.setFitchRating(ratingUpdated.getFitchRating());
            ratingFound.setOrderNumber(ratingUpdated.getOrderNumber());
            ratingRepository.save(ratingFound);
        }
    }

    @Override
    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public Rating getRatingById(Integer id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
