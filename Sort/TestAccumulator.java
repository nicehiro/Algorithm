package Sort;

import edu.princeton.cs.algs4.Accumulator;
import edu.princeton.cs.algs4.StdOut;

import java.util.Random;

/**
 * Created by hiro on 16-9-15.
 */
public class TestAccumulator {
    public static void main(String[] args) {
        int T = Integer.parseInt(args[0]);

        Accumulator a = new Accumulator();

        Random random = new Random();
        double d = random.nextDouble();

        for(int i=0; i<T; i++) {
            a.addDataValue(d);
        }
        StdOut.println(a);
    }
}
