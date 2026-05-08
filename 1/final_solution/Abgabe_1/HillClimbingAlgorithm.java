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
        this.fitnessFunction = fitnessFunction;
        this.minValue = minValue;
        this.maxValue = maxValue;
        assert maxValue > minValue;
        this.dimensions = dimensions;
    }

    public HillClimbingAlgorithm(ToDoubleFunction<ArrayList<Double>> fitnessFunction,
                                 double minValue, double maxValue, int dimensions, int seed) {
        random.setSeed(seed);
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
//    public static double[] genereateNextList( int funktion, double[] xList, double step, double min, double max){
//
//        double[][] neighbors = new double[4][2];
//
//        neighbors[0] = new double[]{Math.min(max, Funktions.round2(xList[0]+step)), xList[1]};
//        neighbors[1] = new double[]{Math.max(min,  Funktions.round2(xList[0]-step)), xList[1]};
//        neighbors[2] = new double[]{xList[0], Math.min(max, Funktions.round2(xList[1]+step))};
//        neighbors[3] = new double[]{xList[0], Math.max(min,  Funktions.round2(xList[1]-step))};
//
//        double bestSum= Funktions.calculateFunktion(funktion,neighbors[0]);
//        int bestI=0;
//
//        for(int i =1; i < neighbors.length; i++){
//            double sum = Funktions.calculateFunktion(funktion,neighbors[i]);
//
//            if(sum < bestSum){
//                bestSum = sum;
//                bestI =i;
//            }
//        }
//
//        return neighbors[bestI];
//    }
}