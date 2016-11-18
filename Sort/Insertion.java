package Sort;

/**
 * Created by hiro on 16-9-22.
 */
public class Insertion {

    public static void sort(Comparable[] a) {
        for (int i=1; i<a.length; i++) {
            for (int j=i; j>0 && less(a[j], a[j-1]); j--) {
                exchange(a, j, j-1);
            }
        }
    }

    public static void pro_sort(Comparable[] a) {
        for (int i=1; i<a.length; i++) {
            Comparable temp = a[i];
            int tempJ = i;
            for (int j=i; j>0 && less(temp, a[j-1]); j--) {
                a[j] = a[j-1];
                tempJ = j-1;
            }
            a[tempJ] = temp;
        }
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exchange(Comparable[] a, int v, int w) {
        Comparable temp = a[v];
        a[v] = a[w];
        a[w] = temp;
    }

    private static void show(Comparable[] a) {
        for (int i=0; i<a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i=1; i<a.length; i++) {
            if (less(a[i], a[i-1]))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = args;
        pro_sort(a);
        assert isSorted(a);
        show(a);
    }

}
