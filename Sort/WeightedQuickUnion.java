package Sort;

/**
 * Created by hiro on 16-9-17.
 */
public class WeightedQuickUnion {

    private int count;
    private int[] id;
    private int[] sz;

    public WeightedQuickUnion(int count) {
        this.count = count;
        id = new int[count];
        for (int i=0; i<count; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int getCount() {return count;}

    public int find(int p) {
        while (p != id[p]) p = id[p];
        return p;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /*
    小树指向大树，权值累加
     */
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) return;

        if (sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        } else {
            id[qRoot] = pRoot;
            sz[pRoot] = sz[qRoot];
        }
    }

}
