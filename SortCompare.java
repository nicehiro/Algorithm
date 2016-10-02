import edu.princeton.cs.algs4.Stopwatch;

import java.util.Random;

/**
 * Created by hiro on 16-9-22.
 */
public class SortCompare {

    public static double time(String alg, Double[] a) {
        Stopwatch timer = new Stopwatch();
        if (alg.equals("Insertion")) Insertion.sort(a);
        if (alg.equals("Selection")) Selection.sort(a);
        if (alg.equals("Shell")) Shell.sort(a);
        if (alg.equals("Merge")) Merge.sort(a);
        if (alg.equals("QuickImprove")) QuickImprove.sort(a);

        return timer.elapsedTime();
    }

    public static double myTime(String alg, Double[] a) {
        long start = System.currentTimeMillis();

        if (alg.equals("Insertion")) Insertion.sort(a);
        if (alg.equals("Selection")) Selection.sort(a);
        if (alg.equals("Shell")) Shell.sort(a);
        if (alg.equals("Merge")) Merge.sort(a);
        if (alg.equals("Quick")) Quick.sort(a);

        long now = System.currentTimeMillis();
        return now-start;
    }

    public static double timeRandomInput(String alg, int N, int T) {
        double total = 0.0;
        Double[] a = new Double[N];
        Random r = new Random();
        for (int t=0; t<T; t++) {
            for (int i=0; i<N; i++) {
                //a[i] = StdRandom.uniform();
                a[i] = r.nextDouble();
            }
            total += myTime(alg, a);
        }

        return total;
    }

    public static void main(String[] args) {
        String alg1 = args[0];
        String alg2 = args[1];
        int N = Integer.parseInt(args[2]);//1000
        int T = Integer.parseInt(args[3]);//100
        double t1 = timeRandomInput(alg1, N, T);
        double t2 = timeRandomInput(alg2, N, T);
        System.out.println("for " + N + " random Doubles " + alg1 + " is " + t1);
        System.out.println("for " + N + " random Doubles " + alg2 + " is " + t2);
    }

}
