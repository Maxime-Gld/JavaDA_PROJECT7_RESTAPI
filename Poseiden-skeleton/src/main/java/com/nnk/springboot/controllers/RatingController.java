package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.impl.RatingServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class RatingController {
    @Autowired
    private RatingServiceImpl ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model, HttpServletRequest request) {
        // récupère la liste des Ratings et l'envoie au modèle
        model.addAttribute("ratings", ratingService.getAllRatings());
        model.addAttribute("httpServletRequest", request);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // vérification des erreurs de validation
        // si valid, enregistre le Rating et redirige vers la liste des Ratings
        if (!result.hasErrors()) {
            ratingService.createRating(rating);
            return "redirect:/rating/list";
        }
        // rediriger vers la page d'ajout du Rating
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // récupère le Rating à mettre à jour et l'envoie au modèle
        Rating rating = ratingService.getRatingById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
            BindingResult result, Model model) {
        // verifie les champs obligatoires
        // si valid, met à jour le Rating et redirige vers la liste des Ratings
        if (!result.hasErrors()) {
            ratingService.updateRating(id, rating);
        }
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // supprime le Rating et redirige vers la liste des Ratings
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
