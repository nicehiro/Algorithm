/**
 * Created by hiro on 16-9-13.
 */
import java.util.Arrays;
import java.util.Scanner;
import edu.princeton.cs.algs4.*;

public class BinarySearch {

    public static int rank(int key, int[] a) {

        int low = 0;
        int high = a.length;

        while (low <= high) {

            int mid = low + (high-low)/2;

            if (key < a[mid])
                high = mid - 1;
            else if (key > a[mid])
                low = mid + 1;
            else
                return mid;

        }

        return -1;

    }

    public static void main (String[] args) {

        int[] whitelist = StdIn.readAllInts();
        Arrays.sort(whitelist);

            System.out.println("Please Input a Key:");
            Scanner in = new Scanner(System.in);

            int key = in.nextInt();

            System.out.println(rank(key, whitelist));

    }

}
