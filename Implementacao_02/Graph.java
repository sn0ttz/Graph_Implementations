
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader buf = new BufferedReader(new FileReader("Implementacao_02/graph-test-50000-1.txt"));
        Scanner sc = new Scanner(System.in);

        int[] graphSize = stringTreatment(buf.readLine());
        String data = "";
        ForwardStar fwd = new ForwardStar(graphSize[1], graphSize[0]);

        while ((data = buf.readLine()) != null) {
            int tuple[] = stringTreatment(data);
            fwd.add(tuple[0], tuple[1]);
        }
        // setando o array de ponteiro do forward star
        fwd.setPointer();

        fwd.Dfs();

        int user_input = 0;
        int vertix_input = 0;
        while (user_input != -1) {
            System.out.println("1 - Get ALL Graph Trees\n2 - Get Vertix Edges\n-1 - Exit\n");
            user_input = sc.nextInt();
            if (user_input != -1) {
                switch (user_input) {
                    case 1:
                        ArrayList<Dfs_Edge> trees = fwd.getTrees();
                        for (Dfs_Edge edge : trees) {
                            System.out.println(edge);
                        }
                        break;
                    case 2:
                        System.out.println("Enter the vertix you want to get the edge types: \n");
                        vertix_input = sc.nextInt();
                        ArrayList<Dfs_Edge> Vertexedges = fwd.getEdges(vertix_input);
                        for (Dfs_Edge edge : Vertexedges) {
                            System.out.println(edge);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        sc.close();
        buf.close();
    }

    public static int[] stringTreatment(String phrase) {
        int[] result = new int[2];
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