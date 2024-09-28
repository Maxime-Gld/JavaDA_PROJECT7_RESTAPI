package com.nnk.springboot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;

/**
 * Implémentation du service pour gérer les opérations CRUD
 * sur l'entité {@link BidList}.
 */
@Service
public class BidListServiceImpl implements BidListService {

    private BidListRepository bidListRepository;

    /**
     * Constructeur avec injection du repository pour la gestion des BidList.
     * 
     * @param bidListRepository Le repository utilisé pour les opérations sur les
     *                          BidList.
     */
    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Crée et enregistre une nouvelle BidList.
     * 
     * @param bid La nouvelle BidList.
     */
    @Override
    public void createBidList(BidList bid) {
        bidListRepository.save(bid);
    }

    /**
     * Supprime une BidList par son identifiant.
     * 
     * @param id L'identifiant de la BidList à supprimer.
     */
    @Override
    public void deleteBidList(Integer id) {
        bidListRepository.deleteById(id);
    }

    /**
     * Met à jour une BidList existante par son identifiant.
     * 
     * @param id         L'identifiant de la BidList à mettre à jour.
     * @param bidUpdated La nouvelle BidList.
     */
    @Override
    public void updateBidList(Integer id, BidList bidUpdated) {
        BidList bid = bidListRepository.findById(id).orElse(null);
        if (bid != null) {
            bid.setAccount(bidUpdated.getAccount());
            bid.setType(bidUpdated.getType());
            bid.setBidQuantity(bidUpdated.getBidQuantity());
            bidListRepository.save(bid);
        }
    }

    /**
     * Récupère une BidList par son identifiant.
     * 
     * @param id L'identifiant de la BidList à récupérer.
     * @return La BidList correspondante, ou null si elle n'existe pas.
     */
    @Override
    public BidList getBidListById(Integer id) {
        return bidListRepository.findById(id).orElse(null);
    }

    /**
     * Récupère toutes les BidList enregistrées.
     * 
     * @return La liste de toutes les BidList.
     */
    @Override
    public List<BidList> getAllBidList() {
        return bidListRepository.findAll();
    }
}
