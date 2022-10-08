import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.List;

public class SAP {
    private final Digraph G;

    private class Ancestor {
        int distance;
        int ancestor;

        Ancestor(int ancestor, int distance) {
            this.ancestor = ancestor;
            this.distance = distance;
        }
    }

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        this.G = new Digraph(G);
    }

    private boolean isValidVertex(int v) {
        return (v >= 0 && v < G.V());
    }

    private void ValidateArguments(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        for (int u : v) {
            if (!isValidVertex(u)) {
                throw new IllegalArgumentException();
            }
        }
        for (int u : w) {
            if (!isValidVertex(u)) {
                throw new IllegalArgumentException();
            }
        }
    }

    private Ancestor findCommonAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        ValidateArguments(v, w);
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(G, w);

        int minDistance = Integer.MAX_VALUE;
        Ancestor ancestor = null;
        for (int u = 0; u < G.V(); u++) {
                if (vPaths.hasPathTo(u) && wPaths.hasPathTo(u)) {
                int distance = vPaths.distTo(u) + wPaths.distTo(u);
                if (distance < minDistance) {
                    minDistance = distance;
                    ancestor = new Ancestor(u, distance);
                }
            }
        }
        return ancestor;
    }

    private static List<Integer> makeList(int value) {
        List<Integer> list = new ArrayList<>();
        list.add(value);
        return list;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(makeList(v), makeList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(makeList(v), makeList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        Ancestor ancestor = findCommonAncestor(v, w);
        if (ancestor == null) {
            return -1;
        } else {
            return ancestor.distance;
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        Ancestor ancestor = findCommonAncestor(v, w);
        if (ancestor == null) {
            return -1;
        } else {
            return ancestor.ancestor;
        }
    }
}