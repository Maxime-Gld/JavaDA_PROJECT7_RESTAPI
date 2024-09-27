package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;

public interface CurveService {

    void createCurve(CurvePoint curvePoint);

    void updateCurve(Integer id, CurvePoint curvePoint);

    void deleteCurve(Integer curveId);

    List<CurvePoint> getAllCurvePoints();

    CurvePoint getCurveById(Integer id);
}
