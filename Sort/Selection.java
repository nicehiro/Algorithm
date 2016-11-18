package Sort;

/**
 * Created by hiro on 16-9-18.
 */
public class Selection {

    public static void sort(Comparable[] a) {
        for (int i=0; i<a.length; i++) {
            int min = i;
            for (int j=i+1; j<a.length; j++)
                if (less(a[j], a[min]))
                    min = j;

            exchange(a, i, min);
        }
    }

    public static void mySort(Comparable[] a) {
        for (int i=0; i<a.length; i++) {
            for (int j=i+1; j<a.length; j++) {
                if (less(a[j], a[i])) {
                    exchange(a, i, j);
                }
            }
        }
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
        mySort(a);
        assert isSorted(a);
        show(a);
    }

}
