import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;

public class StrBFS {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked; // marked[v] = is there an s-v path
    private int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo; // distTo[v] = number of edges shortest s-v path
    private int V = 5; //could be given from graph wordlength
    StrDigraphThread graph; 
    //StrDigraphThread2 graph; 
    //StrDigraphThread2 graph; 


    public StrBFS(StrDigraphThread graph, String source) {
    //public StrBFS(StrDigraphThread2 graph, String source) {
        this.graph = graph; 
        marked = new boolean[graph.V()];
        distTo = new int[graph.V()];
        edgeTo = new int[graph.V()];
        validateVertex(source);
        bfs(source);

        assert check(source);
    }


    // breadth-first search from a single source
    private void bfs(String source) {
        Queue<Integer> q = new Queue<>();
        for (int v = 0; v < graph.V(); v++)
            distTo[v] = INFINITY;
        int sourceIndex = graph.findSourceIndex(source);
        distTo[sourceIndex] = 0;
        marked[sourceIndex] = true;
        q.enqueue(sourceIndex);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            Bag<String> b = (Bag<String>) graph.adjacency[v];
            for (String w : b) {
                int index = graph.getWordIndex(w);
                if (!marked[index]) {
                    edgeTo[index] = v;
                    distTo[index] = distTo[v] + 1;
                    marked[index] = true;
                    q.enqueue(index);
                }
            }
        }
    }


    /**
     * Is there a path between the source vertex {@code s} (or sources) and vertex
     * {@code v}?
     * 
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(String v) {
        validateVertex(v);
        return marked[graph.getWordIndex(v)];
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex
     * {@code s} (or sources) and vertex {@code v}?
     * 
     * @param v the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(String v) {
        validateVertex(v);
        return distTo[graph.getWordIndex(v)];
    }

    /**
     * Returns a shortest path between the source vertex {@code s} (or sources) and
     * {@code v}, or {@code null} if no such path.
     * 
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(String v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = graph.getWordIndex(v); distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x); // source
        return path;
    }

    // check optimality conditions for single source
    private boolean check(String s) {

        // check that the distance of s = 0
        if (distTo[graph.getWordIndex(s)] != 0) {
            StdOut.println("distance of source " + s + " to itself = " + distTo[graph.getWordIndex(s)]);
            return false;
        }

        // check that for each edge v-w dist[w] <= dist[v] + 1
        // provided v is reachable from s
        for (int v = 0; v < graph.V(); v++) {
            Bag<String> bag = (Bag<String>) graph.adjacency[v];
            for (String w : bag) {
                if (hasPathTo(graph.getWordFromIndex(v)) != hasPathTo(w)) {
                    StdOut.println("edge " + v + "-" + w);
                    StdOut.println("hasPathTo(" + graph.getWordFromIndex(v) + ") = " + hasPathTo(graph.getWordFromIndex(v)));
                    StdOut.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }
                if (hasPathTo(graph.getWordFromIndex(v)) && (distTo[graph.getWordIndex(w)] > distTo[v] + 1)) {
                    StdOut.println("edge " + graph.getWordFromIndex(v) + "-" + w);
                    StdOut.println("distTo[" + v + "] = " + distTo[v]);
                    StdOut.println("distTo[" + w + "] = " + distTo[graph.getWordIndex(w)]); // maybe index in both
                    return false;
                }
            }
        }

        // check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
        // provided v is reachable from s
        for (int w = 0; w < graph.V(); w++) {
            if (!hasPathTo(graph.getWordFromIndex(w)) || graph.getWordFromIndex(w).equals(s))
                continue;
            int v = edgeTo[w];
            if (distTo[w] != distTo[v] + 1) {
                StdOut.println("shortest path edge " + graph.getWordFromIndex(v) + "-" + graph.getWordFromIndex(w));
                StdOut.println("distTo[" + v + "] = " + distTo[v]);
                StdOut.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }

        return true;
    }

    private void validateVertex(String v) {
        if (v.substring(v.length()-V).length() != V)
            throw new IllegalArgumentException("vertex " + v + " is not a string of " + V + " characters");
    }

}