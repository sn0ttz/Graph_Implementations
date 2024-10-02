import java.util.Arrays;
import java.util.Stack;
import java.util.ArrayList;

class State {
    int vertex;
    int parent;

    public State(int vertex, int parent) {
        this.vertex = vertex;
        this.parent = parent;
    }
}

public class Tarjan {
    // Atributes
    private int time;
    private ArrayList<ArrayList<int[]>> bc = new ArrayList<>(); // biconnected components
    private Stack<State> stack = new Stack<>(); // stack of states
    int disc[]; // discovery time of vertex
    int low[]; // low number of vertex

    // Constructor
    public Tarjan() {
        this.time = 0;
    }

    // find biconnected components
    public void findBC(AdjacentList graph) {
        int n = graph.VertexList.size(); // number of vertexes
        boolean visited[] = new boolean[n]; // visited vertex
        disc = new int[n];
        low = new int[n];

        // loops through all vertexes
        for (Vertex vertex : graph.VertexList) {
            if (!visited[vertex.number]) {
                findBCUtil(vertex.number, -1, visited, graph);
            }
        }

    }

    // iteractive method to find BCs
    public void findBCUtil(int start, int parent, boolean[] visited, AdjacentList graph) {
        
    }
}
