import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        OptimazitaionFunktions functions = new OptimazitaionFunktions();
        HillClimbingAlgo algo = new HillClimbingAlgo(functions::SphereFunktion, -5.12, 5.12,
                10);

        ArrayList<Double> optimalVector = algo.Optimize(1000, 0.3);
        System.out.println("Sphere Funktion: ");
        for (double v : optimalVector) {
            System.out.print(v + ", ");
        }
        System.out.println("\n");
        algo.fitnessFunction = functions::AcleyFunktion;
        algo.dimension = 2;
        ArrayList<Double> optimalVector2 = algo.Optimize(1000, 0.3);
        System.out.println("Acley Funktion: ");
        for (double v : optimalVector2) {
            System.out.print(v + ", \n");
        }
        GenetischerAlgorithmus sphere = new GenetischerAlgorithmus(functions::SphereFunktion, -5.12, 5.12, 10);
        GenetischerAlgorithmus acley = new GenetischerAlgorithmus(functions::AcleyFunktion, -5.12, 5.12, 2);
        System.out.print("Sphere Funktion: \n");
        System.out.print(sphere.optimize("Sphere_Funktion.csv", 100, 0.25, 1000));
        System.out.print("Acley Funktion: \n");
        System.out.print(acley.optimize("Acley_Funktion.csv", 40, 0.25, 1000));
    }
}