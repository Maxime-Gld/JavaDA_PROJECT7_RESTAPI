package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;

/**
 * Implémentation du service pour gérer les entités Rating.
 */
@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    /**
     * Constructeur avec injection du repository Rating.
     * 
     * @param ratingRepository le repository de Rating.
     */
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Crée un nouvel objet Rating et l'enregistre dans le repository.
     * 
     * @param rating l'objet Rating à créer.
     */
    @Override
    public void createRating(Rating rating) {
        ratingRepository.save(rating);
    }

    /**
     * Met à jour un Rating existant en modifiant ses propriétés.
     * 
     * @param id            l'ID du Rating à mettre à jour.
     * @param ratingUpdated les nouvelles informations du Rating.
     */
    @Override
    public void updateRating(Integer id, Rating ratingUpdated) {
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

    /**
     * Supprime un Rating par son ID.
     * 
     * @param id l'ID du Rating à supprimer.
     */
    @Override
    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }

    /**
     * Récupère un Rating par son ID.
     * 
     * @param id l'ID du Rating.
     * @return le Rating correspondant, ou null s'il n'existe pas.
     */
    @Override
    public Rating getRatingById(Integer id) {
        return ratingRepository.findById(id).orElse(null);
    }

    /**
     * Récupère tous les objets Rating.
     * 
     * @return la liste de tous les Ratings.
     */
    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
