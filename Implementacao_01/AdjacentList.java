import java.util.ArrayList;

public class AdjacentList {
    ArrayList<Vertex> VertexList = new ArrayList<>();

    AdjacentList(int size) {
        for (int index = 1; index <= size; index++) {
            VertexList.add(new Vertex(index));
        }
    }

    public void add(int Vertex, int value) {
        for (Vertex listVertex : VertexList) {
            if (listVertex.number == Vertex) {
                listVertex.sucessorList.add(value);
            }
        }
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
