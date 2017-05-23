package String;

import Sort.Insertion;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-17.
 */
public class MSD {

    private static final int R = 256;

    /*
    * 高位优先排序
    * 在低位优先排序基础上做了些许改动
     */
    private static void sort(String[] a) {
        int n = a.length;
        String[] aux = new String[n];
        sort(a, 0, n-1, 0, aux);
    }

    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    private static void sort(String[] a, int low, int high, int d, String[] aux) {
        /*
        * 这个方法是用来改进算法的
        * 就是当数组很小时候，直接使用插入排序
        *
        if (high <= low + limit) {
            Insertion.sort(a, low, high, d);
            return;
        } */

        if (high <= low) {
            return;
        }

        int[] count = new int[R+2];

        // 计算频率
        // 数组长度为 R+2 count
        /*
        * count 数组值的含义：
        * r=0：未使用
        * r=1：长度为 d 的字符串数量
        * 2<r<R+2：第 d 个字符的索引值是 r-2 的字符串的数量
        **/
        for (int i=low; i<=high; i++) {
            count[charAt(a[i], d) + 2] ++;
        }

        // 将频率转换为索引
        /*
        * count 数组值的含义：
        * r=0：长度为 d 的字符串的子数组的开始索引
        * 1<r<R+1：第 d 个字符的索引值是 r-1 的组字符的其实索引
        * r=R+1：未使用
        **/
        for (int r=0; r<R+1; r++) {
            count[r+1] += count[r];
        }

        // 数据分类
        /*
        * count 数组值的含义：
        * 0<r<R-1：第 d 个字符的索引值为 r 的字符串的子数组的起始索引
        * 其他：未使用
         */
        for (int i=low; i<=high; i++) {
            aux[count[charAt(a[i], d) + 1] ++] = a[i];
        }

        // 回写
        for (int i=low; i<=high; i++) {
            a[i] = aux[i-low];
        }

        // 递归的以每个字符为键进行排序
        for (int r=0; r<R; r++) {
            sort(a, low+count[r], low+count[r+1]-1, d+1, aux);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;
        sort(a);
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);
    }
}
