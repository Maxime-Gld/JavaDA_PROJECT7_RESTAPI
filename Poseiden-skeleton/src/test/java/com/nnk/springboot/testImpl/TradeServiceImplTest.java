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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.impl.TradeServiceImpl;

/**
 * Classe de test pour {@link TradeServiceImpl}.
 */
public class TradeServiceImplTest {

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    private TradeRepository tradeRepository;

    private Trade trade;

    /**
     * Méthode exécutée avant chaque test pour initialiser les objets nécessaires.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        trade = new Trade();
        trade.setAccount("TestAccount");
        trade.setType("TestType");
        trade.setBuyQuantity(100.0);
    }

    /**
     * Teste la méthode createTrade en utilisant un ArgumentCaptor pour capturer et
     * vérifier l'objet Trade sauvegardé.
     */
    @Test
    public void testCreateTrade() {
        // Utilisation d'ArgumentCaptor pour capturer l'argument passé à
        // tradeRepository.save
        ArgumentCaptor<Trade> tradeCaptor = ArgumentCaptor.forClass(Trade.class);

        // Appelle la méthode createTrade
        tradeService.createTrade(trade);

        // Vérifie que la méthode save a été appelée
        verify(tradeRepository, times(1)).save(tradeCaptor.capture());

        // Capture l'objet Trade qui a été sauvegardé
        Trade capturedTrade = tradeCaptor.getValue();

        // Vérifie que les valeurs du Trade capturé sont correctes
        assertEquals("TestAccount", capturedTrade.getAccount());
        assertEquals("TestType", capturedTrade.getType());
        assertEquals(100.0, capturedTrade.getBuyQuantity());
    }

    /**
     * Teste la méthode getAllTrades.
     */
    @Test
    public void testGetAllTrades() {
        // Crée une liste de trades pour le test
        List<Trade> trades = Arrays.asList(trade);

        // Configure le mock pour retourner cette liste
        when(tradeRepository.findAll()).thenReturn(trades);

        // Appelle la méthode à tester
        List<Trade> result = tradeService.getAllTrades();

        // Vérifie que le résultat contient le trade attendu
        assertEquals(1, result.size());
        assertEquals("TestAccount", result.get(0).getAccount());

        // Vérifie que la méthode findAll a été appelée une fois
        verify(tradeRepository, times(1)).findAll();
    }

    /**
     * Teste la méthode getTradeById lorsqu'un trade existe.
     */
    @Test
    public void testGetTradeById() {
        // Configure le mock pour retourner un trade existant
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        // Appelle la méthode à tester
        Trade foundTrade = tradeService.getTradeById(1);

        // Vérifie que le trade retourné n'est pas nul et que ses valeurs sont correctes
        assertNotNull(foundTrade);
        assertEquals("TestAccount", foundTrade.getAccount());

        // Vérifie que la méthode findById a été appelée une fois
        verify(tradeRepository, times(1)).findById(1);
    }

    /**
     * Teste la méthode getTradeById lorsqu'aucun trade n'est trouvé.
     */
    @Test
    public void testGetTradeByIdNotFound() {
        // Configure le mock pour retourner un Optional vide
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        // Appelle la méthode à tester
        Trade foundTrade = tradeService.getTradeById(1);

        // Vérifie que le trade retourné est nul
        assertNull(foundTrade);

        // Vérifie que la méthode findById a été appelée une fois
        verify(tradeRepository, times(1)).findById(1);
    }

    /**
     * Teste la méthode updateTrade lorsque le trade existe.
     */
    @Test
    public void testUpdateTrade() {
        // Crée un trade mis à jour
        Trade updatedTrade = new Trade();
        updatedTrade.setAccount("UpdatedAccount");
        updatedTrade.setType("UpdatedType");
        updatedTrade.setBuyQuantity(200.0);

        // Configure le mock pour retourner le trade existant
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        // Appelle la méthode à tester
        tradeService.updateTrade(1, updatedTrade);

        // Vérifie que les valeurs du trade ont été mises à jour correctement
        assertEquals("UpdatedAccount", trade.getAccount());
        assertEquals("UpdatedType", trade.getType());
        assertEquals(200.0, trade.getBuyQuantity());

        // Vérifie que la méthode save a été appelée une fois
        verify(tradeRepository, times(1)).save(trade);
    }

    /**
     * Teste la méthode updateTrade lorsque le trade n'existe pas.
     */
    @Test
    public void testUpdateTradeNotFound() {
        // Configure le mock pour retourner un Optional vide
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        // Appelle la méthode à tester
        tradeService.updateTrade(1, trade);

        // Vérifie que la méthode save n'a pas été appelée
        verify(tradeRepository, never()).save(any(Trade.class));
    }

    /**
     * Teste la méthode deleteTrade.
     */
    @Test
    public void testDeleteTrade() {
        // Configure le mock pour ne rien faire lors de la suppression
        doNothing().when(tradeRepository).deleteById(1);

        // Appelle la méthode à tester
        tradeService.deleteTrade(1);

        // Vérifie que la méthode deleteById a été appelée une fois
        verify(tradeRepository, times(1)).deleteById(1);
    }
}
