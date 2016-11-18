package Sort;

/**
 * Created by hiro on 16-10-6.
 */
public class Heap {

    private Heap() { }

    public static void sort(Comparable[] pq) {
        int n = pq.length;
        for (int k=n/2; k>=1; k--) {
            sink(pq, k, n);
        }

        while (n > 1) {
            exchange(pq, 1, n--);
            sink(pq, 1, n);
        }
    }

    private static void sink(Comparable[] pq, int k, int n) {
        while (2*k <= n) {
            int j = 2 * k;
            if (j < n && less(pq, j, j+1)) j = j + 1;
            if (!less(pq, k, j)) break;
            exchange(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return a[i-1].compareTo(a[j-1]) < 0;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable temp = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = temp;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i=1; i<a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }
        return true;
    }

    private static void show(Comparable[] a) {
        for (int i=0; i<a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = args;
        sort(a);
        assert isSorted(a);
        show(a);
        System.out.println(a[1]);
    }

}
