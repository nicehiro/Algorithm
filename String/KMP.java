package String;

import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-21.
 */
public class KMP {

    private final int R = 256;
    private int[][] dfa;

    private String pat;

    /*
    * Knuth-Morris-Pratt 子字符串查找算法
    * dfa[A][j] 的含义：在 j 状态时，再来一个 A 字符，将会到达什么状态
    * x 的含义：重启位置，即回退的位置，最开始是状态 0
    * 这段代码的主要目的是构造 DFA
    * 1. 首先，在 0 状态下来一个 pat 的第一个字符，一定会跳到状态 1，其他字符来时依旧保持状态 0
    * 2. 之后，每个状态遇到字符后的处理参照 x 遇到该字符时的处理，这也叫做匹配失败的处理；
    * 3. 除非那个字符是 pat 的下一个字符， 此时应该跳转到下一状态，这也叫做匹配成功的处理
    * 4. 最后更新 x 的位置
     */
    public KMP(String pat) {
        this.pat = pat;

        int m = pat.length();
        dfa = new int[R][m];
        dfa[pat.charAt(0)][0] = 1;                  // 1
        for (int x = 0, j = 1; j < m; j ++) {
            for (int c = 0; c< R; c ++)             // 2
                dfa[c][j] = dfa[c][x];
            dfa[pat.charAt(j)][j] = j + 1;          // 3
            x = dfa[pat.charAt(j)][x];              // 4
        }
    }

    /*
    * 根据 DFA 数组来查找匹配的字符串
     */
    public int search(String txt) {
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i ++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == m) return i - m;
        return n;
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        KMP kmp1 = new KMP(pat);
        int offset1 = kmp1.search(txt);

        // print results
        StdOut.println("text:    " + txt);

        StdOut.print("pattern: ");
        for (int i = 0; i < offset1; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
