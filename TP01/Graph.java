import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        String data = "";

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

            // adj.remove(50);

            // System.out.println(adj.toString());
        }

        scanner.close();
    }

    public static ArrayList<Integer> getPredecessors(int vertex, AdjacentList list) {
        ArrayList<Integer> predecessors = new ArrayList<>();
        for (Vertex vert : list.VertexList) {
            for (Integer num : vert.sucessorList) {
                if (num == vertex) {
                    predecessors.add(vert.number);
                }
            }
        }
        return predecessors;
    }

    public void connectivityTest(AdjacentList adj) {
        ArrayList<Vertex> articullationList = new ArrayList<>();
        // copiando o grafo original
        AdjacentList copy = adj;
        // rodando uma DFS no grafo original para verificar o numero de componentes
        adj.Dfs();
        int components = adj.componnents;
        for (Vertex vert : copy.VertexList) {

        }
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