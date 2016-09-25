/**
 * Created by hiro on 9/25/16.
 */
public class Merge {

    public static Comparable[] aux;
    /*
    自上而下的 sort 方法
    归并顺序如下：
    1,2 - 3,4 - 1,2,3,4 - 5,6 - 7,8 - 5,6,7,8 - 1,2,3,4,5,6,7,8
     */
    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(a, 0, a.length-1);
    }
    /*
    递归 sort 方法
     */
    private static void sort(Comparable[] a, int low, int high) {
        if (low >= high) return;

        int mid = low + (high - low) / 2;
        sort(a, low, mid);
        sort(a, mid+1, high);
        merge(a, low, mid, high);
    }
    /*
    循环遍历数组
    归并顺序如下：
    1,2 - 3,4 - 5,6 - 7,8 - 1,2,3,4 - 5,6,7,8 - 1,2,3,4,5,6,7,8
     */
    public static void sort2(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[a.length];

        for (int size=1; size<N; size+=size) {
            for (int low=0; low<N-size; low+=size+size) {
                merge(a, low, low+size-1, Math.min(low+size+size-1, N-1));
            }
        }
    }

    public static void merge(Comparable[] a, int low, int mid, int high) {
        int i = low;
        int j = mid + 1;

        for (int k=low; k<=high; k++) {
            aux[k] = a[k];
        }

        for (int k=low; k<=high; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > high) a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else a[k] = aux[j++];
        }

    }

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
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
        sort2(a);
        //sort(a);
        assert isSorted(a);
        show(a);
    }

}
