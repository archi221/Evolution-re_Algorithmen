
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        OptimazitaionFunktions functions = new OptimazitaionFunktions();
        int dimensions = 10;
        HillClimbingAlgo algo = new HillClimbingAlgo(functions::SphereFunktion, - 5.12, 5.12,
                dimensions);

        double[] optimalVector = algo.Optimize(10000, 0.1);
        for (double v : optimalVector) {
            System.out.print(v + ", ");
        }

    }
}