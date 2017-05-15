package Graph;

/**
 * Created by hiro on 17-5-12.
 */
public class Topological {

    private Iterable<Integer> order;
    private int[] rank;

    /*
    * 拓扑排序
    * 按照事件的优先级排序，即，要向执行某个操作，必须在另一个操作执行之后才能执行
    * 使用的深度优先搜索逆后序遍历的结果
     */
    public Topological(Digraph g) {
        DirectedCycle finder = new DirectedCycle(g);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfo = new DepthFirstOrder(g);
            order = dfo.reversePost();
            rank = new int[g.V()];
            int i = 0;
            for (int v : order) {
                rank[v] = i ++;
            }
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean hasOrder() {
        return order != null;
    }

    public boolean isDAG() {
        return hasOrder();
    }

    public int rank(int v) {
        validDataVertex(v);
        if (hasOrder()) return rank[v];
        else return -1;
    }

    private void validDataVertex(int v) {
        if (v<0 || v>=rank.length)
            throw new IllegalArgumentException("number must between 0 and V");
    }

    public static void main(String[] args) {}
}
