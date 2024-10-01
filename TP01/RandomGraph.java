import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomGraph {

    public static AdjacentList generateRandomGraph(int Vertices, Integer Edges) {
        AdjacentList graph = new AdjacentList(Vertices);

        int maxEdges = (Vertices * (Vertices - 1)) / 2;

        // Generates a random number of edges if not specified
        if (Edges == null) {
            Random rand = new Random();
            int minEdge = Vertices - 1;
            Edges = rand.nextInt(minEdge * 10 - minEdge) + minEdge;
        }

        // Verifies that the number of edges is not greater than the maximum possible
        Edges = Math.min(Edges, maxEdges);
        System.out.println("Generating graph with " + Vertices + " vertices and " + Edges + " edges");

        System.out.println("Density: " + ((double) Edges / (Vertices * (Vertices - 1))));

        Set<String> existingEdges = new HashSet<>();
        Random rand = new Random();

        List<Integer> vertices = new ArrayList<>();
        for (int v = 0; v < Vertices; v++) {
            vertices.add(v);
        }

        Collections.shuffle(vertices, rand);

        // Adds edges to make the graph connected
        for (int v = 1; v < vertices.size(); v++) {
            addEdge(graph, existingEdges, vertices.get(v), vertices.get(rand.nextInt(v)));
        }

        while (existingEdges.size() < Edges) {
            int vertex1 = rand.nextInt(Vertices);
            int vertex2 = rand.nextInt(Vertices);

            if (vertex1 != vertex2) {
                addEdge(graph, existingEdges, vertex1, vertex2);
            }
        }

        return graph;

    }

    public static void addVertex(int vertex, AdjacentList graph) {
        graph.VertexList.add(new Vertex(vertex));
    }

    private static void addEdge(AdjacentList graph, Set<String> existingEdges, int vertex1, int vertex2) {
        int smaller = Math.min(vertex1, vertex2);
        int bigger = Math.max(vertex1, vertex2);
        String edgeKey = smaller + " " + bigger;

        // Adds the edge if it doesn't already exist
        if (existingEdges.add(edgeKey)) {
            graph.add(smaller, bigger);
            graph.add(bigger, smaller);
        }
    }
}
