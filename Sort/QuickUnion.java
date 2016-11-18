package Sort;

import edu.princeton.cs.algs4.StdIn;

/**
 * Created by hiro on 16-9-17.
 */
public class QuickUnion {

    private int[] id;
    private int count;

    public QuickUnion (int number) {
        count = number;
        id = new int[number];
        for (int i=0; i<number; i ++) {
            id[i] = i;
        }
    }

    public int getCount () {
        return count;
    }

    public void union(int p, int q) {
        if (id[p] == id[q])
            return;
        /*
        相当于是森林一样，每个数组 id[i] 的值都是它所指向的下一个位置，下一个 id
         */
        id[id[p]] = id[q];

        count --;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        /*
        下面这代码实在是精妙！
        第一遍看的时候觉得和原来没啥不同，但之后才发现 while 的神奇作用
        相当于是一个递归一级一级的找到根 —— root
         */
        while (id[p] != p) p = id[p];
        return p;
    }

    public static void main(String[] args) {
        int number = StdIn.readInt();
        UnionFind uf = new UnionFind(number);
        System.out.println(uf.getCount() + " components");

        while(!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
        }
        System.out.println(uf.getCount() + " components");
    }

}
