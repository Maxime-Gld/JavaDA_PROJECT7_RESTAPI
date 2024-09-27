package com.nnk.springboot.testImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.impl.RatingServiceImpl;

public class RatingServiceImplTest {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    private Rating rating;

    /**
     * Méthode exécutée avant chaque test pour initialiser les objets nécessaires.
     */
    @BeforeEach
    public void setup() {
        // Initialise les mocks
        MockitoAnnotations.openMocks(this);

        // Crée un objet Rating pour les tests
        rating = new Rating("moodysRating Test", "sandPRating Test", "fitchRating Test", 1);
    }

    /**
     * Teste la création d'une Rating.
     */
    @Test
    public void testCreateRating() {
        // Configure le comportement du mock pour la méthode save
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);

        // Appelle la méthode à tester
        ratingService.createRating(rating);

        // Vérifie que la méthode save du repository a été appelée une fois
        verify(ratingRepository, times(1)).save(ratingCaptor.capture());

        // Vérifie que les informations de la Rating sont correctes
        assertEquals(rating.getMoodysRating(), ratingCaptor.getValue().getMoodysRating());
        assertEquals(rating.getSandPRating(), ratingCaptor.getValue().getSandPRating());
        assertEquals(rating.getFitchRating(), ratingCaptor.getValue().getFitchRating());
    }

    /**
     * Teste la récupération d'une Rating par son ID lorsqu'elle existe.
     */
    @Test
    public void testGetRatingById() {
        // Configure le mock pour retourner une Rating existante
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        // Appelle la méthode à tester
        Rating foundRating = ratingService.getRatingById(1);

        // Vérifie que la Rating trouvée n'est pas nulle
        assertNotNull(foundRating);

        // Vérifie que les informations de la Rating trouvée correspondent à celles
        // attendues
        assertEquals(rating.getMoodysRating(), foundRating.getMoodysRating());
        assertEquals(rating.getSandPRating(), foundRating.getSandPRating());
        assertEquals(rating.getFitchRating(), foundRating.getFitchRating());

        // Vérifie que la méthode findById du repository a été appelée une fois
        verify(ratingRepository, times(1)).findById(1);
    }

    /**
     * Teste la récupération d'une Rating par son ID lorsqu'elle n'existe pas.
     */
    @Test
    public void testGetRatingByIdNotFound() {
        // Configure le mock pour retourner un Optional vide
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        // Appelle la méthode à tester
        Rating foundRating = ratingService.getRatingById(1);

        // Vérifie que la Rating trouvée est nulle (puisqu'elle n'existe pas)
        assertNull(foundRating);

        // Vérifie que la méthode findById du repository a été appelée une fois
        verify(ratingRepository, times(1)).findById(1);
    }

    /**
     * Teste la récupération de toutes les Rating.
     */
    @Test
    public void testGetAllRating() {
        // Crée une liste de Rating pour le test
        List<Rating> ratings = Arrays.asList(rating);

        // Configure le mock pour retourner la liste de Rating
        when(ratingRepository.findAll()).thenReturn(ratings);

        // Appelle la méthode à tester
        List<Rating> allRatings = ratingService.getAllRatings();

        // Vérifie que la taille de la liste est correcte
        assertEquals(1, allRatings.size());

        // Vérifie que les informations de la première Rating correspondent à celles
        // attendues
        assertEquals(rating.getMoodysRating(), ratings.get(0).getMoodysRating());

        // Vérifie que la méthode findAll du repository a été appelée une fois
        verify(ratingRepository, times(1)).findAll();
    }

    /**
     * Teste la mise à jour d'une Rating existante.
     */
    @Test
    public void testUpdateRating() {
        // Crée une nouvelle Rating avec les nouvelles informations
        Rating updatedRating = new Rating();

        // Configure le mock pour retourner la Rating existante lors de la recherche
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        // Configure le mock pour retourner la Rating mise à jour lors de la sauvegarde
        when(ratingRepository.save(rating)).thenReturn(rating);

        // Appelle la méthode à tester
        ratingService.updateRating(1, updatedRating);

        // Vérifie que les informations ont été mises à jour correctement

        // Vérifie que les méthodes du repository ont été appelées le nombre de fois
        // attendu
        verify(ratingRepository, times(1)).findById(1);
        verify(ratingRepository, times(1)).save(rating);
    }

    /**
     * Teste la mise à jour d'une Rating qui n'existe pas.
     *
     * 
     */
    @Test
    public void testUpdateRatingNotFound() {
        // Crée une nouvelle Rating avec les nouvelles informations
        Rating updatedRating = new Rating();

        // Configure le mock pour retourner un Optional vide lors de la recherche
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        // Appelle la méthode à tester
        ratingService.updateRating(1, updatedRating);

        // Vérifie que les méthodes findById et save n'ont pas été appelées
        verify(ratingRepository, times(1)).findById(1);
        verify(ratingRepository, never()).save(any(Rating.class));
    }

    /**
     * Teste la suppression d'une Rating.
     */
    @Test
    public void testDeleteRating() {
        // Configure le mock pour retourner la Rating existante
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        // Configure le mock pour ne rien faire lors de la suppression
        doNothing().when(ratingRepository).delete(rating);

        // Appelle la méthode à tester
        ratingService.deleteRating(rating.getId());

        // Vérifie que la méthode delete du repository a été appelée une fois
        verify(ratingRepository, times(1)).deleteById(rating.getId());
    }
}
