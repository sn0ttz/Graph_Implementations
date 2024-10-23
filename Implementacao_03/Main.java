package Implementacao_03;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(5);

        graph.addSucessor(1, 2, 1);
        graph.addSucessor(1, 3, 1);
        graph.addSucessor(2, 4, 1);
        graph.addSucessor(3, 4, 1);

        graph.printGraph();
    }
}
