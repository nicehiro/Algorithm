package Sort;

import edu.princeton.cs.algs4.Bag;

import java.util.Scanner;

/**
 * Created by hiro on 16-9-16.
 */
public class HigherFor {

    public static void main(String[] args) {
        Bag<Double> numbers = new Bag<Double>();

        Scanner in = new Scanner(System.in);

        while(in.hasNext())
            numbers.add(in.nextDouble());
        int N = numbers.size();

        double sum = 0;
        for (double x : numbers) {
            sum += x;
        }

    }

}
