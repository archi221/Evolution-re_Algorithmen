import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        OptimizationFunctions optimizationFunctions = new OptimizationFunctions();
        HillClimbingAlgorithm algorithm = new HillClimbingAlgorithm(optimizationFunctions::SphereFunktion
                , -5.12, 5.12,10);

        ArrayList<Double> optimalVectorSphere = algorithm.optimize(1000, 0.3);
        System.out.println("Sphere-Function: ");
        for (double v : optimalVectorSphere) {
            System.out.print(v + ", ");
        }
        System.out.println("\n");

        algorithm.fitnessFunction = optimizationFunctions::AckleyFunktion;
        algorithm.dimensions = 2;
        ArrayList<Double> optimalVectorAckley = algorithm.optimize(1000, 0.3);
        System.out.println("Ackley-Function: ");
        for (double v : optimalVectorAckley) {
            System.out.print(v + ", \n");
        }

        GeneticAlgorithm sphere = new GeneticAlgorithm(optimizationFunctions::SphereFunktion, -5.12
                ,5.12, 10);
        GeneticAlgorithm ackley = new GeneticAlgorithm(optimizationFunctions::AckleyFunktion, -5.12
                ,5.12, 2);
        System.out.print("Sphere Funktion: \n");
        System.out.print(sphere.optimize("Sphere_Funktion.csv", 100, 0.25
                , 1000));
        System.out.print("\n");
        System.out.print("Acley Funktion: \n");
        System.out.print(ackley.optimize("Ackley_Funktion.csv", 20, 0.25
                , 1000));
    }
}