import edu.princeton.cs.algs4.Counter;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by hiro on 16-9-14.
 */
public class Flips {

    public static void main(String args[]) {
        System.out.println("Please Input a Integer:");
        Scanner in = new Scanner(System.in);

        int T = Integer.parseInt(in.next());
        Counter heads = new Counter("heads");
        Counter tails = new Counter("tails");

        /*
        java 产生随机数的方法，用java.util.Random这个累产生的随机数不会重复
        用 Math.random() 产生的随机数是重复的
         */
        Random random = new Random();

        for (int i=0; i<T; i++) {
            if(random.nextInt() >= 0.5) heads.increment();
            else tails.increment();
        }

        System.out.println(heads);
        System.out.println(tails);
        System.out.println(heads.tally() - tails.tally());

    }

}