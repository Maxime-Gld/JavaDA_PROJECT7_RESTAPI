package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;

/**
 * Implémentation du service pour gérer les entités RuleName.
 */
@Service
public class RuleNameServiceImpl implements RuleNameService {

    private RuleNameRepository ruleNameRepository;

    /**
     * Constructeur avec injection du repository RuleName.
     * 
     * @param ruleNameRepository le repository de RuleName.
     */
    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Récupère tous les objets RuleName.
     * 
     * @return la liste des RuleName.
     */
    @Override
    public List<RuleName> getAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    /**
     * Récupère un RuleName par son ID.
     * 
     * @param id l'ID du RuleName.
     * @return le RuleName correspondant, ou null s'il n'existe pas.
     */
    @Override
    public RuleName getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id).orElse(null);
    }

    /**
     * Crée un nouveau RuleName.
     * 
     * @param ruleName l'objet RuleName à créer.
     */
    @Override
    public void createRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    /**
     * Supprime un RuleName par son ID.
     * 
     * @param id l'ID du RuleName à supprimer.
     */
    @Override
    public void deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
    }

    /**
     * Met à jour un RuleName existant.
     * 
     * @param id       l'ID du RuleName à mettre à jour.
     * @param ruleName les nouvelles informations du RuleName.
     */
    @Override
    public void updateRuleName(Integer id, RuleName ruleName) {
        Optional<RuleName> ruleNameToUpdate = ruleNameRepository.findById(id);
        if (ruleNameToUpdate.isPresent()) {
            RuleName ruleNameFound = ruleNameToUpdate.get();
            ruleNameFound.setName(ruleName.getName());
            ruleNameFound.setDescription(ruleName.getDescription());
            ruleNameFound.setJson(ruleName.getJson());
            ruleNameFound.setTemplate(ruleName.getTemplate());
            ruleNameFound.setSqlStr(ruleName.getSqlStr());
            ruleNameFound.setSqlPart(ruleName.getSqlPart());
            ruleNameRepository.save(ruleNameFound);
        }
    }
}
