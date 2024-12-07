package TP02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // OS VERTICES DOS ARQUIVOS COMEÇAM EM 1, CASO TENTE ACESSAR O VÉRTICE 0, DARÁ
        // ERRO

        BufferedReader reader = new BufferedReader(new FileReader("TP02/TestGraphs/pmed1.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("TP02/ResultsII.txt"));
        for (int i = 3; i <= 40; i++) {
            System.out.println("Calculando K-centros para o arquivo pmed" + i + ":");
            reader = new BufferedReader(new FileReader("TP02/TestGraphs/pmed" + i + ".txt"));
            String line = reader.readLine();
            int[] values = treatString(line);
            int k = values[2];
            Graph graph = new Graph();
            while ((line = reader.readLine()) != null) {
                values = treatString(line);
                graph.addEdge(values[0], values[1], values[2]);
            }
            long startTime = System.currentTimeMillis();
            Result result = graph.calculateKcenters(k);
            long endTime = System.currentTimeMillis();

            writer.write("pmed" + i + ".txt:\n");
            writer.write("Tempo: " + (endTime - startTime) + "ms\n");
            writer.write("K = " + k + "\n");
            writer.write("Centros: \n");
            for (int center : result.centers) {
                writer.write(center + " ");
            }
            writer.write("\n");
            writer.write("Raio: " + result.radius + "\n");
            writer.write("-------------------------------------------------------\n");


            // Teste do método de força bruta
            System.out.println("Calculando K-centros (força bruta) para o arquivo pmed" + i + ":");
            startTime = System.currentTimeMillis();
            Result bruteForceResult = graph.calculateKcentersBruteForce(k);
            endTime = System.currentTimeMillis();

            writer.write("Força Bruta:\n");
            writer.write("Tempo: " + (endTime - startTime) + "ms\n");
            writer.write("Centros: \n");
            for (int center : bruteForceResult.centers) {
                writer.write(center + " ");
            }
            writer.write("\n");
            writer.write("Raio: " + bruteForceResult.radius + "\n");
            writer.write("-------------------------------------------------------\n");




        }
        writer.close();
        reader.close();
    }

    public static int[] treatString(String phrase) {
        int[] result = new int[3];
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
