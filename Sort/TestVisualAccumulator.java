package Sort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by hiro on 16-9-15.
 */
public class TestVisualAccumulator {
    public static void main(String[] args) {
        int T = Integer.parseInt(args[0]);

        VisualAccumulator v = new VisualAccumulator(T, 1.0);
        for(int t=0; t<T; t++) {
            v.addDataValue(StdRandom.random());
        }
        StdOut.println(v);
    }
}
