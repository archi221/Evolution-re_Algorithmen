package org.example;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        OptimazitaionFunktions functions = new OptimazitaionFunktions();
        HillClimbingAlgo algo = new HillClimbingAlgo(functions::SphereFunktion, - 5.12, 5.12,
                10);

        double[] optimalVector = algo.Optimize(1000, 0.1);
        System.out.println("Sphere Funktion: ");
        for (double v : optimalVector) {
            System.out.print(v + ", ");
        }
        algo.fitnessFunction = functions::AcleyFunktion;
        algo.dimension = 2;
        double[] optimalVector2 = algo.Optimize(1000, 0.1);
        System.out.println("Acley Funktion: ");
        for (double v : optimalVector2) {
            System.out.print(v + ", ");
        }
    }
}