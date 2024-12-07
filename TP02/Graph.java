package TP02;

import java.util.*;
import java.util.function.Consumer;

class Graph {
    private final Map<Integer, List<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(int src, int dest, double weight) {
        adjacencyList.computeIfAbsent(src, k -> new ArrayList<>()).add(new Edge(dest, weight));
        adjacencyList.computeIfAbsent(dest, k -> new ArrayList<>()).add(new Edge(src, weight)); // Assuming undirected graph
    }

    public Map<Integer, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public void printGraph() {
        adjacencyList.forEach((key, value) -> System.out.println("Vértice " + key + ": " + value));
    }

    public Map<Integer, Map<Integer, Double>> johnson() {
        int numVertices = adjacencyList.size();
        Graph newGraph = new Graph();

        // Add a new vertex s and connect it to all other vertices with edge weight 0
        adjacencyList.keySet().forEach(v -> newGraph.addEdge(numVertices, v, 0));

        // Add all original edges to the new graph
        adjacencyList.forEach((u, edges) -> edges.forEach(edge -> newGraph.addEdge(u, edge.destiny, edge.weight)));

        // Run Bellman-Ford algorithm from the new vertex s
        Map<Integer, Double> h = newGraph.bellmanFord(numVertices);

        // Reweight the edges
        adjacencyList.forEach((u, edges) -> edges.forEach(edge -> edge.weight += h.get(u) - h.get(edge.destiny)));

        // Run Dijkstra's algorithm for each vertex
        Map<Integer, Map<Integer, Double>> distances = new HashMap<>();
        adjacencyList.keySet().forEach(u -> distances.put(u, dijkstra(u)));

        // Restore the original edge weights
        adjacencyList.forEach((u, edges) -> edges.forEach(edge -> edge.weight -= h.get(u) - h.get(edge.destiny)));

        return distances;
    }

    public Map<Integer, Double> bellmanFord(int startVertex) {
        int numVertices = adjacencyList.size();
        Map<Integer, Double> distances = new HashMap<>();
        adjacencyList.keySet().forEach(v -> distances.put(v, Double.MAX_VALUE));
        distances.put(startVertex, 0.0);

        for (int i = 0; i < numVertices - 1; i++) {
            adjacencyList.forEach((u, edges) -> edges.forEach(edge -> {
                if (distances.get(u) + edge.weight < distances.get(edge.destiny)) {
                    distances.put(edge.destiny, distances.get(u) + edge.weight);
                }
            }));
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

    public Result calculateKcenters(int k) {
        Set<Integer> centers = new HashSet<>();
        centers.add(getHighestDegreeVertex()); // Escolhe um vértice arbitrário para ser o primeiro centro. Neste caso,
                                               // o vértice de maior grau pois este tem mais chance de ser um centro do
                                               // que um vértice aleatório.

        Map<Integer, Map<Integer, Double>> allPairsShortestPaths = getShortestPaths(); // Utiliza-se o método de jhonson
                                                                                       // para calcular os caminhos
                                                                                       // mínimos entre todos os
                                                                                       // vértices
        Map<Integer, Double> minDistances = new HashMap<>();
        adjacencyList.keySet().forEach(v -> minDistances.put(v, Double.MAX_VALUE));

        double R = -1; // Inicializa o raio

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
            centers.add(newCenter);

            // Atualiza o raio
            if (maxMinDist > R) {
                R = maxMinDist;
            }
        }
        return new Result(centers, R);
    }

    public int getHighestDegreeVertex() {
        return adjacencyList.entrySet().stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().size()))
                .map(Map.Entry::getKey)
                .orElse(0);
    }

    public Result calculateKcentersBruteForce(int k) {
        Map<Integer, Map<Integer, Double>> allPairsShortestPaths = getShortestPaths();
        List<Integer> vertices = new ArrayList<>(adjacencyList.keySet());
    
        // Matriz de distâncias para acesso mais rápido
        double[][] distanceMatrix = new double[vertices.size()][vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                distanceMatrix[i][j] = allPairsShortestPaths.get(vertices.get(i)).getOrDefault(vertices.get(j), Double.MAX_VALUE);
            }
        }
    
        // Classe interna para encapsular os resultados
        class ResultContainer {
            double bestRadius = Double.MAX_VALUE;
            Set<Integer> bestCenters = new HashSet<>();
        }
        ResultContainer result = new ResultContainer();
    
        // Inicializar bestRadius com o raio de uma solução aproximada
        Result greedyResult = calculateKcenters(k);
        result.bestRadius = greedyResult.radius;
    
        // Função para avaliar uma combinação específica de centros
        Consumer<Set<Integer>> evaluateCenters = centers -> {
            double maxMinDist = 0.0;
    
            for (int i = 0; i < vertices.size(); i++) {
                double minDist = Double.MAX_VALUE;
                for (int center : centers) {
                    int centerIndex = vertices.indexOf(center);
                    minDist = Math.min(minDist, distanceMatrix[i][centerIndex]);
                }
                maxMinDist = Math.max(maxMinDist, minDist);
    
                // Interrompe se a distância já é pior que o melhor raio
                if (maxMinDist >= result.bestRadius) {
                    return;
                }
            }
    
            // Atualiza o melhor resultado
            if (maxMinDist < result.bestRadius) {
                result.bestRadius = maxMinDist;
                result.bestCenters = new HashSet<>(centers);
            }
        };
    
        // Processar combinações
        processCombinations(vertices, k, 0, new HashSet<>(), evaluateCenters, System.currentTimeMillis(), 5 * 60 * 1000); // 5 minutos

    
        return new Result(result.bestCenters, result.bestRadius);
    }
    
    // Função auxiliar para gerar combinações
    private void processCombinations(List<Integer> vertices, int k, int start, Set<Integer> currentCombination, Consumer<Set<Integer>> evaluateCenters, long startTime, long maxDurationMillis) {
        if (System.currentTimeMillis() - startTime > maxDurationMillis) {
            return; // Interrompe ao atingir o tempo máximo
        }
    
        if (currentCombination.size() == k) {
            evaluateCenters.accept(currentCombination);
            return;
        }
    
        for (int i = start; i < vertices.size(); i++) {
            currentCombination.add(vertices.get(i));
            processCombinations(vertices, k, i + 1, currentCombination, evaluateCenters, startTime, maxDurationMillis);
            currentCombination.remove(vertices.get(i)); // Backtrack
        }
    }
    
}

// classe criada apenas para retorno facilitado do resultado em
// calculateKcenters
class Result {
    Set<Integer> centers;
    double radius;

    public Result(Set<Integer> centers, double radius) {
        this.centers = centers;
        this.radius = radius;
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