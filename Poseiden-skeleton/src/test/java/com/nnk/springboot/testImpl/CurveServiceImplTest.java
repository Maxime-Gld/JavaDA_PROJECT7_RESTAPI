package com.nnk.springboot.testImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.impl.CurveServiceImpl;

/**
 * Classe de test pour {@link CurveServiceImpl}.
 */
public class CurveServiceImplTest {

    @InjectMocks
    private CurveServiceImpl curveService;

    @Mock
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePoint;

    /**
     * Méthode exécutée avant chaque test pour initialiser les objets nécessaires.
     */
    @BeforeEach
    public void setup() {
        // Initialise les mocks
        MockitoAnnotations.openMocks(this);

        // Crée un objet CurvePoint pour les tests
        curvePoint = new CurvePoint(1, 1.0, 100.0);
    }

    /**
     * Teste la création d'un CurvePoint en utilisant un ArgumentCaptor pour
     * capturer et
     * vérifier l'objet CurvePoint sauvegardé.
     */
    @Test
    public void testCreateCurve() {
        // Utilisation d'ArgumentCaptor pour capturer l'argument passé au repository
        ArgumentCaptor<CurvePoint> curveCaptor = ArgumentCaptor.forClass(CurvePoint.class);

        // Appelle la méthode à tester
        curveService.createCurve(curvePoint);

        // Vérifie que la méthode save du repository a été appelée une fois
        verify(curvePointRepository, times(1)).save(curveCaptor.capture());

        // Vérifie que les informations du CurvePoint sont correctes
        assertEquals(curveCaptor.getValue().getCurveId(), curvePoint.getCurveId());
        assertEquals(curveCaptor.getValue().getTerm(), curvePoint.getTerm());
        assertEquals(curveCaptor.getValue().getValue(), curvePoint.getValue());
    }

    /**
     * Teste la récupération d'un CurvePoint par son ID lorsqu'il existe.
     */
    @Test
    public void testGetCurveById() {
        // Configure le mock pour retourner un CurvePoint existant
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        // Appelle la méthode à tester
        CurvePoint foundCurve = curveService.getCurveById(1);

        // Vérifie que le CurvePoint trouvé n'est pas nul
        assertNotNull(foundCurve);

        // Vérifie que les informations du CurvePoint trouvé correspondent à celles
        // attendues
        assertEquals(curvePoint.getCurveId(), foundCurve.getCurveId());
        assertEquals(curvePoint.getTerm(), foundCurve.getTerm());
        assertEquals(curvePoint.getValue(), foundCurve.getValue());

        // Vérifie que la méthode findById du repository a été appelée une fois
        verify(curvePointRepository, times(1)).findById(1);
    }

    /**
     * Teste la récupération d'un CurvePoint par son ID lorsqu'il n'existe pas.
     */
    @Test
    public void testGetCurveByIdNotFound() {
        // Configure le mock pour retourner un Optional vide
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        // Appelle la méthode à tester
        CurvePoint foundCurve = curveService.getCurveById(1);

        // Vérifie que le CurvePoint trouvé est nul (puisqu'il n'existe pas)
        assertNull(foundCurve);

        // Vérifie que la méthode findById du repository a été appelée une fois
        verify(curvePointRepository, times(1)).findById(1);
    }

    /**
     * Teste la récupération de tous les CurvePoints.
     */
    @Test
    public void testGetAllCurvePoints() {
        // Crée une liste de CurvePoints pour le test
        List<CurvePoint> curves = Arrays.asList(curvePoint);

        // Configure le mock pour retourner la liste de CurvePoints
        when(curvePointRepository.findAll()).thenReturn(curves);

        // Appelle la méthode à tester
        List<CurvePoint> allCurves = curveService.getAllCurvePoints();

        // Vérifie que la taille de la liste est correcte
        assertEquals(1, allCurves.size());

        // Vérifie que les informations du premier CurvePoint correspondent à celles
        // attendues
        assertEquals(curvePoint.getCurveId(), allCurves.get(0).getCurveId());

        // Vérifie que la méthode findAll du repository a été appelée une fois
        verify(curvePointRepository, times(1)).findAll();
    }

    /**
     * Teste la mise à jour d'un CurvePoint existant.
     */
    @Test
    public void testUpdateCurve() {
        CurvePoint curvePointUpdated = new CurvePoint(1, 2.0, 200.0);
        // Configure le mock pour retourner le CurvePoint existant lors de la recherche
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        // Configure le mock pour retourner le CurvePoint mis à jour lors de la
        // sauvegarde
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        // Appelle la méthode à tester
        curveService.updateCurve(curvePoint.getCurveId(), curvePointUpdated);

        // Vérifie que les informations ont été mises à jour correctement
        assertEquals(curvePointUpdated.getTerm(), curvePoint.getTerm());
        assertEquals(curvePointUpdated.getValue(), curvePoint.getValue());

        // Vérifie que les méthodes du repository ont été appelées le nombre de fois
        // attendu
        verify(curvePointRepository, times(1)).findById(1);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    /**
     * Teste la mise à jour d'un CurvePoint qui n'existe pas.
     */
    @Test
    public void testUpdateCurveNotFound() {
        CurvePoint curvePointUpdated = new CurvePoint(1, 2.0, 200.0);

        // Configure le mock pour retourner un Optional vide lors de la recherche
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        // Appelle la méthode à tester
        curveService.updateCurve(curvePoint.getCurveId(), curvePointUpdated);

        // Vérifie que les méthodes findById et save n'ont pas été appelées
        verify(curvePointRepository, times(1)).findById(1);
        verify(curvePointRepository, never()).save(any(CurvePoint.class));
    }

    /**
     * Teste la suppression d'un CurvePoint.
     */
    @Test
    public void testDeleteCurveById() {
        // Configure le mock pour retourner le CurvePoint existant
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        // Appelle la méthode à tester
        curveService.deleteCurve(curvePoint.getCurveId());

        // Vérifie que la méthode deleteById du repository a été appelée une fois
        verify(curvePointRepository, times(1)).deleteById(curvePoint.getCurveId());
    }
}
