package Sort;

import edu.princeton.cs.algs4.StdIn;

/**
 * Created by hiro on 16-9-17.
 */
public class UnionFind {

    private int[] id;
    private int count;

    public UnionFind (int number) {
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

        int pId = id[p];
        int qId = id[q];

        /*
        刚开始我以为书上的方法太笨，但后来发现我太笨，这种方法事错误的

        int temp = p;
        if (p > q) temp = q;

        if (id[i] == id[temp]) id[p] = id[q] = id[temp];
        */

        for (int i=0; i<id.length; i++) {
            if (id[i] == pId) id[i] = qId;
        }
        count --;

    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        return id[p];
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
