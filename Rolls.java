import edu.princeton.cs.algs4.Counter;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by hiro on 16-9-14.
 */
public class Rolls {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int T = Integer.parseInt(in.next());

        int SIDEs = 6;

        Counter[] rolls = new Counter[SIDEs + 1];

        for(int i=1; i<=SIDEs; i++) {
            rolls[i] = new Counter(i + "'s");
        }

        Random random = new Random();
        double answer = random.nextDouble();

        for(int t=0; t<T; t++) {
            int result = StdRandom.uniform(1, SIDEs+1);
            rolls[result].increment();
        }

        for (int i=1; i<= SIDEs; i++) {
            System.out.println(rolls[i]);
        }

    }

}
