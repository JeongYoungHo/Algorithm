package hw11;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    private int[][] adjMatrix;
    private Graph G;
    private int NIL = -1;
    private Queue<Integer> q;

    public BFS(int[][] adjMatrix){
        this.adjMatrix = adjMatrix;
        this.G = new Graph(this.adjMatrix.length);
    }

    public void bfs(int s){
        for(int i = 0; i < G.size ; i++){
            if( i == s ) continue;
            G.vertices[i] = new Vertex("WHITE", Integer.MAX_VALUE, NIL);
//            G.vertices[i].color = "WHITE";
//            G.vertices[i].d = Integer.MAX_VALUE;
//            G.vertices[i].parent = NIL;
        }
        G.vertices[s] = new Vertex("GRAY", 0, NIL);
//        G.vertices[s].color = "GRAY";
//        G.vertices[s].d = 0;
//        G.vertices[s].parent = NIL;
        this.q = new LinkedList<Integer>();
        q.add(s);
        while(!q.isEmpty()){
            int u = q.poll();
            for(int i = 0; i<this.adjMatrix.length; i++){
                if(this.adjMatrix[u][i] == 1){
                    if(this.G.vertices[i].color.equals("WHITE")){
                        G.vertices[i].color = "GRAY";
                        G.vertices[i].d = G.vertices[u].d + 1;
                        G.vertices[i].parent = u;
                        q.add(i);
                    }
                }
            }
            G.vertices[u].color = "BLACK";
        }
    }

    public void printParent(){
        System.out.println("Parent");
        System.out.println("r  s  t  u  v  w  x  y");
        for(int i = 0; i<this.G.size ; i++){
            System.out.print(G.vertices[i].parent + "  ");
        }
        System.out.println();
    }

    public void printCost(){
        System.out.println("Cost");
        System.out.println("r  s  t  u  v  w  x  y");
        for(int i = 0; i<this.G.size; i++){
            System.out.print(G.vertices[i].d + "  ");
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
            public int d;
            public int parent;

            public Vertex(String color, int d, int parent){
                this.color = color;
                this.d = d;
                this.parent = parent;
            }
        }
}

