package Implementacao_03;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TreeGenerator {
    public static void generateRandomTree(int numVertices, String filename) {
        Random rand = new Random();
        boolean[] inMST = new boolean[numVertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.flux));

        inMST[0] = true;
        addEdgesToPQ(pq, 0, numVertices, inMST, rand);

        List<Edge> edges = new ArrayList<>();

        while (!pq.isEmpty() && edges.size() < numVertices - 1) {
            Edge edge = pq.poll();
            int v = edge.destiny;

            if (!inMST[v]) {
                inMST[v] = true;
                edges.add(edge);
                addEdgesToPQ(pq, v, numVertices, inMST, rand);
            }
        }

        saveTreeToFile(numVertices, edges, filename);
    }

    private static void addEdgesToPQ(PriorityQueue<Edge> pq, int u, int numVertices, boolean[] inMST, Random rand) {
        for (int v = 0; v < numVertices; v++) {
            if (u != v && !inMST[v]) {
                int weight = rand.nextInt(100) + 1;
                pq.add(new Edge(u, v, weight));
            }
        }
    }

    private static void saveTreeToFile(int numVertices, List<Edge> edges, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(numVertices + " " + edges.size() + "\n");
            for (Edge edge : edges) {
                writer.write(edge.source + " " + edge.destiny + " " + edge.maxFlux + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i *= 100) {
            generateRandomTree(i, "randomTree" + i + ".txt");
        }

    }
}