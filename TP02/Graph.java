package TP02;

import java.util.*;

class Graph {
    private final Map<Integer, List<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(int src, int dest, double weight) {
        adjacencyList.putIfAbsent(src, new ArrayList<>());
        adjacencyList.get(src).add(new Edge(dest, weight));
        adjacencyList.putIfAbsent(dest, new ArrayList<>());
        adjacencyList.get(dest).add(new Edge(src, weight)); // Assuming undirected graph
    }

    public Map<Integer, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public void printGraph() {
        for (Map.Entry<Integer, List<Edge>> entry : adjacencyList.entrySet()) {
            System.out.println("Vértice " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public Map<Integer, Map<Integer, Double>> johnson() {
        int numVertices = adjacencyList.size();
        Graph newGraph = new Graph();

        // Add a new vertex s and connect it to all other vertices with edge weight 0
        for (int v : adjacencyList.keySet()) {
            newGraph.addEdge(numVertices, v, 0);
        }

        // Add all original edges to the new graph
        for (int u : adjacencyList.keySet()) {
            for (Edge edge : adjacencyList.get(u)) {
                newGraph.addEdge(u, edge.destiny, edge.weight);
            }
        }

        // Run Bellman-Ford algorithm from the new vertex s
        Map<Integer, Double> h = newGraph.bellmanFord(numVertices);

        // Reweight the edges
        for (int u : adjacencyList.keySet()) {
            for (Edge edge : adjacencyList.get(u)) {
                edge.weight += h.get(u) - h.get(edge.destiny);
            }
        }

        // Run Dijkstra's algorithm for each vertex
        Map<Integer, Map<Integer, Double>> distances = new HashMap<>();
        for (int u : adjacencyList.keySet()) {
            distances.put(u, dijkstra(u));
        }

        // Restore the original edge weights
        for (int u : adjacencyList.keySet()) {
            for (Edge edge : adjacencyList.get(u)) {
                edge.weight -= h.get(u) - h.get(edge.destiny);
            }
        }

        return distances;
    }

    public Map<Integer, Double> bellmanFord(int startVertex) {
        int numVertices = adjacencyList.size();
        Map<Integer, Double> distances = new HashMap<>();
        for (int v : adjacencyList.keySet()) {
            distances.put(v, Double.MAX_VALUE);
        }
        distances.put(startVertex, 0.0);

        for (int i = 0; i < numVertices - 1; i++) {
            for (int u : adjacencyList.keySet()) {
                for (Edge edge : adjacencyList.get(u)) {
                    if (distances.get(u) + edge.weight < distances.get(edge.destiny)) {
                        distances.put(edge.destiny, distances.get(u) + edge.weight);
                    }
                }
            }
        }

        return distances;
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

    public Map<Integer, Map<Integer, Double>> getShortestPaths() {
        return johnson();
    }

    public Set<Integer> calculateKcenters(int k) {
        Set<Integer> centers = new HashSet<>();
        centers.add(getHighestDegreeVertex()); // Escolhe um vértice arbitrário para ser o primeiro centro. Neste caso,
                                               // o vértice de maior grau pois este tem mais chance de ser um centro do
                                               // que um vértice aleatório.

        Map<Integer, Map<Integer, Double>> allPairsShortestPaths = getShortestPaths(); // Utiliza-se o método de jhonson
                                                                                       // para calcular os caminhos
                                                                                       // mínimos entre todos os
                                                                                       // vértices
        Map<Integer, Double> minDistances = new HashMap<>();
        for (int v : adjacencyList.keySet()) {
            minDistances.put(v, Double.MAX_VALUE);
        }

        while (centers.size() < k) {
            int newCenter = -1;
            double maxMinDist = -1;

            // Atualiza as distâncias mínimas para cada vértice
            for (int v : adjacencyList.keySet()) {
                double minDist = minDistances.get(v);
                for (int c : centers) {
                    double dist = allPairsShortestPaths.get(c).getOrDefault(v, Double.MAX_VALUE);
                    if (dist < minDist) {
                        minDist = dist;
                    }
                }
                minDistances.put(v, minDist);

                // Encontra o vértice com a maior distância mínima
                if (minDist > maxMinDist) {
                    maxMinDist = minDist;
                    newCenter = v;
                }
            }
            System.out.println("Restam " + (k - centers.size() - 1) + " centros a serem adicionados");
            centers.add(newCenter);
        }
        System.out.println("Centers: " + centers);
        return centers;
    }

    public int getHighestDegreeVertex() {
        int highestDegree = 0;
        int highestDegreeVertex = 0;
        for (Map.Entry<Integer, List<Edge>> entry : adjacencyList.entrySet()) {
            if (entry.getValue().size() > highestDegree) {
                highestDegree = entry.getValue().size();
                highestDegreeVertex = entry.getKey();
            }
        }
        return highestDegreeVertex;
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
        return "destino: " + destiny + ", peso: " + weight;
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