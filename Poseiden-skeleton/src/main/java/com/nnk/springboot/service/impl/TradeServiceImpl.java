package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;

/**
 * Implémentation du service {@link TradeService} pour gérer les opérations sur
 * les objets Trade.
 */
@Service
public class TradeServiceImpl implements TradeService {

    private TradeRepository tradeRepository;

    /**
     * Constructeur pour injecter le repository Trade.
     * 
     * @param tradeRepository le repository des trades
     */
    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Récupère la liste de tous les trades.
     * 
     * @return une liste de tous les trades
     */
    @Override
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    /**
     * Supprime un trade par son identifiant.
     * 
     * @param id l'identifiant du trade à supprimer
     */
    @Override
    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }

    /**
     * Crée un nouveau trade.
     * 
     * @param trade l'objet Trade à sauvegarder
     */
    @Override
    public void createTrade(Trade trade) {
        tradeRepository.save(trade);
    }

    /**
     * Met à jour un trade existant en fonction de son identifiant.
     * 
     * @param id    l'identifiant du trade à mettre à jour
     * @param trade les nouvelles informations du trade
     */
    @Override
    public void updateTrade(Integer id, Trade trade) {
        Optional<Trade> tradeToUpdate = tradeRepository.findById(id);

        if (tradeToUpdate.isPresent()) {
            Trade tradeFound = tradeToUpdate.get();
            tradeFound.setAccount(trade.getAccount());
            tradeFound.setType(trade.getType());
            tradeFound.setBuyQuantity(trade.getBuyQuantity());
            tradeRepository.save(tradeFound);
        }
    }

    /**
     * Récupère un trade par son identifiant.
     * 
     * @param id l'identifiant du trade à récupérer
     * @return l'objet Trade correspondant ou null s'il n'existe pas
     */
    @Override
    public Trade getTradeById(Integer id) {
        return tradeRepository.findById(id).orElse(null);
    }
}
