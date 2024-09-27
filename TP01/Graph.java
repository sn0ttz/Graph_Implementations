import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        BufferedReader buf = new BufferedReader(new FileReader("TP01/graph-test-50000-1.txt"));

        String data = "";

        int[] graphSize = stringTreatment(buf.readLine());

        AdjacentList adj = new AdjacentList(graphSize[0]);

        while ((data = buf.readLine()) != null) {
            int tuple[] = stringTreatment(data);
            adj.add(tuple[0], tuple[1]);
        }

        int userOption = 0;
        int vertexNumber;
        while (userOption != 5) {
            if (userOption != 5) {
                System.out.println("Por favor, informe o numero do vertice que quer consultar");
                vertexNumber = scanner.nextInt();
                System.out.println(
                        "Agora, digite 1 para saber o grau de saida dele, 2 para o grau de entrada, 3 para o conjunto de sucessores , 4 para o conjunto de predecessores e 5 para sair");
                userOption = scanner.nextInt();
                switch (userOption) {
                    case 1:
                        System.out.println("O grau de saida do vertice e de: " + exitDegree(vertexNumber, adj));
                        break;
                    case 2:
                        System.out.println("O grau de entrada do vertice e de: " + enteringDegree(vertexNumber, adj));
                        break;
                    case 3:
                        try {
                            System.out.println(
                                    "O conjunto de sucessores do vertice e: " + getSuccessors(vertexNumber, adj));
                        } catch (Exception e) {
                            System.out.println("Numero invalido de vertice. Por favor, tente novamente");
                        }
                        break;
                    case 4:
                        System.out.println(
                                "O conjunto de predecessores do vertice e: " + getPredecessors(vertexNumber, adj));
                        break;
                    case 5:
                        break;
                    case 6:
                        // debug
                        System.out.println(adj);
                    default:
                        break;
                }
            }
        }
        buf.close();
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