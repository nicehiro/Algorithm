package String;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-17.
 */
public class LSD {

    private static final int BITS_PRE_BYTE = 8;

    /*
    * 低位优先的字符串排序 Least-Significant-Digit First
    * 定长字符串
    * 从右向左以每个位置的字符作为键，用键索引法将字符串排序 W 遍（W 为字符串的长度）
     */
    public static void sort(String[] a, int w) {
        int n = a.length;
        int R = 256;
        String[] aux = new String[n];

        for (int d=w-1; d>=0; d--) {
            int[] count = new int[R+1];

            // 计算概率
            for (int i=0; i<n; i++) {
                count[a[i].charAt(d) + 1] ++;
            }

            // 将概率转换为索引
            // 这样得到的 count 数组中每个元素的值就是那个元素之前所有元素的个数总和
            for (int r=0; r<R; r++) {
                count[r+1] += count[r];
            }

            // 将元素分类
            for (int i=0; i<n; i++) {
                aux[count[a[i].charAt(d)] ++] = a[i];
            }

            // 回写
            for (int i=0; i<n; i++) {
                a[i] = aux[i];
            }
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;

        int w = a[0].length();
        for (int i=0; i<n; i++) {
            assert a[i].length() == w : "String must have fixed length";
        }

        System.out.println(a[1].charAt(1));

        sort(a, w);
        for (int i=0; i<n; i++) {
            StdOut.println(a[i]);
        }
    }
}
