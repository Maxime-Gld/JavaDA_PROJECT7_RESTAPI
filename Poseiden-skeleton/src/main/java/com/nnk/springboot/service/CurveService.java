package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;

public interface CurveService {

    void createCurve(CurvePoint curvePoint);

    void updateCurve(int id, CurvePoint curvePoint);

    void deleteCurve(int curveId);

    List<CurvePoint> getAllCurvePoints();

    CurvePoint getCurveById(Integer id);
}
