package String;

import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-22.
 */
public class BoyerMoore {

    private final int R = 256;
    private int[] right;
    private String pattern;

    /*
    * 子字符串查找算法
    * right[c]：记录每个字符在模式中出现的最靠右的位置
     */
    public BoyerMoore(String pat) {
        this.pattern = pat;

        right = new int[R];
        for (int c = 0; c < R; c ++)
            right[c] = -1;
        for (int j = 0; j < pattern.length(); j ++)
            right[pattern.charAt(j)] = j;
    }

    /*
    * skip：每次要移动的距离
    * 如果造成匹配失败的字符不在模式中，向右移动 j+1 个位置，即 j-[-1]
    * 如果造成匹配失败的字符在模式中，依照 right 数组来使模式字符串和文本对齐
    * 如果上述方式不能造成 i 向前移动，至少保证 i 每次匹配失败向前移动 1
     */
    public int search(String txt) {
        int m = pattern.length();
        int n = txt.length();
        int skip;

        for (int i=0; i<=n-m; i+=skip) {
            skip = 0;
            for (int j=m-1; j>=0; j--) {
                if (pattern.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j-right[txt.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;
        }
        return n;
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        BoyerMoore boyermoore1 = new BoyerMoore(pat);
        int offset1 = boyermoore1.search(txt);

        // print results
        StdOut.println("text:    " + txt);

        StdOut.print("pattern: ");
        for (int i = 0; i < offset1; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
