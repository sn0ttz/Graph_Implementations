import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;

class State {
    int vertex;
    int parent;
    Iterator<Integer> iterator;

    public State(int vertex, int parent) {
        this.vertex = vertex;
        this.parent = parent;
    }
}

public class Tarjan {
    // Atributos
    private int time;
    private ArrayList<ArrayList<int[]>> bc = new ArrayList<>(); // componentes biconexos
    private Stack<int[]> edgeStack = new Stack<>(); // pilha de arestas
    int disc[]; // tempo de descoberta de vertice
    int low[]; // numero "low" de vertice

    // Construtor
    public Tarjan() {
        this.time = 0;
    }

    // encontrar componentes biconexos
    public void findBC(AdjacentList graph) {
        int n = graph.VertexList.size(); // numero de vertices
        boolean visited[] = new boolean[n]; // vertices visitados
        disc = new int[n];
        low = new int[n];
        long startTime, duration = 0, timeCurent;

        startTime = System.nanoTime();

        // itera para cada um dos vertices
        for (Vertex vertex : graph.VertexList) {
            if (!visited[vertex.number]) {
                findBCUtil(vertex.number, -1, visited, graph);
            }

            timeCurent = System.nanoTime() - (duration + startTime);

            System.out.println("Tempo de execução dessa iteração: " + timeCurent + "ns\n"); // printa tempo de iteração

            duration = (System.nanoTime() - startTime);
            if (duration > 300000000000L) {
                System.out.println(
                        "\n--------------------------------\nTempo de execução excedido\n--------------------------------");
                return;
            }

        }

        // se ainda houver arestas na pilha
        if (!edgeStack.isEmpty()) {
            ArrayList<int[]> lastComponent = new ArrayList<>();
            while (!edgeStack.isEmpty()) {
                lastComponent.add(edgeStack.pop());
            }
            bc.add(lastComponent);
        }

        printBiconnectedComponents();
    }

    // metodo iterativo para encontrar componentes biconexos
    public void findBCUtil(int start, int parent, boolean[] visited, AdjacentList graph) {
        Stack<State> stack = new Stack<>(); // pilha de estados
        stack.push(new State(start, parent));

        while (!stack.isEmpty()) {
            State state = stack.peek();
            int v = state.vertex;
            int p = state.parent;

            // se o vertice ainda não foi visitado
            if (!visited[v]) {
                visited[v] = true;
                disc[v] = low[v] = ++time;
                state.iterator = graph.VertexList.get(v).sucessorList.iterator();
            }

            boolean backtrack = true;

            // itera sobre os sucessores do vertice
            while (state.iterator.hasNext()) {
                int w = state.iterator.next();
                if (!visited[w]) {
                    // aresta de arvore
                    edgeStack.push(new int[] { v, w });
                    stack.push(new State(w, v));
                    backtrack = false;
                    break;
                } else if (w != p && disc[w] < disc[v]) {
                    // aresta de retorno
                    edgeStack.push(new int[] { v, w });
                    low[v] = Math.min(low[v], disc[w]);
                }
            }

            // quando todos os vizinhos foram visitados
            if (backtrack) {
                stack.pop();
                if (p != -1) { // p não é raiz
                    low[p] = Math.min(low[p], low[v]); // atualiza low do pai

                    // checa se v é um ponto de articulação
                    if (low[v] >= disc[p]) {
                        // novo componente biconexo
                        ArrayList<int[]> component = new ArrayList<>();
                        int[] edge;

                        do {
                            edge = edgeStack.pop();
                            component.add(edge);
                        } while (edge[0] != p || edge[1] != v);
                        bc.add(component);
                    }
                }
            }
        }
    }

    private void printBiconnectedComponents() {
        System.out.println("Componentes biconexos:");
        for (ArrayList<int[]> component : bc) {
            System.out.print("{ ");
            for (int[] edge : component) {
                System.out.print("(" + edge[0] + ", " + edge[1] + ") ");
            }
            System.out.println("}");
        }
    }
}