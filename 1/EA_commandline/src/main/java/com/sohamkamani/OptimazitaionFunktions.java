package com.sohamkamani;

import java.util.ArrayList;

public class OptimazitaionFunktions {

    public double SphereFunktion(ArrayList<Double> vector){
        double summe = 0;
        final double valueRange = 5.12;
        for (double v : vector) {
            assert Math.abs(v) <= valueRange;
            summe += Math.pow(v, 2);
        }
        return summe;
    }
    public double AcleyFunktion(ArrayList<Double> vector){
        double firstSum = 0;
        double secondSum = 0;
        for (double v : vector) {
           firstSum += (Math.pow(v, 2) / vector.size());
           secondSum += (Math.cos(2 * Math.PI * v) / vector.size());
        }
        double firstExp = Math.exp(- 0.2 * firstSum);
        double secondExp
                = Math.exp(secondSum);
        return 20 + Math.E - (20 * firstExp) - secondExp;
    }
}