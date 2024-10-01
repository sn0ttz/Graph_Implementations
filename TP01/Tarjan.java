import java.util.Iterator;
import java.util.Stack;

public class Tarjan extends Graph {
    // Atributes
    private int n; // number of vertices
    private int time;
    private AdjacentList adj;

    // Constuctor
    public Tarjan(int n, AdjacentList adj) {
        this.n = n;
        this.time = 0;
        this.adj = new AdjacentList(adj);
    }

    // Getters and Setters
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    // Methods
    public void conectedComponents(int u, int low[], int disc[], boolean stackMember[], Stack<Integer> st) {
        // Initialize discovery time and low value
        disc[u] = low[u] = time;
        time++;
        stackMember[u] = true;
        st.push(u);

        int w; // successor of the current vertex

        Iterator<Integer> i = adj.VertexList.get(u).sucessorList.iterator();

        while(i.hasNext()) {
            n = i.next();
            if (disc[n] == -1);
        }

    }

    public void conectedComponents() {
        int disc[] = new int[n]; // Discovery time of vertices
        int low[] = new int[n]; // Earliest visited vertex that can be reached from subtree rooted with current vertex
        for (int i = 0; i < n; i++) {
            disc[i] = -1;
            low[i] = -1;
        }

        boolean stackMember[] = new boolean[n]; // To check if a vertex is in the stack
        Stack<Integer> stack = new Stack<>(); // Stack to store all vertices that are in the current SCC

        // !! call recursive function
    }
}
