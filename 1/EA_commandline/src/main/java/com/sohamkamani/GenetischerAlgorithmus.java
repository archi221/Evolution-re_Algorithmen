package com.sohamkamani;

import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStart;
import io.jenetics.util.Factory;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.ToDoubleFunction;

import static io.jenetics.engine.Limits.bySteadyFitness;

public class GenetischerAlgorithmus {

    private final Random random = new Random();

    public double min;
    public double max;
    public int dimension;
    private ToDoubleFunction<ArrayList<Double>> fitnessFunction;

    public GenetischerAlgorithmus(ToDoubleFunction<ArrayList<Double>> fitnessFunction,
                                  double min, double max, int dimension, int seed) {
        random.setSeed(seed);
        this.fitnessFunction = fitnessFunction;
        this.dimension = dimension;
        this.min = min;
        this.max = max;
        assert max > min;
    }

    public GenetischerAlgorithmus(ToDoubleFunction<ArrayList<Double>> fitnessFunction,
                                  double min, double max, int dimension) {
        random.setSeed(67);
        this.fitnessFunction = fitnessFunction;
        this.dimension = dimension;
        this.min = min;
        this.max = max;
        assert max > min;
    }

    // Definition der Fitnessfunktion
    private double eval(Genotype<DoubleGene> gt) {
        ArrayList<Double> values = new ArrayList<>();
        gt.chromosome().forEach(p -> values.add(p.doubleValue()));
        return fitnessFunction.applyAsDouble(values);
    }

    public void optimize() {
        // 1.) Genotyp definieren
        Factory<Genotype<DoubleGene>> gtf =
                Genotype.of(DoubleChromosome.of(min, max, dimension));

        // 2.) Engine erstellen
        Engine<DoubleGene, Double> engine = Engine
                .builder(this::eval, gtf)
                .optimize(Optimize.MINIMUM)
                .populationSize(40)
                .alterers(
                        new Mutator<>(0.03),
                        new MeanAlterer<>(0.6)
                )
                .build();

        // 3.) Evolution starten und Ergebnis sammeln
        Genotype<DoubleGene> result = engine.stream()
                .limit(bySteadyFitness(5))
                .limit(20)
                .collect(EvolutionResult. toBestGenotype());

        System.out.println("GA:\n" + result);
    }
}