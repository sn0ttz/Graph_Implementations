import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomGraph {

    public static AdjacentList generateRandomGraph(int Vertices, Integer Edges) {
        AdjacentList graph = new AdjacentList(Vertices);

        long temp = Vertices - 1;
        temp = temp * Vertices;
        temp = temp / 2; 
        long maxEdges = temp;

        // gera um número aleatório de arestas caso não seja fornecido
        if (Edges == null) {
            Random rand = new Random();
            int minEdge = Vertices - 1;
            Edges = rand.nextInt(minEdge * 10 - minEdge) + minEdge;
        }


        // verifica se o número de arestas não é maior que o máximo possível
        Edges = (int) Math.min(Edges, maxEdges);
        System.out.println("Generating graph with " + Vertices + " vertices and " + Edges + " edges");

        temp = Vertices - 1;
        temp = Vertices * temp;
        double density = (double) Edges / (temp);
        
       
        System.out.printf("Density: %.9f \n", density);

        Set<String> existingEdges = new HashSet<>();
        Random rand = new Random();

        List<Integer> vertices = new ArrayList<>();
        for (int v = 0; v < Vertices; v++) {
            vertices.add(v);
        }

        Collections.shuffle(vertices, rand);

        // adiciona arestas para tornar o grafo conexo
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

        // adiciona a aresta se ela ainda não existir
        if (existingEdges.add(edgeKey)) {
            graph.add(smaller, bigger);
            graph.add(bigger, smaller);
        }
    }
}
