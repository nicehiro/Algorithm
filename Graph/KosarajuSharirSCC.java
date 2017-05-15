package Graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-13.
 */
public class KosarajuSharirSCC {

    private boolean[] marked;
    private int[] id;
    private int count;

    /*
    * 配合下面的 dfs 算法一起获得图中所有的强连通分量
    * 得到图 g 的逆的逆后序遍历
    * 然后对得到的队列进行深度优先遍历
     */
    public KosarajuSharirSCC(Digraph g) {
        DepthFirstOrder dfo = new DepthFirstOrder(g.reverse());
        marked = new boolean[g.V()];
        id = new int[g.V()];
        for (int v : dfo.reversePost()) {
            if (!marked[v]) {
                dfs(g, v);
                count ++;
            }
        }
    }

    /*
    * 获得图中所有的强连通分量
    * 强连通分量：如果存在 c-v 那么一定也存在 v-c
    * 如果直接使用 g 的逆后序遍历进行深度优先搜索，得不到你所要的结果
    * 使用 count 记录不同的强连通分量
     */
    private void dfs(Digraph g, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w);
        }
    }

    public int count() {
        return count;
    }

    public boolean stronglyConnected(int v, int w) {
        validDataVertex(v);
        validDataVertex(w);
        return id[v] == id[w];
    }

    public int id(int v) {
        validDataVertex(v);
        return id[v];
    }

    private void validDataVertex(int v) {
        if (v<0 || v>marked.length)
            throw new IllegalArgumentException("number must between 0 and V");
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph g = new Digraph(in);
        KosarajuSharirSCC scc = new KosarajuSharirSCC(g);

        int m = scc.count;
        StdOut.println(m + "strong components");

        Queue<Integer>[] components = new Queue[m];
        for (int i=0; i<m; i++) {
            components[i] = new Queue<>();
        }
        for (int v=0; v<g.V(); v++) {
            components[scc.id(v)].enqueue(v);
        }

        for (int i=0; i<m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
