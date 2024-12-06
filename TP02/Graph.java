package TP02;

import java.util.*;

public class Graph {
    private Map<Integer, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(int source, int destiny, double weight) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.get(source).add(new Edge(destiny, weight));

        adjacencyList.putIfAbsent(destiny, new ArrayList<>());
        adjacencyList.get(destiny).add(new Edge(source, weight));
    }

    public Map<Integer, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public void printGraph() {
        for (Map.Entry<Integer, List<Edge>> entry : adjacencyList.entrySet()) {
            System.out.println("Vértice " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public Map<Integer, Double> dijkstra(int startVertex) {
        Map<Integer, Double> distances = new HashMap<>();
        PriorityQueue<VertexDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(vd -> vd.distance));
        pq.add(new VertexDistance(startVertex, 0.0));
        distances.put(startVertex, 0.0);

        while (!pq.isEmpty()) {
            VertexDistance current = pq.poll();
            int currentVertex = current.vertex;

            for (Edge edge : adjacencyList.getOrDefault(currentVertex, Collections.emptyList())) {
                double newDist = distances.get(currentVertex) + edge.weight;
                if (newDist < distances.getOrDefault(edge.destiny, Double.MAX_VALUE)) {
                    distances.put(edge.destiny, newDist);
                    pq.add(new VertexDistance(edge.destiny, newDist));
                }
            }
        }

        return distances;
    }

    public void printShortestPaths(int startVertex) {
        Map<Integer, Double> distances = dijkstra(startVertex);
        for (Map.Entry<Integer, Double> entry : distances.entrySet()) {
            System.out.println(
                    "Distância do vértice " + startVertex + " ao vértice " + entry.getKey() + " é " + entry.getValue());
        }
    }

}

class Edge {
    int destiny;
    double weight;

    public Edge(int destiny, double weight) {
        this.destiny = destiny;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "(" + destiny + ", " + weight + ")";
    }
}

class VertexDistance {
    int vertex;
    double distance;

    public VertexDistance(int vertex, double distance) {
        this.vertex = vertex;
        this.distance = distance;
    }
}