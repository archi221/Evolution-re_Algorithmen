import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        OptimazitaionFunktions functions = new OptimazitaionFunktions();
        HillClimbingAlgo algo = new HillClimbingAlgo(functions::SphereFunktion, - 5.12, 5.12,
                10);

        double[] optimalVector = algo.Optimize(1000, 0.1);
        System.out.println("Sphere Funktion: ");
        for (double v : optimalVector) {
            System.out.print(v + ", ");
        }
        algo.fitnessFunction = functions::AcleyFunktion;
        algo.dimension = 2;
        double[] optimalVector2 = algo.Optimize(1000, 0.1);
        System.out.println("Acley Funktion: ");
        for (double v : optimalVector2) {
            System.out.print(v + ", ");
        }
    }

    public class HelloWorld {
        // 2.) Definition of the fitness function.
        private static int eval(Genotype<BitGene> gt) {
            return gt.chromosome()
                    .as(BitChromosome.class)
                    .bitCount();
        }

        public static void main(String[] args) {
            // 1.) Define the genotype (factory) suitable
            //     for the problem.
            Factory<Genotype<BitGene>> gtf =
                    Genotype.of(BitChromosome.of(10, 0.5));

            // 3.) Create the execution environment.
            Engine<BitGene, Integer> engine = Engine
                    .builder(HelloWorld::eval, gtf)
                    .build();

            // 4.) Start the execution (evolution) and
            //     collect the result.
            Genotype<BitGene> result = engine.stream()
                    .limit(100)
                    .collect(EvolutionResult.toBestGenotype());

            System.out.println("Hello World:\n" + result);
        }
    }
}