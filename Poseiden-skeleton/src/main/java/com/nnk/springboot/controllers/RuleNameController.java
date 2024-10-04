package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.impl.RuleNameServiceImpl;

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
public class RuleNameController {
    @Autowired
    private RuleNameServiceImpl ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model, HttpServletRequest request) {
        // récupère la liste des RuleNames et l'envoie directement au modèle
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        model.addAttribute("httpServletRequest", request);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // vérification des erreurs de validation
        // si valid, enregistre le RuleName et redirige vers la liste des RuleNames
        if (!result.hasErrors()) {
            ruleNameService.createRuleName(ruleName);
            return "redirect:/ruleName/list";
        }
        // rediriger vers la page d'ajout du RuleName
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // recupère le RuleName à mettre à jour et l'envoie au modèle
        RuleName ruleName = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
            BindingResult result, Model model) {
        // verifie les champs obligatoires
        // si valid, met à jour le RuleName et redirige vers la liste des RuleNames
        if (!result.hasErrors()) {
            ruleNameService.updateRuleName(id, ruleName);
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // supprime le RuleName et redirige vers la liste des RuleNames
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
