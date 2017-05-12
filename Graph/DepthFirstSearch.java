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
