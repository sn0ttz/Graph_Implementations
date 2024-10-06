import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        AdjacentList adj;

        int userOption = 0;
        int vertexNumber = 0;
        int edgeNumber = 0;
        long startTime, endTime, duration, timeCurent;

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
            default:
                break;
        }

        System.out.println("Por favor, selecione o número de arestas desejado");
        edgeNumber = scanner.nextInt();
        startTime = System.currentTimeMillis();
        adj = RandomGraph.generateRandomGraph(vertexNumber, edgeNumber);
        System.out.println("Grafo gerado");
        endTime = System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("Tempo de execução: " + duration + "ms");

        userOption = 0;
        while (userOption != 4) {
            System.out.println("Por favor, selecione a opção desejada:");
            System.out.println("1 - Verificar a existência de ciclos para cada par de vértices");
            System.out.println("2 - Verificar a existência de articulações removendo cada vértice e realizando um DFS");
            System.out.println("3 - Método de Tarjan");
            System.out.println("4 - Sair");
            userOption = scanner.nextInt();
            if (userOption != 4) {
                switch (userOption) {
                    case 1:
                        duration = 0;
                        startTime = System.currentTimeMillis();
                        HashMap<Integer, Integer> cycleMap = new HashMap<>();
                        for (Vertex v1 : adj.VertexList) {

                            cycleMap = adj.isCycle(v1.number);
                            System.out.println("Ciclos com o vértice " + v1.number + ": " + cycleMap);

                            timeCurent = System.currentTimeMillis() - (duration + startTime);
                            System.out.println("Tempo de execução dessa iteração: " + timeCurent + "ms");

                            duration = (System.currentTimeMillis() - startTime);

                            if (duration > 300000) {
                                System.out.println(
                                        "\n--------------------------------\nTempo de execução excedido\n--------------------------------");
                                break;
                            }
                        }
                        endTime = System.currentTimeMillis();
                        duration = (endTime - startTime);
                        System.out.println("Tempo de execução: " + duration + "ms");
                        break;
                    case 2:
                        startTime = System.currentTimeMillis();
                        duration = 0;
                        adj.Dfs();
                        ArrayList<Vertex> articulationList = connectivityTest(adj);
                        System.out.println("\nArticulation points: ");

                        for (Vertex articulation : articulationList) {

                            System.out.print(articulation.number + " ");

                        }

                        endTime = System.currentTimeMillis();
                        duration = (endTime - startTime);
                        System.out.println("\n\nTempo de execução: " + duration + "ms");
                        System.out.println();
                        break;
                    case 3:
                        System.out.println("--------------------------");
                        System.out.println("Iniciando método de Tarjan...");
                        startTime = System.currentTimeMillis();
                        Tarjan tarjan = new Tarjan();
                        tarjan.findBC(adj);
                        System.out.println("------------------------");
                        endTime = System.currentTimeMillis();
                        duration = (endTime - startTime);
                        System.out.println("Tempo de execução: " + duration + "ms");
                        break;
                    case 4:

                        break;

                    default:
                        break;
                }
            }
        }
        scanner.close();
    }

    public static ArrayList<Vertex> connectivityTest(AdjacentList adj) {
        ArrayList<Vertex> articullationList = new ArrayList<>();
        // cria uma cópia profunda do grafo original

        AdjacentList copy = adj.clone();

        // realiza uma DFS no grafo original para verificar o número de componentes
        int components = adj.Dfs();

        // utiliza uma lista temporária para armazenar os vértices a serem removidos e
        // re-adicionados
        ArrayList<Vertex> tempVertexList = new ArrayList<>(copy.VertexList);
        long startTime, duration = 0, timeCurent;

        startTime = System.currentTimeMillis();
        for (Vertex vert : tempVertexList) {
            // removendo o vértice
            copy.removeVertex(vert);
            // realizando uma DFS no grafo sem o vértice
            System.out.println("DFS on the graph without vertex " + vert.number);
            int copycomp = copy.Dfs();
            // se o número de componentes aumentar, o vértice removido era um ponto de
            // articulação
            if (copycomp > components) {
                articullationList.add(vert);
            }
            // re-adicionando o vértice
            copy.VertexList.add(vert);

            for (Vertex v : copy.VertexList) {
                if (adj.VertexList.get(v.number).sucessorList.contains(vert.number)) {
                    v.sucessorList.add(vert.number);
                }
            }

            timeCurent = System.currentTimeMillis() - (duration + startTime);
            System.out.println("Tempo de execução dessa iteração: " + timeCurent + "ms\n");
            duration = (System.currentTimeMillis() - startTime);
            if (duration > 300000) {
                System.out.println(
                        "\n--------------------------------\nTempo de execução excedido\n--------------------------------");
                return articullationList;
            }
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