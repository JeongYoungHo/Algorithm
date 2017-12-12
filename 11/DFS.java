package hw11;

public class DFS {
    private int[][] adjMatrix;
    private Graph G;
    private int NIL = -1;
    private int time;


    public DFS(int[][] adjMatrix){
        this.adjMatrix = adjMatrix;
        this.G = new Graph(this.adjMatrix.length);
    }

    public void dfs() {
        for (int i = 0; i < this.G.size; i++) {
            G.vertices[i] = new Vertex("WHITE", NIL);
        }
        this.time = 0;
        for(int i=0; i< this.G.size; i++){
            if(G.vertices[i].color.equals("WHITE")){
                dfs_visit(i);
            }
        }
    }

    private void dfs_visit(int u){
        time = time + 1;
        G.vertices[u].d = time;
        G.vertices[u].color = "GRAY";
        for(int i =0; i<this.G.size; i++){
            if(this.adjMatrix[u][i] == 1){
                if(this.G.vertices[i].color.equals("WHITE")){
                    G.vertices[i].parent = u;
                    dfs_visit(i);
                }
            }
        }
        G.vertices[u].color = "BLACK";
        time = time + 1;
        G.vertices[u].f = time;
    }

    public void printParent(){
        System.out.println("Parent");
        System.out.println("u  v  w  x  y  z");
        for(int i = 0; i<this.G.size ; i++){
            System.out.print(G.vertices[i].parent + "  ");
        }
        System.out.println();
    }

    public void printDandF(){
        System.out.println("d/f");
        System.out.println("u  v  w  x  y  z");
        for(int i = 0; i<this.G.size ; i++){
            System.out.print(G.vertices[i].d + "  ");
        }
        System.out.println();
        for(int i = 0; i<this.G.size ; i++){
            System.out.print(G.vertices[i].f + "  ");
        }
        System.out.println();
    }

    private class Graph{
        private Vertex[] vertices;
        private int size;

        public Graph(int size){
            this.vertices = new Vertex[size];
            this.size = size;
        }

    }
    private class Vertex{
        public String color;
        public int parent;
        public int d;
        public int f;

        public Vertex(String color, int parent){
            this.color = color;
            this.parent = parent;
        }
    }
}

