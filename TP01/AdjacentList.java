import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class AdjacentList {
    ArrayList<Vertex> VertexList = new ArrayList<>();
    HashMap<Integer, Boolean> visited;
    int componnents;

    AdjacentList(int size) {
        for (int index = 0; index < size; index++) {
            VertexList.add(new Vertex(index));
        }
    }

    AdjacentList(AdjacentList original) {
        for (Vertex vertex : original.VertexList) {
            this.VertexList.add(new Vertex(vertex));
        }
    }

    public AdjacentList clone() {
        AdjacentList clone = new AdjacentList(VertexList.size());
        for (Vertex vert : VertexList) {
            clone.VertexList.add(vert.clone());
        }
        return clone;
    }

    public void add(int Vertex, int value) {
        VertexList.get(Vertex).sucessorList.add(value);
    }

    public void remove(int Vertex) {
        java.util.Iterator<Vertex> iterator = VertexList.iterator();
        while (iterator.hasNext()) {
            Vertex vert = iterator.next();
            if (vert.number == Vertex) {
                vert.sucessorList.clear();
                iterator.remove(); // Safely remove the vertex from the list
            } else if (vert.sucessorList.contains(Vertex)) {
                int pos = vert.sucessorList.indexOf(Vertex);
                vert.sucessorList.remove(pos);
            }
        }
    }

    public void Dfs() {
        visited = new HashMap<>();
        componnents = 0;
        for (Vertex vert : VertexList) {
            if (!visited.containsKey(vert.number)) {
                componnents++;
                visited.put(vert.number, true);
                Dfs(vert.number);
            }
        }
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
        return VertexList.get(vertex).sucessorList;
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
