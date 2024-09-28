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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.impl.RuleNameServiceImpl;

/**
 * Classe de test pour {@link RuleNameServiceImpl}.
 */
public class RuleNameServiceImplTest {

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    private RuleName ruleName;

    /**
     * Méthode exécutée avant chaque test pour initialiser les objets nécessaires.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ruleName = new RuleName(
                "Name Test",
                "Description Test",
                "Json Test",
                "Template Test",
                "SQL Str Test",
                "SQL Part Test");
    }

    /**
     * Teste la création d'un RuleName en utilisant un ArgumentCaptor pour capturer
     * et
     * vérifier l'objet RuleName sauvegardé.
     */
    @Test
    public void testCreateRuleName() {
        // Utilisation d'ArgumentCaptor pour capturer l'argument passé au repository
        ArgumentCaptor<RuleName> ruleNameCaptor = ArgumentCaptor.forClass(RuleName.class);

        // Appelle la méthode à tester
        ruleNameService.createRuleName(ruleName);

        // Vérifie que la méthode save a été appelée avec le bon objet
        verify(ruleNameRepository, times(1)).save(ruleNameCaptor.capture());
        RuleName capturedRuleName = ruleNameCaptor.getValue();

        // Vérifie que les attributs de l'objet sont bien ceux attendus
        assertEquals("Name Test", capturedRuleName.getName());
        assertEquals("Description Test", capturedRuleName.getDescription());
        assertEquals("Json Test", capturedRuleName.getJson());
        assertEquals("Template Test", capturedRuleName.getTemplate());
        assertEquals("SQL Str Test", capturedRuleName.getSqlStr());
        assertEquals("SQL Part Test", capturedRuleName.getSqlPart());
    }

    /**
     * Teste la récupération de tous les RuleNames.
     */
    @Test
    public void testGetAllRuleNames() {
        List<RuleName> ruleNames = Arrays.asList(ruleName);

        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        // Appelle la méthode à tester
        List<RuleName> allRuleNames = ruleNameService.getAllRuleNames();

        // Vérifie la taille de la liste
        assertEquals(1, allRuleNames.size());
        // Vérifie que la méthode findAll du repository a été appelée une fois
        verify(ruleNameRepository, times(1)).findAll();
    }

    /**
     * Teste la récupération d'un RuleName par son ID quand il existe.
     */
    @Test
    public void testGetRuleNameById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        RuleName foundRuleName = ruleNameService.getRuleNameById(1);

        assertNotNull(foundRuleName);
        assertEquals(ruleName.getName(), foundRuleName.getName());
        verify(ruleNameRepository, times(1)).findById(1);
    }

    /**
     * Teste la récupération d'un RuleName par son ID quand il n'existe pas.
     */
    @Test
    public void testGetRuleNameByIdNotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        RuleName foundRuleName = ruleNameService.getRuleNameById(1);

        assertNull(foundRuleName);
        verify(ruleNameRepository, times(1)).findById(1);
    }

    /**
     * Teste la mise à jour d'un RuleName existant.
     */
    @Test
    public void testUpdateRuleName() {
        RuleName updatedRuleName = new RuleName("Updated Name", "Updated Description", "Updated Json",
                "Updated Template", "Updated SQL Str", "Updated SQL Part");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        ruleNameService.updateRuleName(1, updatedRuleName);

        assertEquals("Updated Name", ruleName.getName());
        assertEquals("Updated Description", ruleName.getDescription());
        assertEquals("Updated Json", ruleName.getJson());
        assertEquals("Updated Template", ruleName.getTemplate());
        assertEquals("Updated SQL Str", ruleName.getSqlStr());
        assertEquals("Updated SQL Part", ruleName.getSqlPart());

        verify(ruleNameRepository, times(1)).findById(1);
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    /**
     * Teste la suppression d'un RuleName.
     */
    @Test
    public void testDeleteRuleName() {
        doNothing().when(ruleNameRepository).deleteById(1);

        ruleNameService.deleteRuleName(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}
