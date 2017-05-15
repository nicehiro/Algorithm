package Graph;

/**
 * Created by hiro on 17-5-13.
 */
public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double weight;

    private void validDataVertex(int v) {
        if (v < 0) throw new IllegalArgumentException("number must > 0");
    }

    public Edge(int v, int w, double weight) {
        if (Double.isNaN(weight))
            throw new IllegalArgumentException("weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int v) {
        if (v == this.v)
            return w;
        else if (v == this.w)
            return v;
        else throw new IllegalArgumentException("Illegal point");
    }

    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.weight, o.weight);
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }
}
