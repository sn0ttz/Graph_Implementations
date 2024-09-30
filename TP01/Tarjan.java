import java.util.Iterator;
import java.util.LinkedList;

public class Tarjan {
    // Atributes
    // todo private AdjacentList graph;
    private int n; // number of vertices
    private ArrayList<Integer> adj[]; // adjacency list
    private int time;

    // Constuctor
    public Tarjan(int n) {
        this.n = n;
        this.time = 0;
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    // Methods
    // Add edge to graph
    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    


    // main test
    public static void main(String args[]) {

        // Create a graph given in the above diagram
        Tarjan g1 = new Tarjan(5);

        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        System.out.println("Componentes fortemente conexos do primeiro grafo ");
        g1.SCC();

        Tarjan g2 = new Tarjan(4);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        System.out.println("\nComponentes fortemente conexos do segundo grafo ");
        g2.SCC();

        Tarjan g3 = new Tarjan(7);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(1, 3);
        g3.addEdge(1, 4);
        g3.addEdge(1, 6);
        g3.addEdge(3, 5);
        g3.addEdge(4, 5);
        System.out.println("\nComponentes fortemente conexos do terceiro grafo ");
        g3.SCC();

        Tarjan g4 = new Tarjan(11);
        g4.addEdge(0, 1);
        g4.addEdge(0, 3);
        g4.addEdge(1, 2);
        g4.addEdge(1, 4);
        g4.addEdge(2, 0);
        g4.addEdge(2, 6);
        g4.addEdge(3, 2);
        g4.addEdge(4, 5);
        g4.addEdge(4, 6);
        g4.addEdge(5, 6);
        g4.addEdge(5, 7);
        g4.addEdge(5, 8);
        g4.addEdge(5, 9);
        g4.addEdge(6, 4);
        g4.addEdge(7, 9);
        g4.addEdge(8, 9);
        g4.addEdge(9, 8);
        System.out.println("\nComponentes fortemente conexos do quarto grafo ");
        g4.SCC();

        Tarjan g5 = new Tarjan(5);
        g5.addEdge(0, 1);
        g5.addEdge(1, 2);
        g5.addEdge(2, 3);
        g5.addEdge(2, 4);
        g5.addEdge(3, 0);
        g5.addEdge(4, 2);
        System.out.println("\nComponentes fortemente conexos do quinto grafo ");
        g5.SCC();
    }

}
