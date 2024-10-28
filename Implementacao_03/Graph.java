package Implementacao_03;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

//this implementation was heavily inspired by the one on the following link: https://www.programiz.com/dsa/graph-adjacency-list
public class Graph {
    LinkedList<Edge> adjListArray[];
    int numVertices;

    @SuppressWarnings("unchecked")
    Graph(int numVertices) {
        this.numVertices = numVertices;
        adjListArray = new LinkedList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adjListArray[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destiny, int capacity) {
        Edge directEdge = new Edge(destiny, capacity);
        Edge reverseEdge = new Edge(source, 0);

        directEdge.reverse = reverseEdge;
        reverseEdge.reverse = directEdge;

        adjListArray[source].add(directEdge);
        adjListArray[destiny].add(reverseEdge);
    }

    public boolean BFS(int source, int destiny, int[] parent, Edge[] parentEdges) {
        boolean[] visited = new boolean[adjListArray.length];
        Arrays.fill(visited, false);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (Edge edge : adjListArray[u]) {
                int v = edge.destiny;

                if (!visited[v] && edge.residualCap() > 0) {
                    if (v == destiny) {
                        parent[v] = u;
                        parentEdges[v] = edge;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    parentEdges[v] = edge;
                    visited[v] = true;
                }
            }
        }

        return false;
    }
}

class Edge {
    int source;
    int destiny;
    int flux;
    int maxFlux;
    Edge reverse;

    Edge(int s, int d, int max) {
        this.source = s;
        this.destiny = d;
        this.flux = 0;
        this.maxFlux = max;
    }

    // inicializing the flux as zero here, so i dont have to do it on the
    // FordFulkerson algorithm
    Edge(int d, int max) {
        this.destiny = d;
        this.flux = 0;
        this.maxFlux = max;
        this.reverse = null;
    }

    public void addFlux(int flux) {
        this.flux += flux;
        this.reverse.flux -= flux;
    }

    public int residualCap() {
        return maxFlux - flux;
    }
}
