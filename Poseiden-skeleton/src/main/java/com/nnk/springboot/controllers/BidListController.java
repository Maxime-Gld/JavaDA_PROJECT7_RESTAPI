package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.impl.BidListServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BidListController {
    @Autowired
    private BidListServiceImpl bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model, HttpServletRequest request) {
        // récupère la liste des BidList et l'envoie au modèle
        List<BidList> bidLists = bidListService.getAllBidList();
        model.addAttribute("bidLists", bidLists);
        model.addAttribute("httpServletRequest", request);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // verifie les champs obligatoires
        // si valid, enregistre le Bid et redirige vers la liste des Bid
        if (!result.hasErrors()) {
            bidListService.createBidList(bid);
            return "redirect:/bidList/list";
        }
        // rediriger vers la page d'ajout du Bid
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // recupère le BidList à mettre à jour et l'envoie au modèle
        BidList bid = bidListService.getBidListById(id);
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
            BindingResult result, Model model) {
        // verifie les champs obligatoires
        // si valid, mettre a jour le BidList et redirige vers la liste des Bid
        if (!result.hasErrors()) {
            bidListService.updateBidList(id, bidList);
        }

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // supprime le BidList et redirige vers la liste des Bid
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
