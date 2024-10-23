package Implementacao_03;

import java.util.LinkedList;

//this implementation was heavily inspired by the one on the following link: https://www.programiz.com/dsa/graph-adjacency-list
public class Graph {
    private LinkedList<Vertex> adjListArray[];

    Graph(int numVertices) {
        numVertices++;
        adjListArray = new LinkedList[numVertices];
        for (int i = 1; i < numVertices; i++) {
            adjListArray[i] = new LinkedList<>();
        }
    }

    public void addSucessor(int vertex, int sucessor, int flux) {
        adjListArray[vertex].add(new Vertex(sucessor, flux));
    }

    public void getSucessors(int vertex) {
        for (Vertex pCrawl : adjListArray[vertex]) {
            System.out.print(" -> " + pCrawl.number + "(" + pCrawl.flux + ")");
        }
    }

    public void getPredecessors(int vertex) {
        for (int i = 1; i < adjListArray.length; i++) {
            for (Vertex pCrawl : adjListArray[i]) {
                if (pCrawl.number == vertex) {
                    System.out.print(" -> " + i + "(" + pCrawl.flux + ")");
                }
            }
        }
    }

    public void printGraph() {
        for (int v = 1; v < adjListArray.length; v++) {
            System.out.println("Adjacency list of vertex " + v);
            System.out.print("head");
            for (Vertex pCrawl : adjListArray[v]) {
                System.out.print(" -> " + pCrawl.number + "(" + pCrawl.flux + ")");
            }
            System.out.println("\n");
        }
    }
}

class Vertex {
    int number;
    int flux;

    Vertex(int n, int f) {
        this.number = n;
        this.flux = f;
    }
}
