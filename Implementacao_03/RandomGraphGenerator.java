package Implementacao_03;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RandomGraphGenerator {
    public static void generateRandomGraph(int numVertices, String filename) {
        boolean[] inMST = new boolean[numVertices];
        Queue<Edge> queue = new LinkedList<>();

        // Start with the first vertex
        inMST[0] = true;
        addEdgesToQueue(queue, 0, numVertices, inMST);

        List<Edge> edges = new ArrayList<>();

        while (!queue.isEmpty() && edges.size() < numVertices - 1) {
            Edge edge = queue.poll();
            int v = edge.destination;

            if (!inMST[v]) {
                inMST[v] = true;
                edges.add(edge);
                addEdgesToQueue(queue, v, numVertices, inMST);
            }
        }

        saveGraphToFile(numVertices, edges, filename);
    }

    private static void addEdgesToQueue(Queue<Edge> queue, int u, int numVertices, boolean[] inMST) {
        Random rand = new Random();
        for (int v = 0; v < numVertices; v++) {
            if (u != v && !inMST[v]) {
                int weight = rand.nextInt(100) + 1; // Random weight between 1 and 100
                queue.add(new Edge(u, v, weight));
            }
        }
    }

    private static void saveGraphToFile(int numVertices, List<Edge> edges, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(numVertices + " " + edges.size() + "\n");
            for (Edge edge : edges) {
                System.out.println(edge.weight); // Debugging line
                writer.write(edge.source + " " + edge.destination + " " + edge.weight + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int[] numVerticesArray = { 10, 100, 1000, 10000 }; // Example number of vertices
        for (int numVertices : numVerticesArray) {
            generateRandomGraph(numVertices, "randomGraph" + numVertices + ".txt");
        }
    }

    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
}