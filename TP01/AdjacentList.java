import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class AdjacentList {
    ArrayList<Vertex> VertexList = new ArrayList<>();
    HashMap<Integer, Boolean> visited;
    int components;
    ArrayList<Edge> Edges = new ArrayList<>();
    ArrayList<Integer> cycles = new ArrayList<>();

    AdjacentList(int size) {
        for (int index = 0; index < size; index++) {
            VertexList.add(new Vertex(index));
        }
    }

    AdjacentList(AdjacentList original) {
        this.VertexList = new ArrayList<>();
        for (Vertex vertex : original.VertexList) {
            this.VertexList.add(vertex.clone());
        }
    }

    protected AdjacentList clone() {
        return new AdjacentList(this);
    }

    public void add(int Vertex, int value) {
        VertexList.get(Vertex).sucessorList.add(value);
    }

    public void removeVertex(Vertex vertex) {
        // Remove the vertex from the VertexList
        VertexList.remove(vertex);

        // Remove all references to this vertex in the successor lists of other vertices
        for (Vertex v : VertexList) {
            v.sucessorList.remove(Integer.valueOf(vertex.number));
        }
    }

    public int Dfs() {
        visited = new HashMap<>();
        components = 0;
        for (Vertex vert : VertexList) {
            if (!visited.containsKey(vert.number)) {
                components++;
                visited.put(vert.number, true);
                Dfs(vert.number);
            }
        }
        return components;
    }

    private void Dfs(int root) {
        // para prevenir stackoverflow da maquina, utilizei a stack do java e fiz a dfs
        // de modo iterativo
        Stack<Integer> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            boolean discovered = false;
            // olha o vertice do topo da pilha e processa todos os seus sucessores
            int current = stack.peek();
            ArrayList<Integer> successors = getSucessors(current);

            for (Integer vertex : successors) {
                // para cada sucessor, verifica se ele ja foi visitado
                if (visited.containsKey(vertex)) {
                    this.Edges.add(new Edge(current, vertex, "return"));
                    continue;
                    // se nao foi visitado, marca como visitado, coloca na pilha e busca nele
                } else {
                    visited.put(vertex, true);
                    stack.push(vertex);
                    discovered = true;
                    this.Edges.add(new Edge(current, vertex, "tree"));
                    break;
                }
            }
            // aqui jaz
            if (!discovered) {
                stack.pop();
            }
        }
    }

    public HashMap<Integer, Integer> isCycle(int destination) {
        HashMap<Integer, Integer> cycles = new HashMap<>();
        for (Vertex vert : VertexList) {
            visited = new HashMap<>();
            if (vert.number != destination) {
                if (!visited.containsKey(vert.number)) {
                    visited.put(vert.number, true);
                    if (isCycle(vert.number, destination) && isCycle(destination, vert.number)) {
                        cycles.put(vert.number, destination);
                    }
                }
            }
        }
        return cycles;
    }

    private boolean isCycle(int root, int destination) {
        // metodo quase identico ao dfs, mas com a condicao de parada sendo o destino
        Stack<Integer> stack = new Stack<>();
        stack.push(root);
        // se o destino for sucessor do vertice inicial, nao ha ciclo
        if (getSucessors(root).contains(destination)) {
            return false;
        }

        while (!stack.isEmpty()) {
            boolean discovered = false;
            int current = stack.peek();
            ArrayList<Integer> successors = getSucessors(current);
            for (Integer vertex : successors) {
                // se o vertice atual for o destino, ha ciclo
                if (vertex == destination) {
                    return true;
                }

                if (visited.containsKey(vertex)) {
                    this.Edges.add(new Edge(current, vertex, "return"));
                    continue;
                } else {
                    visited.put(vertex, true);
                    stack.push(vertex);
                    discovered = true;
                    this.Edges.add(new Edge(current, vertex, "tree"));
                    break;
                }
            }
            // aqui jaz
            if (!discovered) {
                stack.pop();
            }
        }
        return false;
    }

    public ArrayList<Integer> getSucessors(int vertex) {
        ArrayList<Integer> sucessors = new ArrayList<>();
        for (Vertex vert : VertexList) {
            if (vert.number == vertex) {
                sucessors = new ArrayList<>(vert.sucessorList);
            }
        }
        return sucessors;
    }

    @Override
    public String toString() {
        String response = "";
        for (Vertex listVertex : VertexList) {
            response += "Vertex " + listVertex.number + " sucessors: ";
            for (Integer next : listVertex.sucessorList) {
                response += next + " ";
            }
            response += "\n";
        }
        return response;
    }
}

class Vertex {
    ArrayList<Integer> sucessorList;
    int number;

    Vertex(int n) {
        sucessorList = new ArrayList<Integer>();
        this.number = n;
    }

    Vertex(Vertex vertex) {
        this.number = vertex.number;
        this.sucessorList = new ArrayList<>(vertex.sucessorList);
    }

    // Clone method
    public Vertex clone() {
        return new Vertex(this);
    }

}
