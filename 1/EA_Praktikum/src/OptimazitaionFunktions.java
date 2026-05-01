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
}
