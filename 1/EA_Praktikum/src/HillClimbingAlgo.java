import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.ToDoubleFunction;

public class HillClimbingAlgo {

    Random random = new Random();
    //    ArrayList<double[]> population = new ArrayList<>();
    public double min;
    public double max;
    public int dimension;
    public ToDoubleFunction<double[]> fitnessFunction;


    public HillClimbingAlgo(ToDoubleFunction<double[]> fitnessFunction,
                            double min, double max, int populationSize, int dimension,
                            int seed) {
        random.setSeed(seed);
        this.fitnessFunction = fitnessFunction;
        this.dimension = dimension;
        this.min = min;
        this.max = max;
        assert max > min;
//        for (int i = 0; i < populationSize; i++) {
//            double[] vector = new double[dimension];
//            for (int j = 0; j < dimension; j++) {
//                vector[j] = random.nextDouble(min, max);// min inclusive max exclusive
//            }
//            population.add(vector);
//        }
    }

    public double[] Optimize(int maxIterations, double steps_range) {
        assert steps_range < (max - min);
        double step;
        double[] bestVector = new double[dimension];
        for (int j = 0; j < dimension; j++) {
            bestVector[j] = random.nextDouble(min, max);// min inclusive max exclusive
        }

        for (int i = 0; i < maxIterations; i++) {
            ArrayList<double[]> population = new ArrayList<>();

            for (int k = 0; k < dimension; k++) {
                double[] vector = bestVector.clone();
                step = random.nextDouble((-steps_range), steps_range);

                vector[k] += step;
                population.add(vector);
            }
            double[] updatedBestVector = population.stream()
                    .min(Comparator.comparingDouble(fitnessFunction))
                    .orElseThrow(() -> new IllegalStateException("Population ist leer"));

            if (fitnessFunction.applyAsDouble(updatedBestVector) < fitnessFunction.applyAsDouble(bestVector)) {
                bestVector = updatedBestVector;
            } else {
                break;
            }
        }
        return bestVector;
    }
}
