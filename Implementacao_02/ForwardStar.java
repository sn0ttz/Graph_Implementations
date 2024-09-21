
import java.util.ArrayList;
import java.util.Stack;

public class ForwardStar {
    int[] origin;
    int[] destiny;
    int[] pointer;
    int[] TD;
    int[] TT;
    int[] father;
    int counter;
    int next_vertex;
    ArrayList<Dfs_Edge> edges;

    ForwardStar(int Edges, int Vertixes) {
        origin = new int[Edges];
        destiny = new int[Edges];
        pointer = new int[Vertixes];
        next_vertex = 0;
        TD = new int[Vertixes];
        TT = new int[Vertixes];
        father = new int[Vertixes];
        edges = new ArrayList<>();
    }

    public void add(int Vertex, int Sucessor) {
        try {
            origin[next_vertex] = Vertex;
            destiny[next_vertex] = Sucessor;
            next_vertex++;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not enough space allocated on the array");
            System.exit(1);
        }
    }

    public void setPointer() {
        int last_vertex = 0;
        for (int i = 0; i < origin.length; i++) {
            if (origin[i] != last_vertex) {
                pointer[last_vertex] = i;
                last_vertex++;
            }
        }
    }

    public ArrayList<Integer> getSucessors(int Vertex) {
        ArrayList<Integer> sucessors = new ArrayList<>();
        int correctedVertex = Vertex - 1;
        if (correctedVertex == pointer.length - 1) {
            for (int j = pointer[correctedVertex]; j < destiny.length; j++) {
                sucessors.add(destiny[j]);
            }
        } else {
            for (int j = pointer[correctedVertex]; j < pointer[correctedVertex + 1]; j++) {
                sucessors.add(destiny[j]);
            }
        }
        // Ordenando em ordem lexicografica
        sucessors.sort(null);
        // Adicionando a fila

        return sucessors;
    }

    public ArrayList<Integer> getPredecessors(int Vertex) {
        ArrayList<Integer> predecessors = new ArrayList<>();
        for (int i = 0; i < pointer.length; i++) {
            if (i == pointer.length - 1) {
                for (int j = pointer[i]; j < destiny.length; j++) {
                    if (destiny[j] == Vertex) {
                        predecessors.add(origin[j]);
                    }
                }
            } else {
                for (int j = pointer[i]; j < pointer[i + 1]; j++) {
                    if (destiny[j] == Vertex) {
                        predecessors.add(origin[j]);
                    }
                }
            }
        }
        // Ordenando por ordem lexicografica
        predecessors.sort(null);
        return predecessors;
    }

    public void Dfs() {
        counter = 0;
        for (int i = 0; i < TD.length; i++) {
            TT[i] = 0;
            TD[i] = 0;
            father[i] = 0;
        }

        for (int i = 0; i < TD.length; i++) {
            if (TD[i] == 0) {
                Dfs(i + 1);
            }
        }
        // for (Dfs_Edge edge : edges) {
        // System.out.println(edge);
        // }
    }

    private void Dfs(int root) {
        Stack<Integer> stack = new Stack<>();
        stack.push(root);
        counter++;
        TD[root - 1] = counter;

        while (!stack.isEmpty()) {
            int current = stack.pop();
            int correctedVertex = current - 1;
            ArrayList<Integer> successors = getSucessors(current);

            for (Integer vertex : successors) {
                if (TD[vertex - 1] == 0) {
                    father[vertex - 1] = current;
                    edges.add(new Dfs_Edge(current, vertex, "tree"));
                    stack.push(vertex);
                    counter++;
                    TD[vertex - 1] = counter;
                } else {
                    if (TT[vertex - 1] == 0) {
                        edges.add(new Dfs_Edge(current, vertex, "back"));
                    } else if (TD[current - 1] < TD[vertex - 1]) {
                        edges.add(new Dfs_Edge(current, vertex, "forward"));
                    } else {
                        edges.add(new Dfs_Edge(current, vertex, "cross"));
                    }
                }
            }
            counter++;
            TT[correctedVertex] = counter;
        }
    }

    public ArrayList<Dfs_Edge> getTrees() {
        ArrayList<Dfs_Edge> trees = new ArrayList<>();
        for (Dfs_Edge edge : edges) {
            if (edge.type.equals("tree")) {
                trees.add(edge);
            }
        }
        return trees;
    }

    public ArrayList<Dfs_Edge> getEdges(int Vertex) {
        ArrayList<Dfs_Edge> edges = new ArrayList<>();
        for (Dfs_Edge edge : this.edges) {
            if (edge.origin == Vertex) {
                edges.add(edge);
            }
        }
        return edges;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < pointer.length; i++) {
            System.out.println("Sucessores do Vertice " + (i + 1) + ": ");
            if (i == pointer.length - 1) {
                for (int j = pointer[i]; j < destiny.length; j++) {
                    System.out.print(destiny[j] + " ");
                }
            } else {
                for (int j = pointer[i]; j < pointer[i + 1]; j++) {
                    System.out.print(destiny[j] + " ");
                }
            }
            System.out.println();
        }
        return ret;
    }
}
