package String;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import javax.naming.BinaryRefAddr;

/**
 * Created by hiro on 17-5-22.
 */
public class RunLength {

    private static final int R = 256;
    private static final int LG_R = 8;

    private RunLength() {}

    /*
    * 读取一个游程的长度，将当前比特按照长度复制并打印
    * 然后转换比特继续读和打印
     */
    public static void expand() {
        boolean b = false;
        while (!BinaryStdIn.isEmpty()) {
            int run = BinaryStdIn.readInt(LG_R);
            for (int i=0; i<run; i++)
                BinaryStdOut.write(b);
            b = !b;
        }
        BinaryStdOut.close();
    }

    /*
    * 每次读取一个比特，如果和上一个字符不同，记录 run 的值，再把 run 置 0，将 old 取反
    * 如果和上一个字符相同，但 run 小于计数器的最大值，将 run 增加
    * 如果和上一个字符相同，但 run 等于计数器的最大值，将 run 记录，再置 0，然后再记录 run，将 run + 1
     */
    public static void compress() {
        char run = 0;
        boolean old = false;
        while (!BinaryStdIn.isEmpty()) {
            boolean b = BinaryStdIn.readBoolean();
            if (b != old) {
                BinaryStdOut.write(run, LG_R);
                run = 0;
                old = !old;
            } else {
                if (run == R-1) {
                    BinaryStdOut.write(run, LG_R);
                    run = 0;
                    BinaryStdOut.write(run, LG_R);
                }
                run ++;
            }
        }
        BinaryStdOut.write(run, LG_R);
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
