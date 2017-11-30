package hw09;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class BellmanFord {
    private int[] d = null;
    private int s, t;
    private int successor[];
    private Graph graph;

    public BellmanFord(String filePath) {
        String data = readFile(filePath);
        StringTokenizer str = new StringTokenizer(data, " |\n");
        d = new int[Integer.parseInt(str.nextToken())];
        successor = new int[d.length];

        s = Integer.parseInt(str.nextToken());
        t = Integer.parseInt(str.nextToken());

        graph = new Graph(d.length, Integer.parseInt(str.nextToken()));

        for (int i = 0; str.hasMoreTokens(); i++) {
            graph.getEdges()[i] = new Edge(Integer.parseInt(str.nextToken()), Integer.parseInt(str.nextToken()), Integer.parseInt(str.nextToken()));
        }
    }

    public void bellmanFord() {
        // 초기화
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.MAX_VALUE;
        }
        d[s] = 0;

        for (int i = 1; i <= graph.getVertexNum() - 1; i++) {
            for (int j = 0; j < graph.getEdgeNum(); j++) {
                int u = graph.getEdges()[j].start;
                int v = graph.getEdges()[j].end;
                int w = graph.getEdges()[j].weight;
                if (d[u] != Integer.MAX_VALUE && d[v] > d[u] + w) {
                    d[v] = d[u] + w;
                    successor[v] = u;
                }
            }
        }

        for (int j = 0; j < graph.getEdgeNum(); j++) {
            int u = graph.getEdges()[j].start;
            int v = graph.getEdges()[j].end;
            int w = graph.getEdges()[j].weight;
            if (d[u] != Integer.MAX_VALUE && d[v] > d[u] + w) {
//                d[v] = d[u] + w;
                System.out.println("report negative-weight cycle exists");
//                break;
            }
        }

        System.out.println("MIN DIST to t : " + d[t]);

    }

    private String readFile(String filePath) {
        BufferedReader br = null;
        StringTokenizer str = null;
        StringBuilder sb = null;
        String line;

        try {
            br = new BufferedReader(new FileReader(filePath));
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

    private class Graph {
        private int vertexNum, edgeNum;
        private Edge[] edges;

        public Graph(int vertexNum, int edgeNum) {
            this.vertexNum = vertexNum;
            this.edgeNum = edgeNum;
            this.edges = new Edge[edgeNum];
        }

        public Edge[] getEdges() {
            return edges;
        }

        public int getVertexNum() {
            return vertexNum;
        }

        public int getEdgeNum() {
            return edgeNum;
        }
    }

    private class Edge {
        private int start;
        private int end;
        private int weight;

        public Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }

}
