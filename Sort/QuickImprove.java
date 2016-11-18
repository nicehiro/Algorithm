package Sort;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by hiro on 16-10-2.
 */
public class QuickImprove {

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int low, int high) {
        if (high <= low) return;

        int it = low;
        int gt = high;
        int i = low + 1;
        Comparable v = a[low];

        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exchange(a, it++, i++);
            else if (cmp > 0) exchange(a, i, gt--);
            else i ++;
        }
        sort(a, low, it-1);
        sort(a, gt+1, high);
    }

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exchange(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void show(Comparable[] a) {
        for (int i=0; i<a.length; i++)
            System.out.println(a[i]);
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i=0; i<a.length; i++) {
            if (less(a[i], a[i-1]))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = args;
        sort(a);
        assert isSorted(a);
        show(a);
    }

}
