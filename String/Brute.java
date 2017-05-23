package String;

import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-21.
 */
public class Brute {

    /*
    * 两种暴力子字符串查找算法
     */
    public static int search1(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();

        for (int i=0; i<=n-m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            }
            if (j == m) return i;
        }
        return n;
    }

    /*
    * 如果 i 和 j 指向的字符不匹配了，那么就需要回退这两个指针
    * j 回退为 0；i 回退为 i-j
     */
    public static int search2(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i ++) {
            if (txt.charAt(i) == pat.charAt(j)) j ++;
            else {
                i -= j;
                j = 0;
            }
        }
        if (j == m) return i-m;
        else return n;
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        int offset1a = search1(pat, txt);
        int offset2a = search2(pat, txt);

        // print results
        StdOut.println("text:    " + txt);

        // from brute force search method 1a
        StdOut.print("pattern: ");
        for (int i = 0; i < offset1a; i++)
            StdOut.print(" ");
        StdOut.println(pat);

        // from brute force search method 2a
        StdOut.print("pattern: ");
        for (int i = 0; i < offset2a; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
