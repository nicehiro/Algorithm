package String;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by hiro on 17-5-17.
 */
public class Quick3string {

    private static void sort(String[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1, 0);
    }

    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    private static void exch(String[] a, int v, int w) {
        String temp = a[v];
        a[v] = a[w];
        a[w] = temp;
    }

    /*
    * 和三向切分快速排序一样，得到三个子数组：
    * 一是含有所有小于首字母的字符串数组
    * 一是含有与首字母相等的子字符串数组
    * 还有就是大于首字母的字符串数组
    * [low, lt) letter than a[low]
    * [lt, gt] equals a[low]
    * (gt, high] greater than a[low]
     */
    private static void sort(String[] a, int low, int high, int d) {

        if (high <= low) {
            return;
        }

        int lt = low;
        int gt = high;
        int v = charAt(a[low], d);
        int i = low + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) exch(a, lt++, i++);
            else if (t > v) exch(a, i, gt--);
            else i ++;
        }

        sort(a, low, lt-1, d);
        if (v > 0) sort(a, lt, gt, d+1);
        sort(a, gt+1, high, d);
    }
}
