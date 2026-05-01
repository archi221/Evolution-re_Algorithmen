public class OptimazitaionFunktions {

    public double SphereFunktion(double[] vector){
        double summe = 0;
        final double valueRange = 5.12;
        for (double v : vector) {
            assert Math.abs(v) <= valueRange;
            summe += Math.pow(v, 2);
        }
        return summe;
    }
    public double AcleyFunktion(double[] vector){
        double firstSum = 0;
        double secondSum = 0;
        for (double v : vector) {
           firstSum += (Math.pow(v, 2) / vector.length);
           secondSum += (Math.cos(2 * Math.PI * v) / vector.length);
        }
        double firstExp = Math.exp(- 0.2 * firstSum);
        double secondExp = Math.exp(secondSum);
        return 20 + Math.E - (20 * firstExp) - secondExp;
    }
}