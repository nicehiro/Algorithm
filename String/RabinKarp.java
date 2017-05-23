package String;

import edu.princeton.cs.algs4.StdOut;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by hiro on 17-5-22.
 */
public class RabinKarp {

    private String pat;
    private long patHash;
    private int m;
    private long q;
    private int R;
    private long RM;

    /*
    * q：比较大的素数
    * m：模式字符串的长度
    * RM：R^(m-1) % q
     */
    public RabinKarp(String pat) {
        this.pat = pat;
        R = 256;
        m = pat.length();
        q = longRandomPrime();

        RM = 1;
        for (int i=1; i<=m-1; i++)
            RM = (R * RM) % q;
        patHash = hash(pat, m);
    }

    /*
    * 关键的地方在每次未找到匹配时，去掉第一个字符，加上最后一个字符的操作
    * txtHash 是已经取余之后的数了，所以每次去掉一个字符时，需要将那个字符也进行取余操作
     */
    public int search(String txt) {
        int n = txt.length();
        if (n < m) return n;
        long txtHash = hash(txt, m);
        if ((patHash == txtHash) && check(txt, 0))
            return 0;

        for (int i=m; i<n; i++) {
            txtHash = (txtHash + q - RM*txt.charAt(i-m) % q) % q;
            txtHash = (txtHash*R + txt.charAt(i)) % q;

            int offset = i - m + 1;
            if ((patHash == txtHash) && check(txt, offset))
                return offset;
        }
        return n;
    }

    private boolean check(String txt, int i) {
        for (int j=0; j<m; j++)
            if (pat.charAt(j) != txt.charAt(i+j))
                return false;
        return true;
    }

    private static long longRandomPrime() {
        BigInteger prim = BigInteger.probablePrime(31, new Random());
        return prim.longValue();
    }

    private long hash(String key, int m) {
        long h = 0;
        for (int j=0; j<m; j++)
            h = (R * h + key.charAt(j)) % q;
        return h;
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        RabinKarp searcher = new RabinKarp(pat);
        int offset = searcher.search(txt);

        // print results
        StdOut.println("text:    " + txt);

        // from brute force search method 1
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
