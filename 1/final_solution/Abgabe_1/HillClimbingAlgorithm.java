import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.function.ToDoubleFunction;

public class HillClimbingAlgorithm {

    Random random = new Random();
    public double minValue;
    public double maxValue;
    public int dimensions;
    public ToDoubleFunction<ArrayList<Double>> fitnessFunction;

    public HillClimbingAlgorithm(ToDoubleFunction<ArrayList<Double>> fitnessFunction,
                                 double minValue, double maxValue, int dimensions) {
        //random.setSeed(420);
        this.fitnessFunction = fitnessFunction;
        this.minValue = minValue;
        this.maxValue = maxValue;
        assert maxValue > minValue;
        this.dimensions = dimensions;
    }

    public ArrayList<Double> optimize(int maxIterations, double steps_range) {
        assert steps_range < (maxValue - minValue);
        double step;
        ArrayList<Double> currentBestVector = new ArrayList<>();
        for (int j = 0; j < dimensions; j++) {
            currentBestVector.add(random.nextDouble(minValue, Math.nextUp(maxValue))); // min & max inclusive
        }

        for (int i = 0; i < maxIterations; i++) {
            ArrayList<ArrayList<Double>> population = new ArrayList<>();

            for (int k = 0; k < dimensions; k++) {
                ArrayList<Double> vector = new ArrayList<>(currentBestVector);
                step = random.nextDouble((-steps_range), steps_range);

                vector.set(k, vector.get(k) + step);
                if (vector.get(k) < minValue) vector.set(k, minValue);
                if (vector.get(k) > maxValue) vector.set(k, maxValue);
                population.add(vector);
            }
            ArrayList<Double> potentialBestVector = population.stream()
                    .min(Comparator.comparingDouble(fitnessFunction))
                    .orElseThrow(() -> new IllegalStateException("population is empty"));

            if (fitnessFunction.applyAsDouble(potentialBestVector) < fitnessFunction.applyAsDouble(currentBestVector)) {
                currentBestVector = new ArrayList<>(potentialBestVector);
            }
           /*
            } else {
               break;
            }
            */
        }
        return currentBestVector;
    }
}
