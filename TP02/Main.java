package TP02;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        // OS VERTICES DOS ARQUIVOS COMEÇAM EM 1, CASO TENTE ACESSAR O VÉRTICE 0, DARÁ
        // ERRO
        try {
            BufferedReader reader = new BufferedReader(new FileReader("TP02/TestGraphs/pmed1.txt"));
            String line = reader.readLine();
            int[] values = treatString(line);
            Graph graph = new Graph();

            while ((line = reader.readLine()) != null) {
                values = treatString(line);
                graph.addEdge(values[0], values[1], values[2]);
            }

            reader.close();
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
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
