import java.util.Arrays;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;

class State {
    int vertex;
    int parent;
    Iterator<Integer> iterator;

    public State(int vertex, int parent) {
        this.vertex = vertex;
        this.parent = parent;
    }
}

public class Tarjan {
    // Atributes
    private int time;
    private ArrayList<ArrayList<int[]>> bc = new ArrayList<>(); // biconnected components
    private Stack<int[]> edgeStack = new Stack<>(); // stack of edges
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

        // if there are edges left in the stack
        if (!edgeStack.isEmpty()) {
            ArrayList<int[]> lastComponent = new ArrayList<>();
            while (!edgeStack.isEmpty()) {
                lastComponent.add(edgeStack.pop());
            }
            bc.add(lastComponent);
        }

        printBiconnectedComponents();
    }

    // iteractive method to find BCs
    public void findBCUtil(int start, int parent, boolean[] visited, AdjacentList graph) {
        Stack<State> stack = new Stack<>(); // stack of states
        stack.push(new State(start, parent));

        while (!stack.isEmpty()) {
            State state = stack.peek();
            int v = state.vertex;
            int p = state.parent;

            // if vertex is not visited
            if (!visited[v]) {
                visited[v] = true;
                disc[v] = low[v] = ++time;
                state.iterator = graph.VertexList.get(v).sucessorList.iterator();
            }

            boolean backtrack = true;

            // loop through all successors
            while (state.iterator.hasNext()) {
                int w = state.iterator.next();
                if (!visited[w]) {
                    // tree edge found
                    edgeStack.push(new int[] { v, w });
                    stack.push(new State(w, v));
                    backtrack = false;
                    break;
                } else if (w != p && disc[w] < disc[v]) {
                    // return edge found
                    edgeStack.push(new int[] { v, w });
                    low[v] = Math.min(low[v], disc[w]);
                }
            }

            // once all neighbors are explored
            if (backtrack) {
                stack.pop();
                if (p != -1) { // p is not the root
                    low[p] = Math.min(low[p], low[v]); // update low number of parent

                    // checks if v is an articulation point
                    if (low[v] >= disc[p]) {
                        // new biconnected component
                        ArrayList<int[]> component = new ArrayList<>();
                        int[] edge;

                        do {
                            edge = edgeStack.pop();
                            component.add(edge);
                        } while (edge[0] != p || edge[1] != v);
                        bc.add(component);
                    }
                }
            }
        }
    }

    private void printBiconnectedComponents() {
        System.out.println("Componentes biconexos:");
        for (ArrayList<int[]> component : bc) {
            System.out.print("{ ");
            for (int[] edge : component) {
                System.out.print("(" + edge[0] + ", " + edge[1] + ") ");
            }
            System.out.println("}");
        }
    }
}