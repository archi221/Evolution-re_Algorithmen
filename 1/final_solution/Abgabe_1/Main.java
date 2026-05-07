import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Functions functions = new Functions();
        HillClimbingAlgorithm algorithm = new HillClimbingAlgorithm(functions::SphereFunktion, -5.12, 5.12,
                2);

        ArrayList<Double> optimalVectorSphere = algorithm.optimize(1000, 0.3);
        System.out.println("Sphere-Function: ");
        for (double v : optimalVectorSphere) {
            System.out.print(v + ", ");
        }
        System.out.println("\n");

        algorithm.fitnessFunction = functions::AckleyFunktion;
        algorithm.dimensions = 2;
        ArrayList<Double> optimalVectorAckley = algorithm.optimize(1000, 0.3);
        System.out.println("Ackley-Function: ");
        for (double v : optimalVectorAckley) {
            System.out.print(v + ", \n");
        }

        GenetischerAlgorithmus sphere = new GenetischerAlgorithmus(functions::SphereFunktion, -5.12, 5.12, 10);
        GenetischerAlgorithmus acley = new GenetischerAlgorithmus(functions::AckleyFunktion, -5.12, 5.12, 2);
        System.out.print("Sphere Funktion: \n");
        System.out.print(sphere.optimize("Sphere_Funktion.csv", 100, 0.25, 1000));
        System.out.print("Acley Funktion: \n");
        System.out.print(acley.optimize("Acley_Funktion.csv", 40, 0.25, 1000));
    }
}