package Implementacao_03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Implementacao_03.FordFulkerson.Result;

public class Main {
    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("output.txt");
        BufferedReader reader = new BufferedReader(new FileReader("Implementacao_03/RandomTrees/randomTree10.txt"));
        writer.write("Random Tree time:" + "\n");
        for (int i = 10; i < 100000; i *= 10) {
            Long start = System.nanoTime();

            reader = new BufferedReader(new FileReader("Implementacao_03/RandomTrees/randomTree" + i + ".txt"));

            String line = reader.readLine();
            int[] values = treatString(line);

            Graph graph = new Graph(values[0]);

            while ((line = reader.readLine()) != null) {
                values = treatString(line);
                graph.addEdge(values[0], values[1], values[2]);
            }

            writer.write("Nodes: " + i + '\n');

            Result result = FordFulkerson.fordFulkerson(graph, 0, graph.numVertices - 1);

            writer.write("Max Flow: " + result.maxFlow + '\n');

            long end = System.nanoTime();
            writer.write("Time: " + (end - start) / 1000000 + "ms\n");
        }
        writer.write("------------------------------------------------------" + "\n");
        writer.write("Random Graph time:" + "\n");

        for (int i = 10; i < 100000; i *= 10) {
            long start = System.nanoTime();
            reader = new BufferedReader(new FileReader("Implementacao_03/RandomGraphs/randomGraph" + i + ".txt"));
            String line = reader.readLine();
            int[] values = treatString(line);
            Graph graph = new Graph(values[0]);
            while ((line = reader.readLine()) != null) {
                values = treatString(line);
                graph.addEdge(values[0], values[1], values[2]);
            }

            writer.write("Nodes: " + i + '\n');
            Result result = FordFulkerson.fordFulkerson(graph, 0, graph.numVertices - 1);
            writer.write("Max Flow: " + result.maxFlow + '\n');
            long end = System.nanoTime();
            writer.write("Time: " + (end - start) / 1000000 + "ms\n");
        }

        reader.close();
        writer.close();
    }

    public static int[] treatString(String line) {
        String[] values = line.split(" ");
        int[] intValues = new int[values.length];

        for (int i = 0; i < values.length; i++) {
            intValues[i] = Integer.parseInt(values[i]);
        }

        return intValues;
    }
}
