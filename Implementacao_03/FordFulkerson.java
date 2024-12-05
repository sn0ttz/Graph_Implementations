package Implementacao_03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FordFulkerson {

    static class Result {
        int maxFlow;
        List<List<Edge>> paths;

        public Result(int maxFlow, List<List<Edge>> paths) {
            this.maxFlow = maxFlow;
            this.paths = paths;
        }
    }

    public static Result fordFulkerson(Graph graph, int source, int destiny) {
        int maxFlow = 0;
        int[] parent = new int[graph.numVertices];
        Edge[] parentEdges = new Edge[graph.numVertices];
        List<List<Edge>> paths = new ArrayList<>();

        while (graph.BFS(source, destiny, parent, parentEdges)) {
            int pathFlux = Integer.MAX_VALUE;
            List<Edge> path = new ArrayList<>();

            for (int v = destiny; v != source; v = parent[v]) {
                Edge edge = parentEdges[v];
                pathFlux = Math.min(pathFlux, edge.residualCap());
                path.add(edge);
            }

            for (int v = destiny; v != source; v = parent[v]) {
                Edge edge = parentEdges[v];
                edge.addFlux(pathFlux);
            }

            maxFlow += pathFlux;
            Collections.reverse(path);
            paths.add(path);
        }

        return new Result(maxFlow, paths);
    }
}
