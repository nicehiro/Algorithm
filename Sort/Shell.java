package Sort;

/**
 * Created by hiro on 16-9-22.
 */
public class Shell {

    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
/*
h 相当于是一个间隔，每个 h 个单位的数列必须有序 —— 这里我们定义为 h 有序数列
通过改变每次 h 的不同使整个数列有序

此算法成立条件：一个 h 有序数列按照增幅 k 排序后仍然还是 h 有序
 */
        while (h < N/3) h = 4*h + 1;

        while (h >= 1) {
            for (int i=h; i<N; i++) {
                for (int j=i; j>=h && less(a[j], a[j-h]); j-=h) {
                    exchange(a, j, j-h);
                }
            }
            h = h/3;
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
        sort(a);
        assert isSorted(a);
        show(a);
    }

}
