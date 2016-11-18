package Sort;

import edu.princeton.cs.algs4.*;

import java.util.Scanner;

/**
 * Created by hiro on 16-9-14.
 */
public class Interval2DTest {
    public static void main(String args[]) {
        System.out.println("Please Input your x and y:");
        Scanner in = new Scanner(System.in);

        System.out.print("xlow - ");
        double xlow = in.nextDouble();
        System.out.print("xhigh = ");
        double xhigh = in.nextDouble();
        System.out.print("ylow = ");
        double ylow = in.nextDouble();
        System.out.print("yhigh = ");
        double yhigh = in.nextDouble();
        System.out.print("How many Points?");
        int T = in.nextInt();

        Interval1D xinterval = new Interval1D(xlow, ylow);
        Interval1D yinterval = new Interval1D(ylow, yhigh);
        Interval2D box = new Interval2D(xinterval, yinterval);
        box.draw();

        Counter c = new Counter("hits");
        for(int t=0; t<T; t++) {
            double x = Math.random();
            double y = Math.random();
            Point2D p = new Point2D(x, y);
            if(box.contains(p)) c.increment();
            else p.draw();
        }

        StdOut.print(c);
        StdOut.print(box.area());

    }
}
