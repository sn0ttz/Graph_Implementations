public class Dfs_Edge {
    int origin;
    int destiny;
    String type;

    public Dfs_Edge(int origin, int destiny, String type) {
        this.origin = origin;
        this.destiny = destiny;
        this.type = type;
    }

    @Override
    public String toString() {
        return "(" + origin + ", " + destiny + ") " + type + "\n";
    }
}
