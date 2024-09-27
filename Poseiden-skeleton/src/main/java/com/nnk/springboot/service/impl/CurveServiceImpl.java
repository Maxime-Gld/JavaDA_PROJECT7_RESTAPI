package com.nnk.springboot.service.impl;

import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurveService;

import java.util.List;
import java.util.Optional;

/**
 * Service implémentant les opérations CRUD pour la gestion des
 * {@link CurvePoint}.
 */
@Service
public class CurveServiceImpl implements CurveService {

    private CurvePointRepository curvePointRepository;

    /**
     * Constructeur avec injection de dépendance pour le repository des CurvePoints.
     * 
     * @param curvePointRepository Le repository utilisé pour les opérations sur les
     *                             CurvePoints.
     */
    public CurveServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Crée et enregistre un nouveau CurvePoint.
     * 
     * @param curvePoint Le nouveau CurvePoint.
     */
    @Override
    public void createCurve(CurvePoint curvePoint) {
        curvePointRepository.save(new CurvePoint(curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getValue()));
    }

    /**
     * Récupère tous les CurvePoints.
     * 
     * @return Une liste de tous les CurvePoints.
     */
    @Override
    public List<CurvePoint> getAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    /**
     * Récupère un CurvePoint par son ID.
     * 
     * @param id L'ID du CurvePoint.
     * @return Le CurvePoint correspondant.
     */
    @Override
    public CurvePoint getCurveById(Integer id) {
        return curvePointRepository.findById(id).orElse(null);
    }

    /**
     * Met à jour un CurvePoint existant.
     * 
     * @param curveId           L'ID du CurvePoint.
     * @param curvePointUpdated Le nouveau CurvePoint.
     */
    @Override
    public void updateCurve(int curveId, CurvePoint curvePointUpdated) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(curveId);
        if (curvePoint.isPresent()) {
            CurvePoint curvePointFound = curvePoint.get();
            curvePointFound.setTerm(curvePointUpdated.getTerm());
            curvePointFound.setValue(curvePointUpdated.getValue());
            curvePointRepository.save(curvePointFound);
        }
    }

    /**
     * Supprime un CurvePoint par son ID.
     * 
     * @param curveId L'ID du CurvePoint à supprimer.
     */
    @Override
    public void deleteCurve(int curveId) {
        curvePointRepository.deleteById(curveId);
    }
}
