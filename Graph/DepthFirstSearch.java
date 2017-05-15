package Graph;

/**
 * Created by hiro on 17-5-11.
 */
public class DepthFirstSearch {
    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph g, int s) {
        marked = new boolean[g.V()];
        validDateVertex(s);
        dfs(g, s);
    }

    /*
    * 深度优先搜索
    * 就像是拿着一根绳子走迷宫，每走一个新路口做一个标记；当发现这个路口已经走过，返回上一个路口
    * marked 记录顶点 s 是否已经被遍历
    * 如果没有被遍历，接着遍历 s 开始的结点
     */
    private void dfs(Graph g, int s) {
        marked[s] = true;
        count ++;
        for (int w : g.adj(s)) {
            if (!marked(s))
                dfs(g, w);
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }

    private void validDateVertex(int s) {
        int V = marked.length;
        if (s < 0 || s > V) {
            throw new IllegalArgumentException("number must between 0 and V");
        }
    }
}
