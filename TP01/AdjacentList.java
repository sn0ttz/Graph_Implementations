import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class AdjacentList {
    ArrayList<Vertex> VertexList = new ArrayList<>();

    AdjacentList(int size) {
        for (int index = 1; index <= size; index++) {
            VertexList.add(new Vertex(index));
        }
    }

    public void add(int Vertex, int value) {
        VertexList.get(Vertex - 1).sucessorList.add(value);
    }

    public void Dfs(int root) {
        // para prevenir stackoverflow da maquina, utilizei a stack do java e fiz a dfs
        // de modo iterativo
        Stack<Integer> stack = new Stack<>();
        stack.push(root);
        HashMap<Integer, Boolean> visited = new HashMap<>();
        while (!stack.isEmpty()) {
            boolean discovered = false;
            // olha o vertice do topo da pilha e processa todos os seus sucessores
            int current = stack.peek();
            ArrayList<Integer> successors = getSucessors(current);

            for (Integer vertex : successors) {
                // para cada sucessor, verifica se ele ja foi visitado
                if (visited.containsKey(vertex)) {
                    continue;
                    // se nao foi visitado, marca como visitado, coloca na pilha e busca nele
                } else {
                    visited.put(vertex, true);
                    stack.push(vertex);
                    discovered = true;
                    break;
                }
            }
            // aqui jaz
            if (!discovered) {
                stack.pop();
            }
        }
    }

    public ArrayList<Integer> getSucessors(int vertex) {
        return VertexList.get(vertex - 1).sucessorList;
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
}
