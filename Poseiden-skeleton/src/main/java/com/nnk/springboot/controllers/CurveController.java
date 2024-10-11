package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurveService;

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
import java.util.List;

@Controller
public class CurveController {
    // injection de la couche service
    private final CurveService curvePointService;

    @Autowired
    public CurveController(CurveService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model, HttpServletRequest request) {
        // récupère la liste des curvePoints et l'envoie au modèle
        List<CurvePoint> curvePoints = curvePointService.getAllCurvePoints();
        model.addAttribute("curvePoints", curvePoints);
        model.addAttribute("httpServletRequest", request);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // vérification des erreurs de validation
        // si valid, enregistre le Bid et redirige vers la liste des CurvePoints
        if (!result.hasErrors()) {
            curvePointService.createCurve(curvePoint);
            return "redirect:/curvePoint/list";
        }
        // rediriger vers la page d'ajout du CurvePoint avec les erreurs
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // récupère le CurvePoint à mettre à jour et l'envoie au modèle
        CurvePoint curvePoint = curvePointService.getCurveById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
            BindingResult result, Model model) {
        // vérification des erreurs de validation
        // si valid, met à jour le CurvePoint et redirige vers la liste des CurvePoints
        if (!result.hasErrors()) {
            curvePointService.updateCurve(id, curvePoint);
            return "redirect:/curvePoint/list";
        }
        // rediriger vers la page de modification du CurvePoint avec les erreurs
        return "curvePoint/update";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // supprime le CurvePoint et redirige vers la liste des CurvePoints
        curvePointService.deleteCurve(id);
        return "redirect:/curvePoint/list";
    }
}
