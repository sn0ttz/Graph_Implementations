import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        AdjacentList adj;

        int userOption = 0;
        int vertexNumber = 0;
        int edgeNumber = 0;

        while (userOption != 5) {
            if (userOption != 5) {
                System.out.println("Por favor, selecione o tamanho do grafo desejado");
                System.out.println(
                        " 1 - 100 \n 2 - 1000 \n 3 - 10000 \n 4 - 100000 \n 5 - sair");

                userOption = scanner.nextInt();
                switch (userOption) {
                    case 1:
                        vertexNumber = 100;
                        break;
                    case 2:
                        vertexNumber = 1000;
                        break;
                    case 3:
                        vertexNumber = 10000;
                        break;
                    case 4:
                        vertexNumber = 100000;
                        break;
                    case 5:
                        break;
                    default:
                        break;
                }
            }

            System.out.println("Por favor, selecione o número de arestas desejado");
            edgeNumber = scanner.nextInt();

            adj = RandomGraph.generateRandomGraph(vertexNumber, edgeNumber);

            System.out.println(adj.toString());

            ArrayList<Vertex> articulationList = connectivityTest(adj);
            System.out.println("Articulation points: " + articulationList.toString());
        }

        scanner.close();
    }

    public static ArrayList<Vertex> connectivityTest(AdjacentList adj) {
        ArrayList<Vertex> articullationList = new ArrayList<>();
        // Creating a deep copy of the original graph
        AdjacentList copy = new AdjacentList(adj);
        // Running a DFS on the original graph to check the number of components
        adj.Dfs();
        int components = adj.componnents;
        for (Vertex vert : copy.VertexList) {
            // Removing a vertex
            copy.VertexList.remove(vert);
            // Running a DFS on the graph without the vertex
            copy.Dfs();
            // If the number of components increases, the removed vertex was an articulation
            // point
            if (copy.componnents > components) {
                articullationList.add(vert);
            }
            // Re-adding the vertex
            copy.VertexList.add(vert);
        }
        return articullationList;
    }

    public static ArrayList<Integer> getSuccessors(int vertex, AdjacentList list) {
        return list.VertexList.get(vertex - 1).sucessorList;
    }

    public static int enteringDegree(int vertex, AdjacentList list) {
        int enterCounter = 0;
        for (Vertex vert : list.VertexList) {
            for (Integer num : vert.sucessorList) {
                if (num == vertex) {
                    enterCounter++;
                }
            }
        }
        return enterCounter;
    }

    public static int exitDegree(int vertex, AdjacentList list) {
        return list.VertexList.get(vertex - 1).sucessorList.size();
    }

    public static int[] stringTreatment(String phrase) {
        int[] result = new int[2];
        int wordCounter = 0;
        int i;
        String resulString = "";
        for (i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) != ' ') {
                resulString += phrase.charAt(i);
                if (i + 1 < phrase.length()) {
                    if (phrase.charAt(i + 1) == ' ') {
                        result[wordCounter] = Integer.parseInt(resulString);
                        wordCounter++;
                        resulString = "";
                    }
                } else {
                    result[wordCounter] = Integer.parseInt(resulString);
                }
            }
        }
        return result;
    }
}