package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;

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
public class TradeController {
    // injection de la couche service
    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model, HttpServletRequest request) {
        // récupère la liste des trades et l'envoie au modèle
        model.addAttribute("trades", tradeService.getAllTrades());
        model.addAttribute("httpServletRequest", request);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // vérification des erreurs de validation
        // si valid, enregistre le Trade et redirige vers la liste des Trades
        if (!result.hasErrors()) {
            tradeService.createTrade(trade);
            return "redirect:/trade/list";
        }
        // rediriger vers la page d'ajout du Trade avec les erreurs
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // recupère le Trade à mettre à jour et l'envoie au modèle
        Trade trade = tradeService.getTradeById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
            BindingResult result, Model model) {
        // vérification des erreurs de validation
        // si valid, met à jour le Trade et redirige vers la liste des Trades
        if (!result.hasErrors()) {
            tradeService.updateTrade(id, trade);
            return "redirect:/trade/list";
        }

        // rediriger vers la page de modification du Trade avec les erreurs
        return "trade/update";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // supprime le Trade et redirige vers la liste des Trades
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
