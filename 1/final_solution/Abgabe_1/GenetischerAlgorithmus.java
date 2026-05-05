import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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

    public Genotype<DoubleGene> optimize(String path, int populationSize, double mutationProbability, int maxGenerations) {
        // 1.) Genotyp definieren
        Factory<Genotype<DoubleGene>> gtf =
                Genotype.of(DoubleChromosome.of(min, max, dimension));

        // 2.) Engine erstellen
        Engine<DoubleGene, Double> engine = Engine
                .builder(this::eval, gtf)
                .populationSize(populationSize)
                .alterers(
                        new Mutator<>(mutationProbability),
                        new MeanAlterer<>(0.6)
                )
                .minimizing()
                .build();

        Genotype<DoubleGene> result = null;
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(path))) {

            writer.write("Total Generations, Worst Fitness, Best Fitness, Median Fitness, Mean Fitness\n");

        // 3.) Evolution starten und Ergebnis sammeln
        result = engine.stream()

                .limit(bySteadyFitness(20))
                .limit(maxGenerations)
                .peek(x -> {
                    double[] fitnessValues = x.population().stream()
                            .mapToDouble(Phenotype::fitness)
                            .toArray();
                    double mean = Arrays.stream(fitnessValues).average().orElse(0.0);
                    double[] fitnessValuesSortet = Arrays.stream(fitnessValues).sorted().toArray();
                    double median;
                    if (fitnessValuesSortet.length % 2 == 0) {
                        median = fitnessValuesSortet[fitnessValuesSortet.length / 2];
                    }else{
                        median = fitnessValuesSortet[fitnessValuesSortet.length / 2 + 1];
                        median = median + fitnessValuesSortet[fitnessValuesSortet.length / 2 - 1];
                        median = median / 2;
                        }
                    try {
                        writer.write(x.totalGenerations() + ",");
                        writer.write(x.worstFitness() + ",");
                        writer.write(x.bestFitness() + ",");
                        writer.write(median + ",");
                        writer.write(mean + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                })
                .collect(EvolutionResult. toBestGenotype());

        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Schreiben der CSV", e);
        }
        return result;
    }
}