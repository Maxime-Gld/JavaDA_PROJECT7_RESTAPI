package com.nnk.springboot.testImpl;

import static org.junit.jupiter.api.Assertions.*;
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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.impl.BidListServiceImpl;

/**
 * Classe de test pour {@link BidListServiceImpl}.
 */
public class BidListServiceImplTest {

    @InjectMocks
    private BidListServiceImpl bidListService;

    @Mock
    private BidListRepository bidListRepository;

    private BidList bid;

    /**
     * Méthode exécutée avant chaque test pour initialiser les objets nécessaires.
     */
    @BeforeEach
    public void setup() {
        // Initialise les mocks
        MockitoAnnotations.openMocks(this);

        // Crée un objet BidList pour les tests
        bid = new BidList("Account Test", "Type Test", 10.0);
    }

    /**
     * Teste la création d'une BidList.
     */
    @Test
    public void testCreateBidList() {
        // Configure le comportement du mock pour la méthode save
        when(bidListRepository.save(any(BidList.class))).thenReturn(bid);

        // Appelle la méthode à tester
        bidListService.createBidList(bid);

        // Vérifie que la méthode save du repository a été appelée une fois
        ArgumentCaptor<BidList> bidCaptor = ArgumentCaptor.forClass(BidList.class);
        verify(bidListRepository, times(1)).save(bidCaptor.capture());
        assertEquals(bidCaptor.getValue().getAccount(), "Account Test");
        assertEquals(bidCaptor.getValue().getType(), "Type Test");
        assertEquals(bidCaptor.getValue().getBidQuantity(), 10.0);
    }

    /**
     * Teste la récupération d'une BidList par son ID lorsqu'elle existe.
     */
    @Test
    public void testGetBidListById() {
        // Configure le mock pour retourner une BidList existante
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        // Appelle la méthode à tester
        BidList foundBid = bidListService.getBidListById(1);

        // Vérifie que la BidList trouvée n'est pas nulle
        assertNotNull(foundBid);

        // Vérifie que les informations de la BidList trouvée correspondent à celles
        // attendues
        assertEquals(bid.getAccount(), foundBid.getAccount());
        assertEquals(bid.getType(), foundBid.getType());

        // Vérifie que la méthode findById du repository a été appelée une fois
        verify(bidListRepository, times(1)).findById(1);
    }

    /**
     * Teste la récupération d'une BidList par son ID lorsqu'elle n'existe pas.
     */
    @Test
    public void testGetBidListByIdNotFound() {
        // Configure le mock pour retourner un Optional vide
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        // Appelle la méthode à tester
        BidList foundBid = bidListService.getBidListById(1);

        // Vérifie que la BidList trouvée est nulle (puisqu'elle n'existe pas)
        assertNull(foundBid);

        // Vérifie que la méthode findById du repository a été appelée une fois
        verify(bidListRepository, times(1)).findById(1);
    }

    /**
     * Teste la récupération de toutes les BidList.
     */
    @Test
    public void testGetAllBidList() {
        // Crée une liste de BidList pour le test
        List<BidList> bids = Arrays.asList(bid);

        // Configure le mock pour retourner la liste de BidList
        when(bidListRepository.findAll()).thenReturn(bids);

        // Appelle la méthode à tester
        List<BidList> allBids = bidListService.getAllBidList();

        // Vérifie que la taille de la liste est correcte
        assertEquals(1, allBids.size());

        // Vérifie que les informations de la première BidList correspondent à celles
        // attendues
        assertEquals(bid.getAccount(), allBids.get(0).getAccount());

        // Vérifie que la méthode findAll du repository a été appelée une fois
        verify(bidListRepository, times(1)).findAll();
    }

    /**
     * Teste la mise à jour d'une BidList existante.
     */
    @Test
    public void testUpdateBidList() {
        // Crée une nouvelle BidList avec les nouvelles informations
        BidList updatedBid = new BidList("Updated Account", "Updated Type", 20.0);

        // Configure le mock pour retourner la BidList existante lors de la recherche
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        // Configure le mock pour retourner la BidList mise à jour lors de la sauvegarde
        when(bidListRepository.save(bid)).thenReturn(bid);

        // Appelle la méthode à tester
        bidListService.updateBidList(1, updatedBid);

        // Vérifie que les informations ont été mises à jour correctement
        assertEquals(updatedBid.getAccount(), bid.getAccount());
        assertEquals(updatedBid.getType(), bid.getType());
        assertEquals(updatedBid.getBidQuantity(), bid.getBidQuantity());

        // Vérifie que les méthodes du repository ont été appelées le nombre de fois
        // attendu
        verify(bidListRepository, times(1)).findById(1);
        verify(bidListRepository, times(1)).save(bid);
    }

    /**
     * Teste la mise à jour d'une BidList qui n'existe pas.
     * 
     * @Test
     *       public void testUpdateBidListNotFound() {
     *       // Configure le mock pour retourner un Optional vide lors de la
     *       recherche
     *       when(bidListRepository.findById(1)).thenReturn(Optional.empty());
     * 
     *       // Appelle la méthode à tester
     *       bidListService.updateBidList(1, "Updated Account", "Updated Type",
     *       20.0);
     * 
     *       // Vérifie que les méthodes findById et save n'ont pas été appelées
     *       verify(bidListRepository, times(1)).findById(1);
     *       verify(bidListRepository, never()).save(any(BidList.class));
     *       }
     */

    /**
     * Teste la suppression d'une BidList.
     */
    @Test
    public void testDeleteBidList() {
        // Configure le mock pour retourner la BidList existante
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        // Configure le mock pour ne rien faire lors de la suppression
        doNothing().when(bidListRepository).delete(bid);

        // Appelle la méthode à tester
        bidListService.deleteBidList(1);

        // Vérifie que la méthode delete du repository a été appelée une fois
        verify(bidListRepository, times(1)).delete(bid);
    }
}
